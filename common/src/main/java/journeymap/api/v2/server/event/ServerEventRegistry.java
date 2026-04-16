package journeymap.api.v2.server.event;

import journeymap.api.v2.common.event.impl.Event;
import journeymap.api.v2.common.event.impl.EventFactory;
import journeymap.api.v2.server.event.server.GlobalWaypointEvent;
import journeymap.api.v2.server.event.server.GlobalWaypointGroupEvent;
import journeymap.api.v2.server.event.server.WaypointPendingActionEvent;
import journeymap.api.v2.server.event.server.WaypointPendingReceivedEvent;
import journeymap.api.v2.server.event.server.WaypointShareSubmitEvent;

/**
 * Server only events will be here.
 */
public class ServerEventRegistry
{
    /**
     * Fired on the server when a player (or addon via IServerAPI) submits a waypoint share.
     * Cancellable: cancel to block the entire share (nothing stored, no notifications sent).
     */
    public static final Event<WaypointShareSubmitEvent> WAYPOINT_SHARE_SUBMIT_EVENT =
            EventFactory.create(WaypointShareSubmitEvent.class);

    /**
     * Fired once per recipient before a pending waypoint entry is stored.
     * Cancellable: cancel to skip this specific recipient; other recipients are unaffected.
     */
    public static final Event<WaypointPendingReceivedEvent> WAYPOINT_PENDING_RECEIVED_EVENT =
            EventFactory.create(WaypointPendingReceivedEvent.class);

    /**
     * Fired when a recipient accepts or declines a pending waypoint.
     * Cancellable: cancel to block the action (waypoint stays in pending).
     */
    public static final Event<WaypointPendingActionEvent> WAYPOINT_PENDING_ACTION_EVENT =
            EventFactory.create(WaypointPendingActionEvent.class);

    /**
     * Fired for global waypoint CRUD. CREATE and UPDATE are cancellable.
     */
    public static final Event<GlobalWaypointEvent> GLOBAL_WAYPOINT_EVENT =
            EventFactory.create(GlobalWaypointEvent.class);

    /**
     * Fired for global waypoint group CRUD. CREATE and UPDATE are cancellable.
     */
    public static final Event<GlobalWaypointGroupEvent> GLOBAL_WAYPOINT_GROUP_EVENT =
            EventFactory.create(GlobalWaypointGroupEvent.class);
}
