/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package journeymap.api.v2.client;

import com.mojang.blaze3d.platform.NativeImage;
import journeymap.api.v2.client.display.Context;
import journeymap.api.v2.client.display.DisplayType;
import journeymap.api.v2.client.display.Displayable;
import journeymap.api.v2.client.event.MappingEvent;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.common.CommonAPI;
import journeymap.api.v2.common.waypoint.Waypoint;
import journeymap.api.v2.common.waypoint.WaypointGroup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

/**
 * Definition for the JourneyMap Client API.
 */
@ParametersAreNonnullByDefault
public interface IClientAPI extends CommonAPI
{
    String API_OWNER = "journeymap";
    String API_VERSION = "2.0.0-SNAPSHOT";

    /**
     * Returns the current UIState of the UI specified.
     * <p>
     * Note: Context.UI.Any is not a meaningful parameter value here and will just return null.
     *
     * @param ui Should be one of: Fullscreen, Minimap, or Webmap
     * @return the current UIState of the UI specified.
     */
    @Nullable
    UIState getUIState(Context.UI ui);


    /**
     * Add (or update) a displayable object to the player's maps. If you modify a Displayable after it
     * has been added, call this method again to ensure the maps reflect your changes.
     * <p>
     * If an object of the same Displayable.Type
     * from your mod with the same displayId has already been added, it will be replaced.
     * <p>
     * Has no effect on display types not accepted by the player.
     *
     * @param displayable The object to display.
     * @throws Exception if the Displayable can't be shown.
     * @see #playerAccepts(String, DisplayType)
     */
    void show(Displayable displayable) throws Exception;

    /**
     * Remove a displayable from the player's maps.
     * Has no effect on display types not accepted by the player.
     *
     * @param displayable The object to display.
     * @see #playerAccepts(String, DisplayType)
     */
    void remove(Displayable displayable);

    /**
     * Remove all displayables by DisplayType from the player's maps.
     * Has no effect on display types not accepted by the player.
     *
     * @param modId       Mod id
     * @param displayType Display type
     * @see #playerAccepts(String, DisplayType)
     */
    void removeAll(String modId, DisplayType displayType);

    /**
     * Remove all displayables.
     * Has no effect on display types not accepted by the player.
     *
     * @param modId Mod id
     * @see #playerAccepts(String, DisplayType)
     */
    void removeAll(String modId);

    /**
     * Check whether a displayable exists in the Client API.  A return value of true means the Client API has the
     * indicated displayable, but not necessarily that the player has made it visible.
     * <p>
     * Always returns false if the display type is not accepted by the player.
     *
     * @param displayable the object
     * @see #playerAccepts(String, DisplayType)
     */
    boolean exists(Displayable displayable);

    /**
     * Check whether player will accept a type of Displayable from your mod. (Like Displayables or Overlays).
     *
     * @param modId       Mod id
     * @param displayType Display type to check
     * @return true if player accepts addition/removal of displayables
     * @see DisplayType
     */
    boolean playerAccepts(String modId, DisplayType displayType);

    /**
     * Asynchronously request a map tile image from JourneyMap. Requests may be throttled, so use sparingly.
     * The largest image size that will be returned is 512x512 px.
     *
     * @param modId      The calling mod's id
     * @param dimension  The dimension
     * @param mapType    The map type
     * @param startChunk The NW chunk of the tile
     * @param endChunk   The SE chunk of the tile
     * @param chunkY     The vertical chunk (slice) if the map type isn't day/night/topo
     * @param zoom       The zoom level (0-8)
     * @param showGrid   Whether to include the chunk grid overlay
     * @param callback   A callback which will receive the NativeImage when available, or null if unavailable
     */
    void requestMapTile(String modId, ResourceKey<Level> dimension, Context.MapType mapType, ChunkPos startChunk, ChunkPos endChunk,
                        @Nullable Integer chunkY, int zoom, boolean showGrid, final Consumer<NativeImage> callback);

    /**
     * Note:  This method IS NOT SUPPORTED for most mods. Talk to Techbrew if you need to use this function.
     * <p>
     * This call can be used to enable or disable map types and UIs in a specific dimension or all dimensions.
     *
     * @param dimension The dimension. Use null for all dimensions.
     * @param mapType   The map type
     * @param enable    True to enable, false to disable.
     */
    void disableFeature(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, boolean enable);

