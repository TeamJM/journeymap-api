package journeymap.api.v2.common.event.common;

import com.google.common.base.Objects;
import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.util.BlockPos;
import journeymap.api.v2.common.waypoint.Waypoint;

/**
 * Fired before a death waypoint is created, both client-side (singleplayer or
 * non-JM-managed servers) and server-side (JourneyMap-managed servers).
 * Cancelling the event prevents the death waypoint from being created.
 *
 * <p>The plain {@link WaypointEvent} with {@link WaypointEvent.Context#CREATE}
 * is suppressed for death waypoints so addons subscribing to either receive
 * exactly one notification per death.</p>
 */
public class DeathWaypointEvent extends CommonEvent
{
    private final Waypoint waypoint;
    private final BlockPos location;
    private final int dimension;

    public DeathWaypointEvent(Waypoint waypoint, BlockPos location, int dimension, Side side)
    {
        super(true, side);
        this.waypoint = waypoint;
        this.location = location;
        this.dimension = dimension;
    }

    /**
     * The death waypoint being created. Mutating fields on it before the event
     * returns will be reflected in the persisted waypoint (if not cancelled).
     */
    public Waypoint getWaypoint()
    {
        return waypoint;
    }

    public BlockPos getLocation()
    {
        return location;
    }

    public int getDimension()
    {
        return dimension;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("waypoint", waypoint)
                .add("location", location)
                .add("dimension", dimension)
                .add("side", getSide())
                .toString();
    }
}
