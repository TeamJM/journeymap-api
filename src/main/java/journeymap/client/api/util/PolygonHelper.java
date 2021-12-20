/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package journeymap.client.api.util;

import journeymap.client.api.model.MapPolygon;
import journeymap.client.api.model.MapPolygonWithHoles;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class related to Polygons.
 */
public class PolygonHelper
{
    /**
     * Creates a polygon for the chunk containing worldCoords, starting with the lower-left (southwest) corner
     * and going counter-clockwise.
     *
     * @param x world X
     * @param y world Y
     * @param z world Z
     * @return a polygon for the surrounding chunk
     */
    public static MapPolygon createChunkPolygonForWorldCoords(int x, int y, int z)
    {
        return createChunkPolygon(x >> 4, y, z >> 4);
    }

    /**
     * Creates a polygon for the chunk coords, starting with the lower-left (southwest) corner
     * and going counter-clockwise.
     *
     * @param chunkX chunk x
     * @param y      block y
     * @param chunkZ chunk z
     * @return polygon
     */
    public static MapPolygon createChunkPolygon(int chunkX, int y, int chunkZ)
    {
        int x = chunkX << 4;
        int z = chunkZ << 4;
        BlockPos sw = new BlockPos(x, y, z + 16);
        BlockPos se = new BlockPos(x + 16, y, z + 16);
        BlockPos ne = new BlockPos(x + 16, y, z);
        BlockPos nw = new BlockPos(x, y, z);

        return new MapPolygon(sw, se, ne, nw);
    }

    /**
     * Given a collection of chunks, creates an Area that covers them.
     *
     * @param chunks The set of chunks.
     * @return An Area of the corresponding block coordinates.
     */
    @Nonnull
    public static Area createChunksArea(@Nonnull final Collection<ChunkPos> chunks)
    {
        final Area area = new Area();
        for (final ChunkPos chunkPos : chunks)
        {
            area.add(new Area(new Rectangle(chunkPos.getMinBlockX(), chunkPos.getMinBlockZ(), 16, 16)));
        }
        return area;
    }

    /**
     * Converts a {@link MapPolygon} into an {@link Area} (keeping XZ coords only).
     *
     * @param polygon The polygon.
     * @return The corresponding area.
     */
    @Nonnull
    public static Area toArea(@Nonnull final MapPolygon polygon)
    {
        final List<BlockPos> points = polygon.getPoints();
        final int[] xPoints = new int[points.size()];
        final int[] yPoints = new int[points.size()];

        for (int i = 0; i < points.size(); ++i)
        {
            xPoints[i] = points.get(i).getX();
            yPoints[i] = points.get(i).getZ();
        }

        return new Area(new Polygon(xPoints, yPoints, points.size()));
    }

    /**
     * Creates a set of {@link MapPolygonWithHoles} from the given {@link Area} (XZ block
     * coords) and the given Y coord.
     *
     * Note that this includes some point-simplification that currently only works if the area is
     * only made up of rectangular subregions -- i.e. all lines are perfectly horizontal or vertical.
     * If you do have diagonal lines this is mostly harmless; just might leave more points than are
     * strictly required.
     *
     * @param area The area to cover.
     * @param y The y-coordinate.
     * @return The polygons.
     */
    @Nonnull
    public static List<MapPolygonWithHoles> createPolygonFromArea(@Nonnull final Area area, final int y)
    {
        final List<MapPolygon> polygons = new ArrayList<>();
        List<BlockPos> poly = new ArrayList<>();
        final PathIterator iterator = area.getPathIterator(null);
        final float[] points = new float[6];
        while (!iterator.isDone())
        {
            final int type = iterator.currentSegment(points);
            switch (type)
            {
                case PathIterator.SEG_MOVETO:
                    if (!poly.isEmpty())
                    {
                        poly = simplify(poly);
                        polygons.add(new MapPolygon(poly));
                        poly = new ArrayList<>();
                    }
                    poly.add(new BlockPos(Math.round(points[0]), y, Math.round(points[1])));
                    break;
                case PathIterator.SEG_LINETO:
                    poly.add(new BlockPos(Math.round(points[0]), y, Math.round(points[1])));
                    break;
            }
            iterator.next();
        }
        if (!poly.isEmpty())
        {
            polygons.add(new MapPolygon(poly));
        }

        return classifyAndGroup(polygons);
    }

