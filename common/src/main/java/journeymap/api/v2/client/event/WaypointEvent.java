package journeymap.api.v2.client.event;

import journeymap.api.v2.common.event.CommonEventRegistry;
import journeymap.api.v2.common.waypoint.Waypoint;
import net.minecraft.client.Minecraft;

/**
 * This event handles all the CRUD operations of a waypoints.
 * @deprecated this event will be deleted, use same event in {@link journeymap.api.v2.common.event.common.WaypointEvent}
 * Switch your registration to use {@link CommonEventRegistry#WAYPOINT_EVENT}
 */
@Deprecated(forRemoval = true)
public class WaypointEvent extends ClientEvent
{
    public final Waypoint waypoint;
    public final Context context;

    public WaypointEvent(Waypoint waypoint, Context context)
    {
        super(false, Minecraft.getInstance().level.dimension());
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


    public enum Context
    {
        /**
         * Fired when a new waypoint is created.
         */
        CREATE,

        /**
         * Fired when an existing waypoint is updated.
         */
        UPDATE,

        /**
         * Fired when a waypoint is deleted.
         */
        DELETED,

        /**
         * Fired when a waypoint is read from disk, waypoints are always read in batches.
         * This event will be fired multiple times in a row, once per waypoint as it is loaded and put into internal cache.
         * <p>
         * This will happen periodically as the waypoint cache gets refreshed on dimension change, modifying some options, and at world join.
         */
        READ
    }
}
