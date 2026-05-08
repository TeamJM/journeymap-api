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
import journeymap.api.v2.client.display.DisplayType;
import journeymap.api.v2.client.display.PolygonOverlay;
import journeymap.api.v2.client.model.MapPolygon;
import journeymap.api.v2.client.model.MapPolygonWithHoles;
import journeymap.api.v2.client.model.ShapeProperties;
import journeymap.api.v2.client.model.TextProperties;
import journeymap.api.v2.client.util.PolygonHelper;
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

public final class SampleComplexPolygonOverlayFactory
{
    private SampleComplexPolygonOverlayFactory()
    {
    }

    public static List<PolygonOverlay> create(IClientAPI jmAPI, BlockPos pos, ResourceKey<Level> dimension, int maxDistance)
    {
        final List<PolygonOverlay> result = new ArrayList<>();
        final Random random = ThreadLocalRandom.current();

        TextProperties textProps = new TextProperties()
                .setBackgroundColor(0x000022)
                .setBackgroundOpacity(.5f)
                .setColor(0x00ff00)
                .setOpacity(1f)
                .setMinZoom(2)
                .setFontShadow(true);

        // an E shape
        {
            final ChunkPos center = ChunkPos.containing(new BlockPos(
                    pos.getX() + random.nextInt(maxDistance) - maxDistance / 2,
                    pos.getY(),
                    pos.getZ() + random.nextInt(maxDistance) - maxDistance / 2));
            final int cx = center.x();
            final int cz = center.z();
            final Set<ChunkPos> shape = new HashSet<>();
            shape.add(center);
            shape.add(new ChunkPos(cx, cz - 1));
            shape.add(new ChunkPos(cx, cz - 2));
            shape.add(new ChunkPos(cx + 1, cz - 2));
            shape.add(new ChunkPos(cx + 2, cz - 2));
            shape.add(new ChunkPos(cx + 1, cz));
            shape.add(new ChunkPos(cx, cz + 1));
            shape.add(new ChunkPos(cx, cz + 2));
            shape.add(new ChunkPos(cx + 1, cz + 2));
            shape.add(new ChunkPos(cx + 2, cz + 2));

            final List<MapPolygonWithHoles> polygons = PolygonHelper.createChunksPolygon(shape, pos.getY());

            ShapeProperties shapeProps = new ShapeProperties()
                    .setStrokeWidth(2)
                    .setStrokeColor(0xff0000).setStrokeOpacity(.7f)
                    .setFillColor(0xff0000).setFillOpacity(.4f);

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

            ShapeProperties shapeProps = new ShapeProperties()
                    .setStrokeWidth(2)
                    .setStrokeColor(0x0000ff).setStrokeOpacity(.7f)
                    .setFillColor(0x0000ff).setFillOpacity(.4f);

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
                    final PolygonOverlay overlay = new PolygonOverlay(ExampleMod.MODID, dimension, shapeProps, polygon);
                    overlay.setOverlayGroupName(name)
                            .setTextProperties(textProps)
                            .setLabel(label);

                    result.add(overlay);
                    jmAPI.show(overlay);
                    index++;
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
