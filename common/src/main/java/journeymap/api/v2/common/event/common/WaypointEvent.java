package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.Waypoint;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * This event handles all the CRUD operations of a waypoints.
 * TODO: Currently only a client side event, will be a common event eventually.
 */

public class WaypointEvent extends CommonEvent
{
    public final Waypoint waypoint;
    public final Context context;
    public final ResourceKey<Level> dimension;

    public WaypointEvent(Waypoint waypoint, Context context, ResourceKey<Level> dimension)
    {
        super(context.cancelable, Side.Client);
        this.dimension = dimension;
        this.waypoint = waypoint;
        this.context = context;
    }

    /**
     * Server-side constructor. Use when firing from a server context.
     */
    public WaypointEvent(Waypoint waypoint, Context context, ResourceKey<Level> dimension, Side side)
    {
        super(context.cancelable, side);
        this.dimension = dimension;
        this.waypoint = waypoint;
        this.context = context;
    }

    /**
     * World dimension where event occurred.
     */
    public ResourceKey<Level> getDimension()
    {
        return dimension;
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
        CREATE(true),

        /**
         * Fired when an existing waypoint is updated.
         */
        UPDATE(true),

        /**
         * Fired when a waypoint is deleted.
         */
        DELETED(false),

        /**
         * Fired when a waypoint is read from disk, waypoints are always read in batches.
         * This event will be fired multiple times in a row, once per waypoint as it is loaded and put into internal cache.
         * <p>
         * This will happen periodically as the waypoint cache gets refreshed on dimension change, modifying some options, and at world join.
         */
        READ(false);

        final boolean cancelable;

        Context(boolean cancelable)
        {
            this.cancelable = cancelable;
        }
    }
}
