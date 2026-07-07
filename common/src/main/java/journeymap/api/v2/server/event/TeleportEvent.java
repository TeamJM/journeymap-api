package journeymap.api.v2.server.event;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.util.BlockPos;
import journeymap.api.v2.common.waypoint.Waypoint;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;

/**
 * Fired on the server when a teleport is about to be applied by JourneyMap.
 * Cancellable.
 */
public class TeleportEvent extends CommonEvent
{
    private final EntityPlayerMP player;
    @Nullable
    private final Waypoint waypoint;
    private BlockPos pos;
    private final int fromLevel;
    private int destinationLevel;

    public TeleportEvent(EntityPlayerMP player, @Nullable Waypoint waypoint, BlockPos pos,
                         int fromLevel, int destinationLevel)
    {
        super(true, Side.Server);
        this.player = player;
        this.waypoint = waypoint;
        this.pos = pos;
        this.fromLevel = fromLevel;
        this.destinationLevel = destinationLevel;
    }

    /**
     * The player being teleported. Non-null; this event only fires server-side
     * for a concrete teleport request.
     */
    public EntityPlayerMP getPlayer()
    {
        return player;
    }

    /**
     * This will be null for context menu teleporting on the fullscreen map.
     */
    @Nullable
    public Waypoint getWaypoint()
    {
        return waypoint;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public int getFromLevel()
    {
        return fromLevel;
    }

    public int getDestinationLevel()
    {
        return destinationLevel;
    }
}
