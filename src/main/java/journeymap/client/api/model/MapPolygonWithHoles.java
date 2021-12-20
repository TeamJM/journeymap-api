package journeymap.client.api.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * This is a simple convenience wrapper for an outer {@link MapPolygon} and
 * a list of holes.
 */
public class MapPolygonWithHoles
{
    @Nonnull public final MapPolygon hull;
    @Nullable public final List<MapPolygon> holes;

    /**
     * Creates a MapPolygonWithHoles.
     *
     * @param hull The outer hull of the polygon.
     * @param holes Intersecting polygons representing holes in the hull.
     */
    public MapPolygonWithHoles(@Nonnull final MapPolygon hull,
                               @Nullable final List<MapPolygon> holes)
    {
        this.hull = hull;
        this.holes = holes;
    }
}
