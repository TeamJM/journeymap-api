package journeymap.api.v2.server.event;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.WaypointGroup;

/**
 * Fired on the server for global waypoint group CRUD operations.
 * CREATE and UPDATE are cancellable; DELETED is not.
 */
public class GlobalWaypointGroupEvent extends CommonEvent
{
    public final WaypointGroup group;
    public final Context context;

    public GlobalWaypointGroupEvent(WaypointGroup group, Context context)
    {
        super(context.cancelable, Side.Server);
        this.group = group;
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
