package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymapapi.client.api.display.IOverlayListener;
import journeymapapi.client.api.display.PolygonOverlay;
import journeymapapi.client.api.model.MapPolygon;
import journeymapapi.client.api.model.ShapeProperties;
import journeymapapi.client.api.model.TextProperties;
import journeymapapi.client.api.util.PolygonHelper;
import journeymapapi.client.api.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Sample factory that produces PolygonOverlays used to show which chunks likely to spawn slime.
 */
class SamplePolygonOverlayFactory
{
    /**
     * Create an overlay for the given chunk coords.
     *
     * @param chunkCoords chunk location
     * @param dimension   chunk dimension
     * @return a new overlay
     */
    static PolygonOverlay create(ChunkPos chunkCoords, ResourceKey<Level> dimension)
    {
        String displayId = "slime_" + chunkCoords.toString();
        String groupName = "Slime Chunks";
        String label = String.format("Slime Chunk [%s,%s]", chunkCoords.x, chunkCoords.z);

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
                .setMinZoom(2)
                .setFontShadow(true);

        // Define the shape
        MapPolygon polygon = PolygonHelper.createChunkPolygon(chunkCoords.x, 70, chunkCoords.z);

        // Create the overlay
        PolygonOverlay slimeChunkOverlay = new PolygonOverlay(ExampleMod.MODID, displayId, dimension, shapeProps, polygon);

        // Set the text
        slimeChunkOverlay.setOverlayGroupName(groupName)
                .setLabel(label)
                .setTextProperties(textProps);

        // Add a listener for mouse events
        IOverlayListener overlayListener = new SlimeChunkListener(slimeChunkOverlay);
        slimeChunkOverlay.setOverlayListener(overlayListener);

        return slimeChunkOverlay;
    }

    /**
     * Listener for events on a slime chunk overlay instance.
     */
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
            // Reset
            resetShapeProperties();
        }

        @Override
        public void onDeactivate(UIState uiState)
        {
            // Reset
            resetShapeProperties();
        }

        @Override
        public void onMouseMove(UIState uiState, Point2D.Double mousePosition, BlockPos blockPosition)
        {
            // Random stroke and make it opaque just to prove this works
            sp.setStrokeColor(new Random().nextInt(0xffffff));
            sp.setStrokeOpacity(1f);

            // Update title
            String title = "%s blocks away";
            net.minecraft.core.BlockPos playerLoc = Minecraft.getInstance().player.blockPosition();
            int distance = (int) Math.sqrt(playerLoc.distSqr(blockPosition));
            overlay.setTitle(String.format(title, distance));
        }

        @Override
        public void onMouseOut(UIState uiState, Point2D.Double mousePosition, net.minecraft.core.BlockPos blockPosition)
        {
            // Reset
            resetShapeProperties();
            overlay.setTitle(null);
        }

        @Override
        public boolean onMouseClick(UIState uiState, Point2D.Double mousePosition, net.minecraft.core.BlockPos blockPosition, int button, boolean doubleClick)
        {
            // Random color on click just to prove the event works.
            sp.setFillColor(new Random().nextInt(0xffffff));

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
