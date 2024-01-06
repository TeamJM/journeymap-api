package journeymap.common.api.waypoint;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;


public class WaypointFactory
{
    private final WaypointStore store;

    private static WaypointFactory INSTANCE;

    @ApiStatus.Internal
    public WaypointFactory(WaypointStore store)
    {
        this.store = store;
        INSTANCE = this;
    }


    private static WaypointFactory getInstance()
    {
        return INSTANCE;
    }

    /**
     * Creates a ClientWaypoint.
     *
     * @param modId            - The modid of the mod creating the waypoint
     * @param pos              - The BlockPos of the waypoint
     * @param name             - The Optional Name of the waypoint. If null, it will use the coordinates as the name.e
     * @param primaryDimension - The primary dimension, this is where it will be displayed and if
     *                         waypoint teleporting is enabled this is the dimension the user will be teleported to.
     * @param persistent       - should the waypoint persist between sessions?
     *                         True, JourneyMap will save this waypoint to disk and load every session it only needs to be sent once.
     *                         False, The waypoint will be flushed when the user changes dimensions and exits the game.
     * @return - The Waypoint with default values set.
     */
    public static Waypoint createClientWaypoint(String modId, BlockPos pos, @Nullable String name, ResourceKey<Level> primaryDimension, boolean persistent)
    {
        return createClientWaypoint(modId, pos, name, primaryDimension.location().toString(), persistent);
    }

    public static Waypoint createClientWaypoint(String modId, BlockPos pos, ResourceKey<Level> primaryDimension, boolean persistent)
    {
        return createClientWaypoint(modId, pos, primaryDimension.location().toString(), persistent);
    }

    public static Waypoint createClientWaypoint(String modId, BlockPos pos, String primaryDimension, boolean persistent)
    {
        return createClientWaypoint(modId, pos, null, primaryDimension, persistent);
    }

    public static Waypoint createClientWaypoint(String modId, BlockPos pos, @Nullable String name, String primaryDimension, boolean persistent)

    {
        return getInstance().store.createClientWaypoint(modId, pos, name, primaryDimension, persistent);
    }

    public static WaypointGroup createWaypointGroup(String modId, String name)
    {
        return getInstance().store.createWaypointGroup(modId, name);
    }


    public interface WaypointStore
    {
        Waypoint createClientWaypoint(String modId, BlockPos pos, @Nullable String name, String primaryDimension, boolean persistent);

        WaypointGroup createWaypointGroup(String modId, String name);
    }
}