    /**
     * Given an arbitrary list of polygons, determine which are hulls and holes and which holes are
     * associated with which hulls.
     *
     * Assumes that hulls use CCW point winding and holes use CW point winding, which seems to be
     * consistent with {@link #createPolygonFromArea}.
     *
     * @param polygons The input list of {@link MapPolygon}s.
     * @return The resulting list of {@link MapPolygonWithHoles}.
     */
    @Nonnull
    public static List<MapPolygonWithHoles> classifyAndGroup(@Nonnull final List<MapPolygon> polygons)
    {
        final List<MapPolygon> hulls = new ArrayList<>();
        final List<MapPolygon> holes = new ArrayList<>();

        for (final MapPolygon polygon : polygons)
        {
            if (isHole(polygon))
            {
                holes.add(polygon);
            }
            else
            {
                hulls.add(polygon);
            }
        }

        final List<Tuple<MapPolygon, Area>> holeAreas = holes.stream()
                .map(hole -> new Tuple<>(hole, toArea(hole)))
                .collect(Collectors.toList());

        final List<MapPolygonWithHoles> result = new ArrayList<>();
        for (final MapPolygon hull : hulls)
        {
            final Area hullArea = toArea(hull);
            final List<MapPolygon> hullHoles = new ArrayList<>();

            for (final Iterator<Tuple<MapPolygon, Area>> iterator = holeAreas.iterator(); iterator.hasNext(); )
            {
                final Tuple<MapPolygon, Area> holeArea = iterator.next();
                final Area intersection = new Area(hullArea);
                intersection.intersect(holeArea.getB());
                if (!intersection.isEmpty())
                {
                    hullHoles.add(holeArea.getA());
                    iterator.remove();
                }
            }

            result.add(new MapPolygonWithHoles(hull, hullHoles));
        }

        return result;
    }

    /**
     * The input tends to have points for each chunk, even along a straight line.
     * Remove the unneeded intermediate points.  Currently this only works along
     * purely horizontal/vertical lines, not diagonals.
     *
     * @param points The input points
     * @return The filtered points
     */
    @Nonnull
    private static List<BlockPos> simplify(@Nonnull final List<BlockPos> points)
    {
        final List<BlockPos> result = new ArrayList<>();
        BlockPos prev2 = points.get(0);
        BlockPos prev1 = points.get(1);
        result.add(prev2);
        for (int index = 2; index < points.size(); ++index)
        {
            final BlockPos next = points.get(index);
            if (prev2.getX() == prev1.getX() && prev1.getX() == next.getX())
            {
                // merge horizontal line by skipping the middle point
                prev1 = next;
            }
            else if (prev2.getZ() == prev1.getZ() && prev1.getZ() == next.getZ())
            {
                // merge vertical line by skipping the middle point
                prev1 = next;
            }
            else
            {
                // corner; keep the point
                result.add(prev1);
                prev2 = prev1;
                prev1 = next;
            }
        }
        result.add(prev1);
        return result;
    }

    /**
     * Determine if the given polygon is a "hole".  Holes have CW point winding.
     * Assumes that +X is "right" and +Z is "down".
     *
     * @param polygon The polygon.
     * @return True if it's a hole.
     */
    private static boolean isHole(@Nonnull final MapPolygon polygon)
    {
        // from https://stackoverflow.com/a/18472899/43534
        long sum = 0;
        final List<BlockPos> points = polygon.getPoints();
        BlockPos a = points.get(points.size() - 1);
        for (final BlockPos b : points)
        {
            sum += (long) (b.getX() - a.getX()) * (b.getZ() + a.getZ());
            a = b;
        }
        return sum < 0;
    }
}
