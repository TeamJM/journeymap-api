package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.JourneyMapEvent;
import journeymap.api.v2.common.waypoint.Waypoint;
import journeymap.api.v2.common.waypoint.WaypointGroup;
import org.jetbrains.annotations.Nullable;

public class WaypointGroupTransferEvent extends JourneyMapEvent
{
    private final WaypointGroup to;
    private final WaypointGroup from;
    private final Waypoint waypoint;

    protected WaypointGroupTransferEvent(@Nullable WaypointGroup from, WaypointGroup to, Waypoint waypoint)
    {
        super(true);
        this.from = from;
        this.to = to;
        this.waypoint = waypoint;
    }

    /**
     * The group the waypoint is transferring to.
     *
     * @return - The waypoint group.
     */
    public WaypointGroup getGroupTo()
    {
        return to;
    }

    /**
     * The group the waypoint is transferring from.
     * <p>
     * May be null.
     *
     * @return - The waypoint group.
     */
    @Nullable
    public WaypointGroup getGroupFrom()
    {
        return from;
    }

    /**
     * Gets the waypoint that is transferring groups.
     *
     * @return - The waypoint group.
     */
    public Waypoint getWaypoint()
    {
        return waypoint;
    }
}
