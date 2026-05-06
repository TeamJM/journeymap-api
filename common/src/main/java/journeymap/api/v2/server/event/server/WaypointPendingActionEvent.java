package journeymap.api.v2.server.event.server;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.Waypoint;

import java.util.UUID;

/**
 * Fired when a recipient accepts or declines a pending waypoint.
 * Cancellable: cancel to block the action (waypoint stays in pending).
 */
public class WaypointPendingActionEvent extends CommonEvent
{
    public final UUID playerUUID;
    public final Waypoint waypoint;
    public final Action action;

    public WaypointPendingActionEvent(UUID playerUUID, Waypoint waypoint, Action action)
    {
        super(true, Side.Server);
        this.playerUUID = playerUUID;
        this.waypoint = waypoint;
        this.action = action;
    }

    public enum Action
    {
        ACCEPT,
        DECLINE
    }
}
