package journeymap.client.api.event;

import com.google.common.base.MoreObjects;
import net.minecraft.util.math.BlockPos;

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
        super(Type.DEATH_WAYPOINT, dimension);
        this.location = location;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("location", location)
                .toString();
    }
}
