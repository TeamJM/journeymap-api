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
import journeymap.api.v2.common.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

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

    public static List<PolygonOverlay> create(IClientAPI jmAPI, BlockPos pos, int dimension, int maxDistance)
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
            // 1.7.10 has no ChunkPos(BlockPos) constructor; derive the chunk coordinate the same
            // way that constructor does on later versions (block coordinate >> 4).
            final int blockX = pos.getX() + random.nextInt(maxDistance) - maxDistance / 2;
            final int blockZ = pos.getZ() + random.nextInt(maxDistance) - maxDistance / 2;
            final ChunkCoordIntPair center = new ChunkCoordIntPair(blockX >> 4, blockZ >> 4);
            final int cx = center.chunkXPos;
            final int cz = center.chunkZPos;
            final Set<ChunkCoordIntPair> shape = new HashSet<>();
            shape.add(center);
            shape.add(new ChunkCoordIntPair(cx, cz - 1));
            shape.add(new ChunkCoordIntPair(cx, cz - 2));
            shape.add(new ChunkCoordIntPair(cx + 1, cz - 2));
            shape.add(new ChunkCoordIntPair(cx + 2, cz - 2));
            shape.add(new ChunkCoordIntPair(cx + 1, cz));
            shape.add(new ChunkCoordIntPair(cx, cz + 1));
            shape.add(new ChunkCoordIntPair(cx, cz + 2));
            shape.add(new ChunkCoordIntPair(cx + 1, cz + 2));
            shape.add(new ChunkCoordIntPair(cx + 2, cz + 2));

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
            // The BlockPos shim only implements the members common/src/main actually calls (no
            // east()/south()/north()); east is +X, south is +Z, north is -Z, so build the offset
            // corners directly.
            final MapPolygon rect1 = PolygonHelper.createBlockRect(corner,
                    new BlockPos(corner.getX() + 20, corner.getY(), corner.getZ() + 14));
            final MapPolygon rect2 = PolygonHelper.createBlockRect(
                    new BlockPos(corner.getX() + 10, corner.getY(), corner.getZ() - 4),
                    new BlockPos(corner.getX() + 30, corner.getY(), corner.getZ() + 6));
            final MapPolygon rect3 = PolygonHelper.createBlockRect(
                    new BlockPos(corner.getX() + 15, corner.getY(), corner.getZ() + 2),
                    new BlockPos(corner.getX() + 18, corner.getY(), corner.getZ() + 4));

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

    private static List<PolygonOverlay> createOverlays(IClientAPI jmAPI, int dimension, String name, String label, List<MapPolygonWithHoles> polygons, TextProperties textProps, ShapeProperties shapeProps)
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
