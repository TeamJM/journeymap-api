package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.JourneyMapEvent;
import journeymap.api.v2.common.waypoint.WaypointGroup;

public class WaypointGroupEvent extends JourneyMapEvent
{
    public final WaypointGroup group;
    public final Context context;

    public WaypointGroupEvent(WaypointGroup group, Context context)
    {
        super(context.cancelable);
        this.group = group;
        this.context = context;
    }

    /**
     * Gets the waypoint group that the event is handling.
     *
     * @return - The waypoint group.
     */
    public WaypointGroup getGroup()
    {
        return group;
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
         * Fired when a new waypoint group is created.
         */
        CREATE(true),

        /**
         * Fired when an existing waypoint group is updated.
         */
        UPDATE(true),

        /**
         * Fired when a waypoint group is deleted.
         */
        DELETED(false),

        /**
         * Fired when a waypoint group. is read from disk, waypoint groups are always read in batches.
         * This event will be fired multiple times in a row, once per waypoint group as it is loaded and put into internal cache.
         * <p>
         * This will happen periodically as the waypoint group cache gets refreshed on dimension change, modifying some options, and at world join.
         */
        READ(false);

        final boolean cancelable;

        Context(boolean cancelable)
        {
            this.cancelable = cancelable;
        }
    }
}
