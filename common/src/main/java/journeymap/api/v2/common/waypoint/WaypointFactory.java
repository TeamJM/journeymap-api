package journeymap.api.v2.common.waypoint;

import journeymap.api.v2.common.util.BlockPos;
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
     * Creates a Waypoint. On the client side this will be a ClientWaypoint; on the server side this
     * will be a plain WaypointImpl.
     *
     * @param modId            - The modid of the mod creating the waypoint
     * @param pos              - The BlockPos of the waypoint
     * @param name             - The Optional Name of the waypoint. If null, it will use the coordinates as the name.
     * @param primaryDimension - The primary dimension identifier string.
     * @param persistent       - should the waypoint persist between sessions?
     *                         True, JourneyMap will save this waypoint to disk and load every session it only needs to be sent once.
     *                         False, The waypoint will be flushed when the user changes dimensions and exits the game.
     * @return - The Waypoint with default values set.
     */
    public static Waypoint createWaypoint(String modId, BlockPos pos, @Nullable String name, int primaryDimension, boolean persistent)
    {
        return createWaypoint(modId, pos, name, String.valueOf(primaryDimension), persistent);
    }

    public static Waypoint createWaypoint(String modId, BlockPos pos, int primaryDimension, boolean persistent)
    {
        return createWaypoint(modId, pos, String.valueOf(primaryDimension), persistent);
    }

    public static Waypoint createWaypoint(String modId, BlockPos pos, String primaryDimension, boolean persistent)
    {
        return createWaypoint(modId, pos, null, primaryDimension, persistent);
    }

    public static Waypoint createWaypoint(String modId, BlockPos pos, @Nullable String name, String primaryDimension, boolean persistent)
    {
        return getInstance().store.createWaypoint(modId, pos, name, primaryDimension, persistent);
    }

    public static Waypoint fromWaypointJsonString(String waypoint)
    {
        return getInstance().store.fromWaypointJsonString(waypoint);
    }

    public static WaypointGroup fromGroupJsonString(String waypoint)
    {
        return getInstance().store.fromGroupJsonString(waypoint);
    }

    public static WaypointGroup createWaypointGroup(String modId, String name)
    {
        return getInstance().store.createWaypointGroup(modId, name);
    }


    @ApiStatus.Internal
    public interface WaypointStore
    {
        Waypoint createWaypoint(String modId, BlockPos pos, @Nullable String name, String primaryDimension, boolean persistent);

        Waypoint fromWaypointJsonString(String waypoint);

        WaypointGroup fromGroupJsonString(String waypoint);

        WaypointGroup createWaypointGroup(String modId, String name);
    }
}
