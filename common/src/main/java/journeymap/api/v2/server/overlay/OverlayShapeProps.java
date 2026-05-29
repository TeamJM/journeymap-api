package journeymap.api.v2.server.overlay;

import journeymap.api.v2.common.Context;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Style and visibility properties applied to every polygon in a single
 * {@link ServerPolygon}. Mirrors the subset of {@code Overlay} fields that are
 * meaningful for server-pushed overlays. Interactive listeners are not
 * supported in v1 and are therefore not represented here.
 *
 * @param fillColor      0xRRGGBB; alpha is supplied separately as fillOpacity
 * @param fillOpacity    0.0 (transparent) to 1.0 (opaque)
 * @param strokeColor    0xRRGGBB
 * @param strokeWidth    pixels; 0 disables the stroke
 * @param strokeOpacity  0.0 to 1.0
 * @param displayOrder   z-order among overlays; higher draws in front (default 1000)
 * @param minZoom        minimum fullscreen zoom at which the overlay is visible
 * @param maxZoom        maximum fullscreen zoom at which the overlay is visible
 * @param activeUIs      which UIs (Fullscreen, Minimap, Webmap) the overlay is visible in
 * @param activeMapTypes which map types (Day, Night, etc.) the overlay is visible in
 * @param label          static label rendered with the polygon, or null for none
 * @param title          hover tooltip text, or null for none
 */
@SuppressWarnings("deprecation")
public record OverlayShapeProps(int fillColor,
                                float fillOpacity,
                                int strokeColor,
                                float strokeWidth,
                                float strokeOpacity,
                                int displayOrder,
                                int minZoom,
                                int maxZoom,
                                Set<Context.UI> activeUIs,
                                Set<Context.MapType> activeMapTypes,
                                @Nullable String label,
                                @Nullable String title)
{
}
