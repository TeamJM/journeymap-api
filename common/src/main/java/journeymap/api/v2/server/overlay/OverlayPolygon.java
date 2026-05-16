package journeymap.api.v2.server.overlay;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * One polygon: an outer ring and optional holes punched out of it.
 *
 * @param outer the outer ring (required)
 * @param holes interior rings rendered as holes; null or empty for none
 */
public record OverlayPolygon(OverlayPoints outer, @Nullable List<OverlayPoints> holes)
{
}
