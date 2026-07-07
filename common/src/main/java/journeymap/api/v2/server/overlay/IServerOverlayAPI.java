package journeymap.api.v2.server.overlay;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Server-side API for pushing polygon overlays to a single player's
 * JourneyMap client. The consumer addon is responsible for all state: when
 * to push, who to push to, and any persistence. JourneyMap does not store
 * server-pushed overlays.
 *
 * <p>Reach this from a server plugin via:</p>
 * <pre>
 * IServerOverlayAPI overlayApi = jmServerApi.getOverlayApi();
 * </pre>
 */
public interface IServerOverlayAPI
{
    /**
     * Push polygon overlays to a single player. Each {@link ServerPolygon}'s
     * {@code overlayId} is stable and addon-chosen; re-calling {@code show()}
     * with the same {@code (modId, overlayId)} replaces the prior version on
     * the client.
     *
     * <p>Calling with no polygons is a no-op. Calling for an offline or
     * disconnected {@code EntityPlayerMP} is a no-op.</p>
     *
     * @param player   the recipient
     * @param modId    the addon's mod id (becomes the JourneyMap displayable owner)
     * @param polygons one or more {@link ServerPolygon}s
     */
    void show(EntityPlayerMP player, String modId, ServerPolygon... polygons);

    /**
     * Remove a single overlay (by {@code modId} + {@code overlayId}) from a
     * player's client. Calling for an offline player is a no-op; removing an
     * overlay that does not exist on the client is a silent no-op.
     *
     * @param player    the recipient
     * @param modId     the addon's mod id
     * @param overlayId the addon-stable id used in a prior {@code show()}
     */
    void remove(EntityPlayerMP player, String modId, String overlayId);

    /**
     * Remove every overlay this addon has shown to the player.
     *
     * @param player the recipient
     * @param modId  the addon's mod id
     */
    void clearAll(EntityPlayerMP player, String modId);
}
