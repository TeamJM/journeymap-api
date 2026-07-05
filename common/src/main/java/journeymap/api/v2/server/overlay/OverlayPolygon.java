package journeymap.api.v2.server.overlay;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * One polygon: an outer ring and optional holes punched out of it.
 */
public final class OverlayPolygon
{
    private final OverlayPoints outer;
    @Nullable
    private final List<OverlayPoints> holes;

    /**
     * @param outer the outer ring (required)
     * @param holes interior rings rendered as holes; null or empty for none
     */
    public OverlayPolygon(OverlayPoints outer, @Nullable List<OverlayPoints> holes)
    {
        this.outer = outer;
        this.holes = holes;
    }

    public OverlayPoints outer()
    {
        return outer;
    }

    @Nullable
    public List<OverlayPoints> holes()
    {
        return holes;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof OverlayPolygon))
        {
            return false;
        }
        OverlayPolygon other = (OverlayPolygon) o;
        return Objects.equals(outer, other.outer) && Objects.equals(holes, other.holes);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(outer, holes);
    }

    @Override
    public String toString()
    {
        return "OverlayPolygon[outer=" + outer + ", holes=" + holes + "]";
    }
}
