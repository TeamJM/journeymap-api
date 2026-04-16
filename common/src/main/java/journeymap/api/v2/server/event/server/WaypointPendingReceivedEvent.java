package journeymap.api.v2.server.event.server;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.Waypoint;

import java.util.UUID;

/**
 * Fired once per recipient before a pending waypoint entry is stored.
 * Cancellable: cancel to skip this specific recipient; other recipients are unaffected.
 */
public class WaypointPendingReceivedEvent extends CommonEvent
{
    public final UUID recipientUUID;
    public final UUID senderUUID;
    public final Waypoint waypoint;

    public WaypointPendingReceivedEvent(UUID recipientUUID, UUID senderUUID, Waypoint waypoint)
    {
        super(true, Side.Server);
        this.recipientUUID = recipientUUID;
        this.senderUUID = senderUUID;
        this.waypoint = waypoint;
    }
}
