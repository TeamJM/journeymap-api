package journeymap.api.v2.server.event;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.Waypoint;

import java.util.List;
import java.util.UUID;

/**
 * Fired on the server when a player (or addon via IServerAPI) submits a waypoint share.
 * Cancellable: cancel to block the entire share (nothing stored, no notifications sent).
 * Addon may mutate {@code targetIds} to filter recipients before returning.
 */
public class WaypointShareSubmitEvent extends CommonEvent
{
    public final UUID senderUUID;
    public final String senderName;
    public final Waypoint waypoint;
    public final List<UUID> targetIds;

    public WaypointShareSubmitEvent(UUID senderUUID, String senderName, Waypoint waypoint, List<UUID> targetIds)
    {
        super(true, Side.Server);
        this.senderUUID = senderUUID;
        this.senderName = senderName;
        this.waypoint = waypoint;
        this.targetIds = targetIds;
    }
}
