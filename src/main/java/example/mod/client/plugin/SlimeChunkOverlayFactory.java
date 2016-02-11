package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.display.IOverlayListener;
import journeymap.client.api.display.PolygonOverlay;
import journeymap.client.api.model.MapPolygon;
import journeymap.client.api.model.ShapeProperties;
import journeymap.client.api.model.TextProperties;
import journeymap.client.api.util.PolygonHelper;
import journeymap.client.api.util.UIState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

import java.awt.geom.Point2D;

/**
 * Factory for overlays showing chunks likely to spawn slime.
 */
public class SlimeChunkOverlayFactory
{
    /**
     * Create an overlay for the given chunk coords.
     *
     * @param chunkCoords chunk location
     * @param dimension   chunk dimension
     * @return a new overlay
     */
    public static PolygonOverlay create(ChunkCoordIntPair chunkCoords, int dimension)
    {
        String displayId = "slime_" + chunkCoords.toString();
        String groupName = "Slime Chunks";
        String label = "Slime Chunk";
        String title = String.format("%s,%s", chunkCoords.chunkXPos, chunkCoords.chunkZPos);

        // Style the polygon
        ShapeProperties shapeProps = new ShapeProperties()
                .setStrokeWidth(2)
                .setStrokeColor(0x00ff00).setStrokeOpacity(.7f)
                .setFillColor(0x00ff00).setFillOpacity(.4f);

        // Style the text
        TextProperties textProps = new TextProperties()
                .setBackgroundColor(0x000022)
                .setBackgroundOpacity(.5f)
                .setColor(0x00ff00)
                .setOpacity(1f)
                .setFontShadow(true);

        // Define the shape
        MapPolygon polygon = PolygonHelper.createChunkPolygon(chunkCoords.chunkXPos, 70, chunkCoords.chunkZPos);

        // Create the overlay
        PolygonOverlay slimeChunkOverlay = new PolygonOverlay(ExampleMod.MODID, displayId, dimension, shapeProps, polygon);

        // Set the text
        slimeChunkOverlay.setOverlayGroupName(groupName)
                .setLabel(label)
                .setTitle(title)
                .setTextProperties(textProps);

        // Add a listener for mouse events
        IOverlayListener overlayListener = new SlimeChunkListener(slimeChunkOverlay);
        slimeChunkOverlay.setOverlayListener(overlayListener);

        return slimeChunkOverlay;
    }

    /**
     * Listener for events on a slime chunk overlay instance.
     *
     */
    static class SlimeChunkListener implements IOverlayListener
    {
        final ShapeProperties sp;
        final int fillColor;
        final int strokeColor;
        final float strokeOpacity;

        SlimeChunkListener(final PolygonOverlay overlay)
        {
            sp = overlay.getShapeProperties();
            fillColor = sp.getFillColor();
            strokeColor = sp.getStrokeColor();
            strokeOpacity = sp.getStrokeOpacity();
        }

        @Override
        public void onActivate(UIState mapState)
        {
            // Reset
            resetShapeProperties();
        }

        @Override
        public void onDeactivate(UIState mapState)
        {
            // Reset
            resetShapeProperties();
        }

        @Override
        public void onMouseMove(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            // Invert the stroke and make it opaque just to prove this works
            sp.setStrokeColor(0xFFFFFF - strokeColor);
            sp.setStrokeOpacity(1f);
        }

        @Override
        public void onMouseOut(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            // Reset
            resetShapeProperties();
        }

        @Override
        public boolean onMouseClick(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
        {
            // Toggle color on click just to prove it works.
            sp.setFillColor(0xFFFFFF - fillColor);

            // Returning false will stop the click event from being used by other overlays,
            // including JM's invisible overlay for creating/selecting waypoints
            return false;
        }

        /**
         * Reset properties back to original
         */
        private void resetShapeProperties()
        {
            sp.setFillColor(fillColor);
            sp.setStrokeColor(strokeColor);
            sp.setStrokeOpacity(strokeOpacity);
        }
    }
}
