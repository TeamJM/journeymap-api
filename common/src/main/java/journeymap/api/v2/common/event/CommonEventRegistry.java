package journeymap.api.v2.common.event;

import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.common.event.common.DeathWaypointEvent;
import journeymap.api.v2.server.event.TeleportEvent;
import journeymap.api.v2.common.event.common.WaypointEvent;
import journeymap.api.v2.common.event.common.WaypointGroupEvent;
import journeymap.api.v2.common.event.common.WaypointGroupTransferEvent;
import journeymap.api.v2.common.event.impl.Event;
import journeymap.api.v2.common.event.impl.EventFactory;

/**
 * Common events, fired both on Client and Server
 * Events consumers subscribe in the {@link IClientPlugin#initialize(IClientAPI)} or @link IServerPlugin#initialize(IServerAPI) method on your plugin.
 * Example:
 * <code>WAYPOINT_EVENT.subscribe(MOD_ID, Consumer)</code>
 */
public class CommonEventRegistry
{

    /**
     * This event handles all the CRUD operations of a waypoints on client and server.
     */
    public static final Event<WaypointEvent> WAYPOINT_EVENT = EventFactory.create(WaypointEvent.class);

    /**
     * This event handles all the CRUD operations of a waypoint groups on client and server.
     */
    public static final Event<WaypointGroupEvent> WAYPOINT_GROUP_EVENT = EventFactory.create(WaypointGroupEvent.class);

    /**
     * This event fires when a waypoint is transferred from one group to another, from may be null.
     */
    public static final Event<WaypointGroupTransferEvent> WAYPOINT_GROUP_TRANSFER_EVENT = EventFactory.create(WaypointGroupTransferEvent.class);

    /**
     * Fired before a death waypoint is created. Cancellable. Fires both client-side
     * (singleplayer or non-JM-managed servers) and server-side (JourneyMap-managed
     * servers). The plain {@link WaypointEvent} CREATE is suppressed for death
     * waypoints so subscribers receive exactly one notification per death.
     */
    public static final Event<DeathWaypointEvent> DEATH_WAYPOINT_EVENT = EventFactory.create(DeathWaypointEvent.class);

}
