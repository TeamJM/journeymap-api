package journeymap.api.v2.client.util;

import journeymap.api.v2.client.model.MapPolygon;
import journeymap.api.v2.client.model.MapPolygonWithHoles;
import net.minecraft.util.math.BlockPos;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolygonHelperTest
{
    private static final int Y = 64;

    /**
     * The core regression: an island hull sitting inside another hull's hole.  The
     * hole must be carved from the enclosing square, not from the island whose fill
     * merely intersects it.  The island hull is fed first so the old
     * intersection-based pairing (which grabs the first hull it visits) mis-assigns
     * the hole to the island.
     */
    @Test
    void nestedIslandDoesNotStealTheEnclosingHullsHole()
    {
        final MapPolygon square = hull(0, 0, 300, 300);
        final MapPolygon bigHole = hole(30, 30, 270, 270);
        final MapPolygon island = hull(125, 125, 175, 175);

        final List<MapPolygonWithHoles> grouped =
                PolygonHelper.classifyAndGroup(Arrays.asList(island, bigHole, square));

        final MapPolygonWithHoles squareGroup = groupFor(grouped, square);
        final MapPolygonWithHoles islandGroup = groupFor(grouped, island);

        assertEquals(1, squareGroup.holes.size(), "the hole belongs to the enclosing square");
        assertSame(bigHole, squareGroup.holes.get(0));
        assertTrue(islandGroup.holes == null || islandGroup.holes.isEmpty(),
                "the island encloses no hole");

        // A stolen or dropped hole both break this: every (hull minus its holes) must
        // re-union to the original area.  Compare via Area.equals(Area) with assertTrue;
        // assertEquals would bind to Object.equals (identity) and never match.
        final Area expected = new Area(new Rectangle(0, 0, 300, 300));
        expected.subtract(new Area(new Rectangle(30, 30, 240, 240)));
        expected.add(new Area(new Rectangle(125, 125, 50, 50)));
        assertTrue(expected.equals(reunion(grouped)),
                "hull-minus-holes must reproduce the input area");
    }

    /**
     * A hole enclosed by two nested hulls belongs to the innermost (smallest) one.
     * The outer hole belongs to the outer hull; the inner hole belongs to the inner
     * hull, even though the outer hull's filled outline also contains it.
     */
    @Test
    void holeIsPairedWithTheInnermostContainingHull()
    {
        final MapPolygon outer = hull(0, 0, 300, 300);
        final MapPolygon outerHole = hole(30, 30, 270, 270);
        final MapPolygon inner = hull(60, 60, 240, 240);
        final MapPolygon innerHole = hole(90, 90, 210, 210);

        final List<MapPolygonWithHoles> grouped =
                PolygonHelper.classifyAndGroup(Arrays.asList(outer, outerHole, inner, innerHole));

        final MapPolygonWithHoles outerGroup = groupFor(grouped, outer);
        final MapPolygonWithHoles innerGroup = groupFor(grouped, inner);

        assertEquals(1, outerGroup.holes.size());
        assertSame(outerHole, outerGroup.holes.get(0));
        assertEquals(1, innerGroup.holes.size());
        assertSame(innerHole, innerGroup.holes.get(0));

        final Area expected = new Area(new Rectangle(0, 0, 300, 300));
        expected.subtract(new Area(new Rectangle(30, 30, 240, 240)));
        expected.add(new Area(new Rectangle(60, 60, 180, 180)));
        expected.subtract(new Area(new Rectangle(90, 90, 120, 120)));
        assertTrue(expected.equals(reunion(grouped)),
                "hull-minus-holes must reproduce the input area");
    }

    @Test
    void plainDonutKeepsItsHole()
    {
        final MapPolygon square = hull(0, 0, 100, 100);
        final MapPolygon donutHole = hole(25, 25, 75, 75);

        final List<MapPolygonWithHoles> grouped =
                PolygonHelper.classifyAndGroup(Arrays.asList(square, donutHole));

        assertEquals(1, grouped.size());
        assertEquals(1, grouped.get(0).holes.size());
        assertSame(donutHole, grouped.get(0).holes.get(0));
    }

    @Test
    void severalNonNestedHolesAllGroupWithTheirHull()
    {
        final MapPolygon square = hull(0, 0, 100, 100);
        final MapPolygon holeA = hole(10, 10, 30, 30);
        final MapPolygon holeB = hole(60, 60, 90, 90);

        final List<MapPolygonWithHoles> grouped =
                PolygonHelper.classifyAndGroup(Arrays.asList(square, holeA, holeB));

        assertEquals(1, grouped.size());
        final List<MapPolygon> holes = grouped.get(0).holes;
        assertEquals(2, holes.size());
        assertTrue(holes.contains(holeA));
        assertTrue(holes.contains(holeB));
    }

    @Test
    void holeWithNoContainingHullIsDropped()
    {
        final MapPolygon square = hull(0, 0, 100, 100);
        final MapPolygon orphan = hole(200, 200, 250, 250);   // lies entirely outside the hull

        final List<MapPolygonWithHoles> grouped =
                PolygonHelper.classifyAndGroup(Arrays.asList(square, orphan));

        assertEquals(1, grouped.size());
        assertTrue(grouped.get(0).holes.isEmpty(), "an unenclosed hole is dropped, not forced onto a hull");

        final Area expected = new Area(new Rectangle(0, 0, 100, 100));
        assertTrue(expected.equals(reunion(grouped)), "dropping the orphan leaves the hull's fill intact");
    }

    /**
     * A hole that pokes outside every hull (possible only for arbitrary input, not for the
     * normalized {@code Area} contours {@code createPolygonFromArea} emits) is contained by no
     * hull, so it falls back to the first hull it intersects.  This preserves the pre-fix
     * behavior for {@code classifyAndGroup}'s documented arbitrary-polygon input: the overlapping
     * quadrant is carved rather than the whole hull rendering solid.
     */
    @Test
    void partiallyOverlappingHoleFallsBackToTheHullItIntersects()
    {
        final MapPolygon square = hull(0, 0, 100, 100);
        final MapPolygon overhangingHole = hole(50, 50, 150, 150);   // only its lower-left quadrant is inside

        final List<MapPolygonWithHoles> grouped =
                PolygonHelper.classifyAndGroup(Arrays.asList(square, overhangingHole));

        assertEquals(1, grouped.size());
        assertEquals(1, grouped.get(0).holes.size(),
                "the partially overlapping hole falls back to the hull it intersects");
        assertSame(overhangingHole, grouped.get(0).holes.get(0));

        final Area expected = new Area(new Rectangle(0, 0, 100, 100));
        expected.subtract(new Area(new Rectangle(50, 50, 100, 100)));   // only the overlap is carved
        assertTrue(expected.equals(reunion(grouped)),
                "the overlapping quadrant is carved from the hull");
    }

    private static MapPolygon hull(final int minX, final int minZ, final int maxX, final int maxZ)
    {
        // createBlockRect emits counter-clockwise winding, which classifyAndGroup treats as a hull.
        return PolygonHelper.createBlockRect(new BlockPos(minX, Y, minZ), new BlockPos(maxX, Y, maxZ));
    }

    private static MapPolygon hole(final int minX, final int minZ, final int maxX, final int maxZ)
    {
        // Reversing the winding turns the same rectangle into a clockwise hole.
        final List<BlockPos> points = new ArrayList<>(hull(minX, minZ, maxX, maxZ).getPoints());
        Collections.reverse(points);
        return new MapPolygon(points);
    }

    private static MapPolygonWithHoles groupFor(final List<MapPolygonWithHoles> groups, final MapPolygon hull)
    {
        return groups.stream()
                .filter(group -> group.hull == hull)
                .findFirst()
                .orElseThrow(() -> new AssertionError("no group for the given hull"));
    }

    private static Area reunion(final List<MapPolygonWithHoles> groups)
    {
        final Area total = new Area();
        for (final MapPolygonWithHoles group : groups)
        {
            final Area filled = PolygonHelper.toArea(group.hull);
            if (group.holes != null)
            {
                for (final MapPolygon hole : group.holes)
                {
                    filled.subtract(PolygonHelper.toArea(hole));
                }
            }
            total.add(filled);
        }
        return total;
    }
}
