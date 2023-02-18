package journeymap.client.api.event;

import journeymap.client.api.display.Waypoint;
import net.minecraft.client.Minecraft;

public class WaypointEvent extends ClientEvent
{
    public final Waypoint waypoint;
    public final Context context;

    private WaypointEvent(Waypoint waypoint, Context context)
    {
        super(Type.WAYPOINT, Minecraft.getInstance().level.dimension());
        this.waypoint = waypoint;
        this.context = context;
    }

    /**
     * Gets the waypoint that the event is handling.
     *
     * @return - The waypoint.
     */
    public Waypoint getWaypoint()
    {
        return waypoint;
    }

    /**
     * The event context.
     *
     * @return - The context.
     */
    public Context getContext()
    {
        return context;
    }

    /**
     * Fired when an existing waypoint is updated.
     */
    public static class WaypointUpdateEvent extends WaypointEvent
    {
        public WaypointUpdateEvent(Waypoint waypoint)
        {
            super(waypoint, Context.UPDATE);
        }
    }

    /**
     * Fired when a new waypoint is created.
     */
    public static class WaypointCreatedEvent extends WaypointEvent
    {
        public WaypointCreatedEvent(Waypoint waypoint)
        {
            super(waypoint, Context.CREATE);
        }
    }

    /**
     * Fired when a waypoint is deleted.
     */
    public static class WaypointDeletedEvent extends WaypointEvent
    {
        public WaypointDeletedEvent(Waypoint waypoint)
        {
            super(waypoint, Context.DELETED);
        }
    }

    /**
     * Fired when a waypoint is read from disk, waypoints are always read in batches.
     * This event will be fired multiple times in a row, once per waypoint as it is loaded and put into internal cache.
     *
     * This will happen periodically as the waypoint cache gets refreshed on dimension change, modifying some options, and at world join.
     */
    public static class WaypointReadEvent extends WaypointEvent
    {
        public WaypointReadEvent(Waypoint waypoint)
        {
            super(waypoint, Context.READ);
        }
    }

    public enum Context
    {
        CREATE,
        UPDATE,
        DELETED,
        READ
    }
}
