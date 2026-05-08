/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.client.display.IOverlayListener;
import journeymap.api.v2.client.display.PolygonOverlay;
import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.model.MapPolygon;
import journeymap.api.v2.client.model.ShapeProperties;
import journeymap.api.v2.client.model.TextProperties;
import journeymap.api.v2.client.util.PolygonHelper;
import journeymap.api.v2.client.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Sample factory that produces PolygonOverlays used to show which chunks are likely to spawn slime.
 */
public final class SamplePolygonOverlayFactory
{
    private SamplePolygonOverlayFactory()
    {
    }

    public static PolygonOverlay create(ChunkPos chunkCoords, ResourceKey<Level> dimension)
    {
        String groupName = "Slime Chunks";
        String label = String.format("Slime Chunk [%s,%s]", chunkCoords.x, chunkCoords.z);

        ShapeProperties shapeProps = new ShapeProperties()
                .setStrokeWidth(2)
                .setStrokeColor(0x00ff00).setStrokeOpacity(.7f)
                .setFillColor(0x00ff00).setFillOpacity(.4f);

        TextProperties textProps = new TextProperties()
                .setBackgroundColor(0x000022)
                .setBackgroundOpacity(.5f)
                .setColor(0x00ff00)
                .setOpacity(1f)
                .setMinZoom(2)
                .setFontShadow(true);

        MapPolygon polygon = PolygonHelper.createChunkPolygon(chunkCoords.x, 70, chunkCoords.z);

        PolygonOverlay slimeChunkOverlay = new PolygonOverlay(ExampleMod.MODID, dimension, shapeProps, polygon);
        slimeChunkOverlay.setOverlayGroupName(groupName)
                .setLabel(label)
                .setTextProperties(textProps);

        IOverlayListener overlayListener = new SlimeChunkListener(slimeChunkOverlay);
        slimeChunkOverlay.setOverlayListener(overlayListener);

        return slimeChunkOverlay;
    }

    static class SlimeChunkListener implements IOverlayListener
    {
        final PolygonOverlay overlay;
        final ShapeProperties sp;
        final int fillColor;
        final int strokeColor;
        final float strokeOpacity;

        SlimeChunkListener(final PolygonOverlay overlay)
        {
            this.overlay = overlay;
            sp = overlay.getShapeProperties();
            fillColor = sp.getFillColor();
            strokeColor = sp.getStrokeColor();
            strokeOpacity = sp.getStrokeOpacity();
        }

        @Override
        public void onActivate(UIState uiState)
        {
            resetShapeProperties();
        }

        @Override
        public void onDeactivate(UIState uiState)
        {
            resetShapeProperties();
        }

        @Override
        public void onMouseMove(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            sp.setStrokeColor(new Random().nextInt(0xffffff));
            sp.setStrokeOpacity(1f);
            String title = "%s blocks away";
            BlockPos playerLoc = Minecraft.getInstance().player.blockPosition();
            int distance = (int) Math.sqrt(playerLoc.distSqr(blockPosition));
            overlay.setTitle(String.format(title, distance));
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            resetShapeProperties();
            overlay.setTitle(null);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            sp.setFillColor(new Random().nextInt(0xffffff));
            return false;
        }

        @Override
        public void onOverlayMenuPopup(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, ModPopupMenu modPopupMenu)
        {
            modPopupMenu.addMenuItem("Reset", b -> resetShapeProperties());
        }

        private void resetShapeProperties()
        {
            sp.setFillColor(fillColor);
            sp.setStrokeColor(strokeColor);
            sp.setStrokeOpacity(strokeOpacity);
        }
    }
}
