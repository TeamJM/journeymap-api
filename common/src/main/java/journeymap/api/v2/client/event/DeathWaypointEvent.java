package journeymap.api.v2.client.event;

import com.google.common.base.Objects;
import journeymap.api.v2.common.util.BlockPos;

/**
 * Indicates a Death Waypoint is about to be created.
 * Can be cancelled, which will prevent the waypoint creation.
 */
public class DeathWaypointEvent extends ClientEvent
{
    public final BlockPos location;

    /**
     * Constructor.
     *
     * @param location  The location of the waypoint.
     * @param dimension The dimension of the waypoint.
     */
    public DeathWaypointEvent(BlockPos location, int dimension)
    {
        super(true, dimension);
        this.location = location;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("location", location)
                .toString();
    }
}
