package journeymap.api.v2.server.overlay;

import java.util.List;

/**
 * A ring of points representing one closed polygon outline (or a hole).
 * <p>
 * Each {@code Long} is a packed {@link net.minecraft.core.BlockPos} produced
 * via {@code BlockPos#asLong()}. Points are ordered counterclockwise for the
 * outer ring and clockwise for holes (matching the existing JourneyMap
 * polygon convention).
 *
 * @param points packed BlockPos longs, at least 3 entries
 */
public record OverlayPoints(List<Long> points)
{
}
