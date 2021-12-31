package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.PolygonOverlay;
import journeymap.client.api.model.MapPolygon;
import journeymap.client.api.model.MapPolygonWithHoles;
import journeymap.client.api.model.ShapeProperties;
import journeymap.client.api.model.TextProperties;
import journeymap.client.api.util.PolygonHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

class SampleComplexPolygonOverlayFactory
{
    private SampleComplexPolygonOverlayFactory()
    {
    }

    /**
     * Create a bunch of random polygon overlays near a particular location, just to demonstrate some of the
     * additional helpers.
     */
    public static List<PolygonOverlay> create(IClientAPI jmAPI, BlockPos pos, ResourceKey<Level> dimension, int maxDistance)
    {
        final List<PolygonOverlay> result = new ArrayList<>();
        final Random random = ThreadLocalRandom.current();

        // Style the text
        TextProperties textProps = new TextProperties()
                .setBackgroundColor(0x000022)
                .setBackgroundOpacity(.5f)
                .setColor(0x00ff00)
                .setOpacity(1f)
                .setMinZoom(2)
                .setFontShadow(true);

        // an E shape
        {
            final ChunkPos center = new ChunkPos(new BlockPos(
                    pos.getX() + random.nextInt(maxDistance) - maxDistance / 2,
                    pos.getY(),
                    pos.getZ() + random.nextInt(maxDistance) - maxDistance / 2));
            final Set<ChunkPos> shape = new HashSet<>();
            shape.add(center);
            shape.add(new ChunkPos(center.x, center.z - 1));
            shape.add(new ChunkPos(center.x, center.z - 2));
            shape.add(new ChunkPos(center.x + 1, center.z - 2));
            shape.add(new ChunkPos(center.x + 2, center.z - 2));
            shape.add(new ChunkPos(center.x + 1, center.z));
            shape.add(new ChunkPos(center.x, center.z + 1));
            shape.add(new ChunkPos(center.x, center.z + 2));
            shape.add(new ChunkPos(center.x + 1, center.z + 2));
            shape.add(new ChunkPos(center.x + 2, center.z + 2));

            final List<MapPolygonWithHoles> polygons = PolygonHelper.createChunksPolygon(shape, pos.getY());

            // Style the polygon
            ShapeProperties shapeProps = new ShapeProperties()
                    .setStrokeWidth(2)
                    .setStrokeColor(0xff0000).setStrokeOpacity(.7f)
                    .setFillColor(0xff0000).setFillOpacity(.4f);

            // there should only be one, since our chunks were all contiguous, but just to demonstrate the general method
            result.addAll(createOverlays(jmAPI, dimension, "polyE", "E", polygons, textProps, shapeProps));
        }

        // merge two rectangles with a hole
        {
            final BlockPos corner = new BlockPos(
                    pos.getX() + random.nextInt(maxDistance) - maxDistance / 2,
                    pos.getY(),
                    pos.getZ() + random.nextInt(maxDistance) - maxDistance / 2);
            final MapPolygon rect1 = PolygonHelper.createBlockRect(corner, corner.east(20).south(14));
            final MapPolygon rect2 = PolygonHelper.createBlockRect(corner.east(10).north(4), corner.east(30).south(6));
            final MapPolygon rect3 = PolygonHelper.createBlockRect(corner.east(15).south(2), corner.east(18).south(4));

            final Area composite = PolygonHelper.toArea(rect1);
            composite.add(PolygonHelper.toArea(rect2));
            composite.subtract(PolygonHelper.toArea(rect3));

            final List<MapPolygonWithHoles> polygons = PolygonHelper.createPolygonFromArea(composite, pos.getY());

            // Style the polygon
            ShapeProperties shapeProps = new ShapeProperties()
                    .setStrokeWidth(2)
                    .setStrokeColor(0x0000ff).setStrokeOpacity(.7f)
                    .setFillColor(0x0000ff).setFillOpacity(.4f);

            // there still should only be one, since the hole didn't cut the whole shape into multiple pieces
            result.addAll(createOverlays(jmAPI, dimension, "polyRect", "box", polygons, textProps, shapeProps));
        }

        return result;
    }

    private static List<PolygonOverlay> createOverlays(IClientAPI jmAPI, ResourceKey<Level> dimension, String name, String label, List<MapPolygonWithHoles> polygons, TextProperties textProps, ShapeProperties shapeProps)
    {
        final List<PolygonOverlay> result = new ArrayList<>();

        try
        {
            if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
            {
                int index = 0;
                for (final MapPolygonWithHoles polygon : polygons)
                {
                    final String displayId = name + "_" + ++index;
                    final PolygonOverlay overlay = new PolygonOverlay(ExampleMod.MODID, displayId, dimension, shapeProps, polygon);
                    overlay.setOverlayGroupName(name)
                            .setTextProperties(textProps)
                            .setLabel(label);

                    result.add(overlay);
                    jmAPI.show(overlay);
                }
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }

        return result;
    }
}
