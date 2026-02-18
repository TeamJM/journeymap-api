package journeymap.api.v2.server;

import journeymap.api.v2.common.CommonAPI;
import journeymap.api.v2.common.waypoint.Waypoint;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.UUID;

/**
 * Currently unused, just a placeholder. Please do not code any of this as it will likely change.
 */
@Deprecated
public interface IServerAPI extends CommonAPI
{
    /**
     * Gets all the waypoints stored for the target Server Player.
     *
     * @param player - the Player
     * @return - the waypoints.
     */
    List<Waypoint> getWaypoints(ServerPlayer player);

    /**
     * Gets all the waypoints stored for the target Server Player.
     *
     * @param id - the player uuid
     * @return - the waypoints.
     */
    List<Waypoint> getWaypoints(UUID id);

    /**
     * Gets All waypoints stored on the server for all players and all common/global waypoints.
     *
     * @return all waypoints
     */
    List<Waypoint> getWaypoints();

    /**
     * Gets all waypoints that are not tied to specific players.
     * These waypoints are created for every player when they log in.
     *
     * @return
     */
    List<Waypoint> getGlobalWaypoints();
}
