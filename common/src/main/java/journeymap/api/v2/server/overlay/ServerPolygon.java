package journeymap.api.v2.server.overlay;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * One logical overlay pushed by a server addon. May contain many disjoint
 * {@link OverlayPolygon}s sharing one set of {@link OverlayShapeProps}.
 *
 * <p>The {@code overlayId} is a stable, addon-chosen string. Re-calling
 * {@link IServerOverlayAPI#show} with the same {@code (modId, overlayId)} pair
 * replaces any prior version on the client; calling
 * {@link IServerOverlayAPI#remove} removes it.</p>
 *
 * @param overlayId the addon-stable handle for this overlay
 * @param dimension the dimension this overlay belongs to
 * @param polygons  one or more polygons, all sharing the same shape props
 * @param props     style and visibility for every polygon in this overlay
 */
public record ServerPolygon(String overlayId,
                            ResourceKey<Level> dimension,
                            List<OverlayPolygon> polygons,
                            OverlayShapeProps props)
{
}
