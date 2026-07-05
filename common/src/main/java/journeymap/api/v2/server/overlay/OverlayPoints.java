package journeymap.api.v2.server.overlay;

import java.util.List;
import java.util.Objects;

/**
 * A ring of points representing one closed polygon outline (or a hole).
 * <p>
 * Each {@code Long} is a packed {@link net.minecraft.core.BlockPos} produced
 * via {@code BlockPos#asLong()}. Points are ordered counterclockwise for the
 * outer ring and clockwise for holes (matching the existing JourneyMap
 * polygon convention).
 */
public final class OverlayPoints
{
    private final List<Long> points;

    /**
     * @param points packed BlockPos longs, at least 3 entries
     */
    public OverlayPoints(List<Long> points)
    {
        this.points = points;
    }

    public List<Long> points()
    {
        return points;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof OverlayPoints))
        {
            return false;
        }
        return Objects.equals(points, ((OverlayPoints) o).points);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(points);
    }

    @Override
    public String toString()
    {
        return "OverlayPoints[points=" + points + "]";
    }
}
