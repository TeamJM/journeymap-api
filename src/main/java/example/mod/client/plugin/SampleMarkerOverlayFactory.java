package example.mod.client.plugin;

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
import java.util.Random;

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
    public static List<MarkerOverlay> create(BlockPos center, int points, double radius)
    {
        // Limiting the number of points possible so we can tint them using Minecraft's map color array
        points = Math.max(1, Math.min(points, 35));

        List<MarkerOverlay> list = new ArrayList<MarkerOverlay>(points);

        double slice = 2 * Math.PI / points;
        for (int i = 1; i <= points; i++)
        {
            // Create a position in a circle around the center position
            double angle = slice * i;
            int newX = (int) (center.getX() + radius * Math.cos(angle));
            int newZ = (int) (center.getZ() + radius * Math.sin(angle));
            BlockPos pos = new BlockPos(newX, 70, newZ);

            // Use the white cube image, anchor it so it will center on the position
            MapImage cube = new MapImage(new ResourceLocation("examplemod:images/cube.png"), 64, 64)
                    .setRotation((int) slice)
                    .centerAnchors();

            // Lets tint it using one Minecraft's map colors (starting with index of 1)
            cube.setColor(MapColor.mapColorArray[i].colorValue);

            // Finally we can build the overlay
            MarkerOverlay markerOverlay = new MarkerOverlay("journeymap", "marker" + i, pos, cube);
            markerOverlay.setDimension(0)
                    .setTitle(String.format("x:%s,z:%s", pos.getX(), pos.getZ()))
                    .setLabel("" + i);

            // Set the display order too in case they overlap
            markerOverlay.setDisplayOrder(100 + i);

            // Add a listener to it
            markerOverlay.setOverlayListener(new MarkerListener(markerOverlay));

            list.add(markerOverlay);
        }
        return list;
    }

    /**
     * Listener for events on a MarkerOverlay instance.
     *
     */
    static class MarkerListener implements IOverlayListener
    {
        final MarkerOverlay overlay;
        final int color;

        MarkerListener(final MarkerOverlay overlay)
        {
            this.overlay = overlay;
            this.color = overlay.getIcon().getColor();
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
            // Lets rotate it for no good reason!
            overlay.getIcon().setRotation((int) (System.currentTimeMillis() / 100 % 360));
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            refresh(uiState);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            // Make it bigger on each click, and random color too
            double size = overlay.getIcon().getDisplayWidth() + uiState.blockSize;
            overlay.getIcon()
                    .setColor(new Random().nextInt(0xffffff))
                    .setDisplayWidth(size)
                    .setDisplayHeight(size)
                    .centerAnchors();

            // Returning false will stop the click event from being used by other overlays,
            // including JM's invisible overlay for creating/selecting waypoints
            return false;
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
                    .setRotation(0)
                    .setColor(color)
                    .setDisplayWidth(size)
                    .setDisplayHeight(size)
                    .centerAnchors();
        }
    }
}
