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
import net.minecraftforge.fml.client.FMLClientHandler;

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
        IOverlayListener overlayListener = createOverlayListener(slimeChunkOverlay);
        slimeChunkOverlay.setOverlayListener(overlayListener);

        return slimeChunkOverlay;
    }

    /**
     * Create a listener for events related to an overlay instance.
     *
     * @param overlay the overlay
     * @return a new event listener
     */
    private static IOverlayListener createOverlayListener(final PolygonOverlay overlay)
    {
        return new IOverlayListener()
        {
            @Override
            public void onActivate(UIState mapState)
            {
                ExampleMod.LOGGER.info("onActivate " + overlay.getTitle());
            }

            @Override
            public void onDeactivate(UIState mapState)
            {
                ExampleMod.LOGGER.info("onDeactivate " + overlay.getTitle());
            }

            @Override
            public void onMouseMove(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition)
            {
                ExampleMod.LOGGER.info("onMouseMove " + overlay.getTitle());

                // Show the title
                int color = overlay.getTextProperties().getColor();
                FMLClientHandler.instance().getClient().fontRendererObj.drawString(
                        overlay.getTitle(),
                        (float) mousePosition.x,
                        (float) mousePosition.y,
                        color, true);
            }

            @Override
            public void onMouseClick(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
            {
                ExampleMod.LOGGER.info("onMouseClick " + overlay.getTitle());

                // Toggle opacity on click ... just because
                float newOpacity = overlay.getShapeProperties().getFillOpacity() > 0f ? 0f : .5f;
                overlay.getShapeProperties().setFillOpacity(newOpacity);
            }
        };
    }

}