    /**
     * This call gets the current user's data path for saving custom addon data specific to the game/world the user is playing in.
     * <p>
     * This is only valid when Journeymap is mapping. Mods ideally should store this value on
     * {@link MappingEvent.Stage#MAPPING_STARTED} event.
     * <p>
     * Note: Will method return null if not in world or not mapping.
     * The path is flushed right after
     * {@link MappingEvent.Stage#MAPPING_STOPPED}
     *
     * @param modId The ModId
     * @return a path similar to ./journeymap/data/{sp|mp}/{worldname}/addon-data/{modid}/
     */
    @Nullable
    File getDataPath(String modId);

    /**
     * Returns all waypoints that journeymap has stored for the current game/server.
     * Modifying these waypoints will not change anything in game, they are just a copy.
     *
     * @return - List of all waypoints.
     */
    List<? extends Waypoint> getAllWaypoints();

    /**
     * Gets all waypoints for the provided dimension from all of journeymap's waypoints.
     * Modifying these waypoints will not change anything in game, they are just a copy.
     *
     * @param dim - The dimension
     * @return The waypoints.
     */
    List<? extends Waypoint> getAllWaypoints(ResourceKey<Level> dim);

    /**
     * Gets the waypoint by display Id for the modId provided.
     *
     * @param modId - The modId
     * @param guid  - The guid
     * @return The waypoint.
     */
    @Nullable
    Waypoint getWaypoint(final String modId, final String guid);

    /**
     * Gets all waypoints for the provided modId
     *
     * @param modId - The modId
     * @return the waypoint list
     */
    List<? extends Waypoint> getWaypoints(final String modId);

    /**
     * Remove a waypoint.
     *
     * @param modId    - The modId
     * @param waypoint - the waypoint
     */
    void removeWaypoint(final String modId, Waypoint waypoint);

    /**
     * Adds a waypoint
     *
     * @param modId    - The modId
     * @param waypoint - the waypoint
     */
    void addWaypoint(final String modId, Waypoint waypoint);

    /**
     * Remove all waypoints.
     *
     * @param modId - The modId
     */
    void removeAllWaypoints(final String modId);

    /**
     * Adds a Waypoint group to the system.
     */
    void addWaypointGroup(WaypointGroup group);

    /**
     * Gets a waypoint group from the group's guid.
     *
     * @param groupGuid the guid
     * @return the group.
     */
    WaypointGroup getWaypointGroup(final String groupGuid);

    /**
     * Gets a waypoint group from the name. (not case-sensitive)
     * If multiple are found, returns the first one.
     *
     * @param name  the group name
     * @param modId the modId
     * @return the group.
     */
    WaypointGroup getWaypointGroupByName(final String modId, final String name);

    /**
     * Gets all waypoint groups for a modid.
     *
     * @param modId the modId
     * @return the immutable list of groups
     */
    List<? extends WaypointGroup> getWaypointGroups(final String modId);

    /**
     * Gets an immutable list of all waypoint groups.
     *
     * @return the immutable list of groups
     */
    List<? extends WaypointGroup> getAllWaypointGroups();

    /**
     * Removes a waypoint group.
     * Setting deleteWaypoints to false will move all waypoints in the Default group.
     *
     * @param group           the group
     * @param deleteWaypoints to delete all waypoints in group
     */
    void removeWaypointGroup(WaypointGroup group, boolean deleteWaypoints);

    /**
     * Removes groups for a modId.
     * Setting deleteWaypoints to false will move all waypoints in the Default group.
     *
     * @param modId           - the modId
     * @param deleteWaypoints to delete all waypoints in group
     */
    void removeWaypointGroups(String modId, boolean deleteWaypoints);

    /**
     * Gets the worldId for the current world.
     *
     * @return worldId.
     */
    String getWorldId();

    /**
     * Toggles the minimmap display
     *
     * @param enable - enable or disable
     */
    void toggleMinimap(boolean enable);

    /**
     * Gets the minimap enabled or disabled state
     *
     * @return - true or false
     */
    boolean minimapEnabled();
}
