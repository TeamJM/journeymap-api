package journeymap.api.v2.server;

import journeymap.api.v2.common.CommonAPI;
import journeymap.api.v2.common.event.ServerEventRegistry;
import journeymap.api.v2.common.waypoint.Waypoint;
import journeymap.api.v2.common.waypoint.WaypointGroup;
import journeymap.api.v2.server.event.GlobalWaypointEvent;
import journeymap.api.v2.server.event.GlobalWaypointGroupEvent;
import journeymap.api.v2.server.overlay.IServerOverlayAPI;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;
import java.util.UUID;

/**
 * Server-side JourneyMap API for reading and manipulating waypoints.
 */
public interface IServerAPI extends CommonAPI
{
    // ---- Player waypoint getters ----

    /**
     * Gets all waypoints stored for the given server player.
     *
     * @param player the server player
     * @return the player's waypoints
     */
    List<Waypoint> getWaypoints(EntityPlayerMP player);

    /**
     * Gets all waypoints stored for the player with the given UUID.
     *
     * @param playerUUID the player UUID
     * @return the player's waypoints
     */
    List<Waypoint> getWaypoints(UUID playerUUID);

    /**
     * Gets all waypoints stored on the server for all players and all global waypoints.
     *
     * @return all waypoints
     */
    List<Waypoint> getWaypoints();

    /**
     * Gets the waypoint with the given GUID for the specified player.
     *
     * @param playerUUID the player UUID
     * @param guid       the waypoint GUID
     * @return the waypoint, or null if not found
     */
    Waypoint getWaypoint(UUID playerUUID, String guid);

    /**
     * Gets all waypoint groups for the specified player.
     *
     * @param playerUUID the player UUID
     * @return the player's waypoint groups
     */
    List<WaypointGroup> getAllGroups(UUID playerUUID);

    /**
     * Gets the waypoint group with the given GUID for the specified player.
     *
     * @param playerUUID the player UUID
     * @param guid       the group GUID
     * @return the group, or null if not found
     */
    WaypointGroup getGroup(UUID playerUUID, String guid);

    // ---- Player waypoint mutations ----

    /**
     * Adds or updates a waypoint for the specified player.
     * Fires {@link journeymap.api.v2.common.event.common.WaypointEvent} (CREATE or UPDATE, cancellable).
     *
     * @param playerUUID the player UUID
     * @param waypoint   the waypoint to add or update
     */
    void addPlayerWaypoint(UUID playerUUID, Waypoint waypoint);

    /**
     * Deletes the waypoint with the given GUID for the specified player.
     * Fires {@link journeymap.api.v2.common.event.common.WaypointEvent} (DELETED).
     *
     * @param playerUUID the player UUID
     * @param guid       the waypoint GUID to delete
     */
    void deletePlayerWaypoint(UUID playerUUID, String guid);

    /**
     * Adds or updates a waypoint group for the specified player.
     *
     * @param playerUUID the player UUID
     * @param group      the group to add or update
     */
    void addPlayerGroup(UUID playerUUID, WaypointGroup group);

    /**
     * Deletes the waypoint group with the given GUID for the specified player.
     *
     * @param playerUUID      the player UUID
     * @param guid            the group GUID to delete
     * @param deleteWaypoints if true, also deletes all waypoints belonging to the group
     */
    void deletePlayerGroup(UUID playerUUID, String guid, boolean deleteWaypoints);

    // ---- Global waypoint getters ----

    /**
     * Gets all waypoints that are not tied to specific players.
     * These waypoints are visible to every player when they log in.
     *
     * @return all global waypoints
     */
    List<Waypoint> getGlobalWaypoints();

    /**
     * Gets the global waypoint with the given GUID.
     *
     * @param guid the waypoint GUID
     * @return the waypoint, or null if not found
     */
    Waypoint getGlobalWaypoint(String guid);

    /**
     * Gets all global waypoint groups.
     *
     * @return all global waypoint groups
     */
    List<WaypointGroup> getAllGlobalGroups();

    /**
     * Gets the global waypoint group with the given GUID.
     *
     * @param guid the group GUID
     * @return the group, or null if not found
     */
    WaypointGroup getGlobalGroup(String guid);

    // ---- Global waypoint mutations ----

    /**
     * Adds or updates a global waypoint.
     * Fires {@link GlobalWaypointEvent} (CREATE or UPDATE, cancellable).
     *
     * @param waypoint the waypoint to add or update
     */
    void addGlobalWaypoint(Waypoint waypoint);

    /**
     * Deletes the global waypoint with the given GUID.
     * Fires {@link GlobalWaypointEvent} (DELETED).
     *
     * @param guid the waypoint GUID to delete
     */
    void deleteGlobalWaypoint(String guid);

    /**
     * Adds or updates a global waypoint group.
     * Fires {@link GlobalWaypointGroupEvent} (CREATE or UPDATE, cancellable).
     *
     * @param group the group to add or update
     */
    void addGlobalGroup(WaypointGroup group);

    /**
     * Deletes the global waypoint group with the given GUID.
     * Fires {@link GlobalWaypointGroupEvent} (DELETED).
     *
     * @param guid            the group GUID to delete
     * @param deleteWaypoints if true, also deletes all waypoints belonging to the group
     */
    void deleteGlobalGroup(String guid, boolean deleteWaypoints);

    // ---- Share hook ----

    /**
     * Programmatically share a waypoint with one or more players.
     * Fires {@link ServerEventRegistry#WAYPOINT_SHARE_SUBMIT_EVENT} (cancellable)
     * before storing pending entries.
     *
     * @param waypoint      the waypoint to share
     * @param fromUUID      UUID of the player or system initiating the share
     * @param fromName      display name shown to recipients
     * @param targetIds     specific recipients; ignored if allKnownUsers is true
     * @param allKnownUsers if true, shares with all players in PlayerData
     */
    void shareWaypoint(Waypoint waypoint, UUID fromUUID, String fromName,
                       List<UUID> targetIds, boolean allKnownUsers);

    // ---- Overlay API ----

    /**
     * Returns the server-side overlay API, used to push polygon overlays to
     * connected players' JourneyMap clients.
     *
     * @return the overlay API
     */
    IServerOverlayAPI getOverlayApi();
}
