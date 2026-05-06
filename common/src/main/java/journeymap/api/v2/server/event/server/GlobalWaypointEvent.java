package journeymap.api.v2.server.event.server;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.Waypoint;

/**
 * Fired on the server for global waypoint CRUD operations.
 * CREATE and UPDATE are cancellable; DELETED is not.
 */
public class GlobalWaypointEvent extends CommonEvent
{
    public final Waypoint waypoint;
    public final Context context;

    public GlobalWaypointEvent(Waypoint waypoint, Context context)
    {
        super(context.cancelable, Side.Server);
        this.waypoint = waypoint;
        this.context = context;
    }

    public enum Context
    {
        CREATE(true),
        UPDATE(true),
        DELETED(false);

        final boolean cancelable;

        Context(boolean cancelable)
        {
            this.cancelable = cancelable;
        }
    }
}
