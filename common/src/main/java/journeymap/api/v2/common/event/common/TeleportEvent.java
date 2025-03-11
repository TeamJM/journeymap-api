package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.waypoint.Waypoint;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class TeleportEvent extends CommonEvent
{
    @Nullable
    private final Waypoint waypoint;
    private BlockPos pos;
    private final ResourceKey<Level> fromLevel;
    private ResourceKey<Level> destinationLevel;

    public TeleportEvent(@Nullable Waypoint waypoint, BlockPos pos, ResourceKey<Level> fromLevel, ResourceKey<Level> destinationLevel)
    {
        // Currently only supported on the client. Will eventually support being on the server.
        super(true, Side.Client);
        this.waypoint = waypoint;
        this.pos = pos;
        this.fromLevel = fromLevel;
        this.destinationLevel = destinationLevel;
    }

    public TeleportEvent(BlockPos pos, ResourceKey<Level> level)
    {
        this(null, pos, level, level);
    }

    /**
     * This will be null for context menu teleporting on the fullscreen map.
     * <p>
     * Do not modify this, use only for information.
     *
     * @return - The Waypoint
     */
    @Nullable
    public Waypoint getWaypoint()
    {
        return waypoint;
    }

    /**
     * Block Position of target location.
     *
     * @return the blockpos
     */
    public BlockPos getPos()
    {
        return pos;
    }

    /**
     * Update this to modify final teleport location
     *
     * @param pos
     */
    public void setPos(BlockPos pos)
    {
        this.pos = pos;
    }

    /**
     * Dimension where the user initiated the teleport.
     *
     * @return - the level key.
     */
    public ResourceKey<Level> getFromLevel()
    {
        return fromLevel;
    }

    /**
     * Destination Dimension
     *
     * @return - the destination dimension.
     */
    public ResourceKey<Level> getDestinationLevel()
    {
        return destinationLevel;
    }

    /**
     * Set to override the destination level.
     *
     * @param destinationLevel
     */
    public void setDestinationLevel(ResourceKey<Level> destinationLevel)
    {
        this.destinationLevel = destinationLevel;
    }
}
