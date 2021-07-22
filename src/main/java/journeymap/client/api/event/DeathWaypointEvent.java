package journeymap.client.api.event;

import com.google.common.base.MoreObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

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
    public DeathWaypointEvent(net.minecraft.core.BlockPos location, ResourceKey<Level> dimension)
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
