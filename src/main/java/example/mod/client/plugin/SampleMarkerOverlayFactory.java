package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.IOverlayListener;
import journeymap.client.api.display.MarkerOverlay;
import journeymap.client.api.model.MapImage;
import journeymap.client.api.util.UIState;
import net.minecraft.block.material.MapColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample factory that generates a list of MarkerOverlays.
 */
public class SampleMarkerOverlayFactory
{
    /**
     * Create a circle of MarkerOverlays around the given BlockPos.
     * For absolutely no good reason, just showing how the pieces fit together.
     *
     * @param center    center position
     * @param points    how many markers, up to 35
     * @param radius    distance from center
     * @return list of MarkerOverlays
     */
    public static List<MarkerOverlay> create(IClientAPI jmClientAPI, BlockPos center, int points, double radius)
    {
        // Limiting the number of points possible so we can tint them using Minecraft's map color array
        points = Math.max(1, Math.min(points, 35));

        // Use a sprite sheet to vary the icons
        ResourceLocation sprites = new ResourceLocation("examplemod:images/sprites.png");
        int spriteX = 0, spriteY = 0;
        int iconSize = 64;
        int iconColumns = 8;

        List<MarkerOverlay> list = new ArrayList<MarkerOverlay>(points);

        double slice = 2 * Math.PI / points;
        for (int i = 1; i <= points; i++)
        {
            // Create a position in a circle around the center position
            double angle = slice * i;
            int newX = (int) (center.getX() + radius * Math.cos(angle));
            int newZ = (int) (center.getZ() + radius * Math.sin(angle));
            BlockPos pos = new BlockPos(newX, 70, newZ);

            // Lets tint the icon using one Minecraft's map colors (starting with index of 1)
            int color = MapColor.mapColorArray[i].colorValue;

            MapImage icon = new MapImage(sprites, spriteX, spriteY, iconSize, iconSize, color, 1f)
                    .centerAnchors();

            // Build the overlay
            MarkerOverlay markerOverlay = new MarkerOverlay(ExampleMod.MODID, "sampleMarker" + i, pos, icon);
            markerOverlay.setDimension(0)
                    .setTitle(String.format("x:%s,z:%s", pos.getX(), pos.getZ()))
                    .setLabel("" + i);

            // Set the display order too in case they overlap
            markerOverlay.setDisplayOrder(100 + i);

            // Add a listener to it
            markerOverlay.setOverlayListener(new MarkerListener(jmClientAPI, markerOverlay));

            // Add to list
            try
            {
                jmClientAPI.show(markerOverlay);
                list.add(markerOverlay);
            }
            catch (Exception e)
            {
                ExampleMod.LOGGER.error("Can't add marker overlay", e);
            }

            // Set next sprite coords
            spriteX += iconSize;
            if (spriteX >= (iconSize * iconColumns))
            {
                spriteX = 0;
                spriteY += iconSize;
            }
        }

        return list;
    }

    /**
     * Listener for events on a MarkerOverlay instance.
     *
     */
    static class MarkerListener implements IOverlayListener
    {
        final IClientAPI jmClientAPI;
        final MarkerOverlay overlay;
        final int color;
        final float opacity;

        MarkerListener(IClientAPI jmClientAPI, final MarkerOverlay overlay)
        {
            this.jmClientAPI = jmClientAPI;
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
            // Scale the icon larger and change color and opacity
            double size = uiState.blockSize * 10;

            // Update the display dimensions and re-center the anchors so the icon
            // will be centered over it's BlockPos
            overlay.getIcon()
                    .setColor(0xffffff)
                    .setOpacity(.5f)
                    .setDisplayWidth(size)
                    .setDisplayHeight(size)
                    .centerAnchors();
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            refresh(uiState);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            // Remove it on click
            jmClientAPI.remove(overlay);

            // Returning true will allow the click event to be used by other overlays
            return true;
        }

        /**
         * Reset properties back to originals, scale display size to zoom level
         */
        private void refresh(UIState uiState)
        {
            // Lets scale the icon to only span 8 blocks, regardless of zoom level
            double size = uiState.blockSize * 8;

            // Update the display dimensions and re-center the anchors so the icon
            // will be centered over it's BlockPos
            overlay.getIcon()
                    .setColor(color)
                    .setOpacity(opacity)
                    .setDisplayWidth(size)
                    .setDisplayHeight(size)
                    .centerAnchors();
        }
    }
}
