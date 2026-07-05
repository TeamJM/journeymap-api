package journeymap.api.v2.server.overlay;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

/**
 * One logical overlay pushed by a server addon. May contain many disjoint
 * {@link OverlayPolygon}s sharing one set of {@link OverlayShapeProps}.
 *
 * <p>The {@code overlayId} is a stable, addon-chosen string. Re-calling
 * {@link IServerOverlayAPI#show} with the same {@code (modId, overlayId)} pair
 * replaces any prior version on the client; calling
 * {@link IServerOverlayAPI#remove} removes it.</p>
 */
public final class ServerPolygon
{
    private final String overlayId;
    private final ResourceKey<Level> dimension;
    private final List<OverlayPolygon> polygons;
    private final OverlayShapeProps props;

    /**
     * @param overlayId the addon-stable handle for this overlay
     * @param dimension the dimension this overlay belongs to
     * @param polygons  one or more polygons, all sharing the same shape props
     * @param props     style and visibility for every polygon in this overlay
     */
    public ServerPolygon(String overlayId,
                         ResourceKey<Level> dimension,
                         List<OverlayPolygon> polygons,
                         OverlayShapeProps props)
    {
        this.overlayId = overlayId;
        this.dimension = dimension;
        this.polygons = polygons;
        this.props = props;
    }

    public String overlayId()
    {
        return overlayId;
    }

    public ResourceKey<Level> dimension()
    {
        return dimension;
    }

    public List<OverlayPolygon> polygons()
    {
        return polygons;
    }

    public OverlayShapeProps props()
    {
        return props;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ServerPolygon))
        {
            return false;
        }
        ServerPolygon other = (ServerPolygon) o;
        return Objects.equals(overlayId, other.overlayId)
                && Objects.equals(dimension, other.dimension)
                && Objects.equals(polygons, other.polygons)
                && Objects.equals(props, other.props);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(overlayId, dimension, polygons, props);
    }

    @Override
    public String toString()
    {
        return "ServerPolygon[overlayId=" + overlayId + ", dimension=" + dimension
                + ", polygons=" + polygons + ", props=" + props + "]";
    }
}
