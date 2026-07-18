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

package journeymap.api.v2.client.util;

import journeymap.api.v2.client.model.MapPolygon;
import journeymap.api.v2.client.model.MapPolygonWithHoles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.ChunkPos;

import javax.annotation.Nonnull;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collection;
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
     * Creates a polygon for the block coords, starting with the lower-left (southwest) corner
     * and going counter-clockwise.  The supplied coordinates don't need to be in order, just opposite.
     *
     * @param corner1 One corner of the desired rectangle
     * @param corner2 The opposite corner
     * @return polygon
     */
    public static MapPolygon createBlockRect(final BlockPos corner1, final BlockPos corner2)
    {
        final int minX = Math.min(corner1.getX(), corner2.getX());
        final int maxX = Math.max(corner1.getX(), corner2.getX());
        final int minZ = Math.min(corner1.getZ(), corner2.getZ());
        final int maxZ = Math.max(corner1.getZ(), corner2.getZ());

        final BlockPos sw = new BlockPos(minX, corner1.getY(), maxZ);
        final BlockPos se = new BlockPos(maxX, corner1.getY(), maxZ);
        final BlockPos ne = new BlockPos(maxX, corner2.getY(), minZ);
        final BlockPos nw = new BlockPos(minX, corner2.getY(), minZ);

        return new MapPolygon(sw, se, ne, nw);
    }

    /**
     * Given a collection of chunks, creates an {@link Area} that covers them.
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
     * Given a collection of chunks, creates one or more {@link MapPolygonWithHoles} that covers them.
     * (Just a convenience wrapper for the {@link Area}-based methods.)
     *
     * @param chunks The set of chunks.
     * @param y      The y-coordinate for the resulting polygons.
     * @return One or more polygons that cover the specified chunks.
     */
    @Nonnull
    public static List<MapPolygonWithHoles> createChunksPolygon(@Nonnull final Collection<ChunkPos> chunks, final int y)
    {
        return createPolygonFromArea(createChunksArea(chunks), y);
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
     * <p>
     * Note that this includes some point-simplification that currently only works if the area is
     * only made up of rectangular subregions -- i.e. all lines are perfectly horizontal or vertical.
     * If you do have diagonal lines this is mostly harmless; just might leave more points than are
     * strictly required.
     *
     * @param area The area to cover.
     * @param y    The y-coordinate.
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
     * <p>
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

        // Pair each hull with its filled area (nesting ignored) so a hole can be tested
        // for containment against it.
        final List<Tuple<MapPolygon, Area>> hullAreas = hulls.stream()
                .map(hull -> new Tuple<>(hull, toArea(hull)))
                .collect(Collectors.toList());

        final List<List<MapPolygon>> hullHoles = new ArrayList<>();
        for (int index = 0; index < hulls.size(); ++index)
        {
            hullHoles.add(new ArrayList<>());
        }

        for (final MapPolygon hole : holes)
        {
            final Area holeArea = toArea(hole);
            int owner = -1;
            long ownerArea = Long.MAX_VALUE;

            // A hole is carved from the innermost hull that fully contains it.  Containing
            // hulls are nested, so the one with the smallest ring area is the immediate
            // owner.  Nested hulls differ in area, so this pairing is independent of the
            // contour order.
            for (int index = 0; index < hullAreas.size(); ++index)
            {
                if (!contains(hullAreas.get(index).getB(), holeArea))
                {
                    continue;
                }
                final long area = ringArea(hullAreas.get(index).getA());
                if (owner < 0 || area < ownerArea)
                {
                    owner = index;
                    ownerArea = area;
                }
            }

            // Malformed input can leave a hole with no containing hull; drop it rather
            // than forcing it onto an unrelated hull.
            if (owner >= 0)
            {
                hullHoles.get(owner).add(hole);
            }
        }

        final List<MapPolygonWithHoles> result = new ArrayList<>();
        for (int index = 0; index < hulls.size(); ++index)
        {
            result.add(new MapPolygonWithHoles(hulls.get(index), hullHoles.get(index)));
        }

        return result;
    }

    /**
     * Determines whether the filled {@code hull} area fully contains the filled
     * {@code hole} area, i.e. the hole lies entirely inside the hull.  This is
     * stronger than a mere intersection: an island hull sitting inside a hole
     * intersects that hole without containing it.
     *
     * @param hull The candidate containing area.
     * @param hole The hole area.
     * @return True if the hole is fully contained by the hull.
     */
    private static boolean contains(@Nonnull final Area hull, @Nonnull final Area hole)
    {
        final Area remainder = new Area(hole);
        remainder.subtract(hull);
        return remainder.isEmpty();
    }

    /**
     * Twice the unsigned area enclosed by the polygon ring, via the shoelace formula.
     * Only used to rank containing hulls by size, so the constant factor of two is
     * irrelevant.
     *
     * @param polygon The polygon.
     * @return Twice the unsigned enclosed area.
     */
    private static long ringArea(@Nonnull final MapPolygon polygon)
    {
        long sum = 0;
        final List<BlockPos> points = polygon.getPoints();
        BlockPos a = points.get(points.size() - 1);
        for (final BlockPos b : points)
        {
            sum += (long) (b.getX() - a.getX()) * (b.getZ() + a.getZ());
            a = b;
        }
        return Math.abs(sum);
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
