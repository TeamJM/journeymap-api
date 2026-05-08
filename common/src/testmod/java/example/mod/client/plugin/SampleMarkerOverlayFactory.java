/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.display.IOverlayListener;
import journeymap.api.v2.client.display.MarkerOverlay;
import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.model.MapImage;
import journeymap.api.v2.client.util.UIState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.MapColor;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sample factory that generates a list of MarkerOverlays.
 */
public final class SampleMarkerOverlayFactory
{
    private SampleMarkerOverlayFactory()
    {
    }

    public static List<MarkerOverlay> create(IClientAPI jmAPI, BlockPos center, int quantity, int maxDistance)
    {
        ResourceLocation sprites = ResourceLocation.parse("examplemod:images/sprites.png");
        int spriteX = 0, spriteY = 0;
        int iconSize = 64;
        int iconColumns = 8;
        int iconRows = 4;

        List<MarkerOverlay> list = new ArrayList<>();
        Random random = new Random();
        int minX = center.getX() - maxDistance;
        int minZ = center.getZ() - maxDistance;

        int colorIndex = 0;
        for (int i = 0; i < quantity; i++)
        {
            BlockPos pos = new BlockPos(minX + random.nextInt(maxDistance), 70, minZ + random.nextInt(maxDistance));

            colorIndex++;
            if (colorIndex > 35)
            {
                colorIndex = 1;
            }
            int color = MapColor.byId(colorIndex).col;

            MapImage icon = new MapImage(sprites, spriteX, spriteY, iconSize, iconSize, color, 1f);
            icon.centerAnchors();
            MarkerOverlay markerOverlay = new MarkerOverlay(ExampleMod.MODID, pos, icon);
            markerOverlay.setDimension(Level.OVERWORLD).setTitle("Marker Overlay").setLabel("" + i);
            markerOverlay.setOverlayListener(new MarkerListener(jmAPI, markerOverlay));

            try
            {
                jmAPI.show(markerOverlay);
                list.add(markerOverlay);
            }
            catch (Exception e)
            {
                ExampleMod.LOGGER.error("Can't add marker overlay", e);
            }

            spriteX += iconSize;
            if (spriteX >= (iconSize * iconColumns))
            {
                spriteX = 0;
                spriteY += iconSize;
            }
            if (spriteY >= (iconSize * iconRows))
            {
                spriteY = 0;
            }
        }

        return list;
    }

    static class MarkerListener implements IOverlayListener
    {
        final IClientAPI jmAPI;
        final MarkerOverlay overlay;
        final int color;
        final float opacity;

        MarkerListener(IClientAPI jmAPI, final MarkerOverlay overlay)
        {
            this.jmAPI = jmAPI;
            this.overlay = overlay;
            this.color = overlay.getIcon().getColor();
            this.opacity = overlay.getIcon().getOpacity();
        }

        @Override
        public void onActivate(UIState uiState)
        {
            refresh(uiState);
        }

        @Override
        public void onDeactivate(UIState uiState)
        {
            refresh(uiState);
        }

        @Override
        public void onMouseMove(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            double size = uiState.blockSize * 10;

            if (overlay.getIcon().getDisplayWidth() != size)
            {
                overlay.getIcon()
                        .setDisplayWidth(size)
                        .setDisplayHeight(size)
                        .setAnchorX(size / 2)
                        .setAnchorY(size);
                overlay.flagForRerender();
            }
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            refresh(uiState);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            jmAPI.remove(overlay);
            return true;
        }

        @Override
        public void onOverlayMenuPopup(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, ModPopupMenu modPopupMenu)
        {
            modPopupMenu.addMenuItem("Delete", b -> jmAPI.remove(overlay));
        }

        private void refresh(UIState uiState)
        {
            double size = uiState.blockSize * 8;
            overlay.getIcon()
                    .setColor(color)
                    .setOpacity(opacity)
                    .setDisplayWidth(size)
                    .setDisplayHeight(size)
                    .setAnchorX(size / 2)
                    .setAnchorY(size);
            overlay.flagForRerender();
        }
    }
}
