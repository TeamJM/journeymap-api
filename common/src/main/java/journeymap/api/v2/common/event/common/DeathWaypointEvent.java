package journeymap.api.v2.common.event.common;

import com.google.common.base.MoreObjects;
import journeymap.api.v2.common.event.impl.CommonEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

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
    private final BlockPos location;
    private final ResourceKey<Level> dimension;

    public DeathWaypointEvent(BlockPos location, ResourceKey<Level> dimension, Side side)
    {
        super(true, side);
        this.location = location;
        this.dimension = dimension;
    }

    public BlockPos getLocation()
    {
        return location;
    }

    public ResourceKey<Level> getDimension()
    {
        return dimension;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("location", location)
                .add("dimension", dimension)
                .add("side", getSide())
                .toString();
    }
}
