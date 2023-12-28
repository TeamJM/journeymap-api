package journeymap.common.api.waypoint;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


public class WaypointFactory
{
    private final WaypointStore store;

    private static WaypointFactory INSTANCE;

    private WaypointFactory(WaypointStore store)
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
     * @return - The Waypoint with default values set.
     */
    public static Waypoint createClientWaypoint(String modId, BlockPos pos, @Nullable String name, ResourceKey<Level> primaryDimension)
    {
        return createClientWaypoint(modId, pos, name, primaryDimension.location().toString());
    }

    public static Waypoint createClientWaypoint(String modId, BlockPos pos, ResourceKey<Level> primaryDimension)
    {
        return createClientWaypoint(modId, pos, primaryDimension.location().toString());
    }

    public static Waypoint createClientWaypoint(String modId, BlockPos pos, String primaryDimension)
    {
        return createClientWaypoint(modId, pos, null, primaryDimension);
    }

    public static Waypoint createClientWaypoint(String modId, BlockPos pos, @Nullable String name, String primaryDimension)
    {
        return getInstance().store.createClientWaypoint(modId, pos, name, primaryDimension);
    }

    public static WaypointGroup createWaypointGroup(String modId, String name)
    {
        return getInstance().store.createWaypointGroup(modId, name);
    }


    public interface WaypointStore
    {
        Waypoint createClientWaypoint(String modId, BlockPos pos, @Nullable String name, String primaryDimension);

        WaypointGroup createWaypointGroup(String modId, String name);
    }
}
