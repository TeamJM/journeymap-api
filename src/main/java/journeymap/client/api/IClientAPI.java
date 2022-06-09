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

package journeymap.client.api;

import com.mojang.blaze3d.platform.NativeImage;
import journeymap.client.api.display.Context;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.Displayable;
import journeymap.client.api.display.Waypoint;
import journeymap.client.api.event.ClientEvent;
import journeymap.client.api.util.UIState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * Definition for the JourneyMap Client API.
 */
@ParametersAreNonnullByDefault
public interface IClientAPI
{
    String API_OWNER = "journeymap";
    String API_VERSION = "1.9-SNAPSHOT";

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
     * Subscribes to all of the eventTypes specified. Use EnumSet.noneOf(ClientEvent.Type)
     * if no event subscriptions are needed. (This is the default).
     *
     * @param modId      Mod id
     * @param eventTypes set of types
     */
    void subscribe(String modId, EnumSet<ClientEvent.Type> eventTypes);

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
     * Note:  This method IS NOT SUPPORTED for most mods. Misuse will lead to severe performance issues.
     * Talk to Techbrew if you need to use this function.
     * <p>
     * Asynchonrously request a BufferedImage map tile from JourneyMap. Requests may be throttled, so use sparingly.
     * The largest image size that will be returned is 512x512 px.
     *
     * @param modId      Mod id
     * @param dimension  The dimension
     * @param mapType    The map type
     * @param startChunk The NW chunk of the tile.
     * @param endChunk   The SW chunk of the tile.
     * @param chunkY     The vertical chunk (slice) if the maptype isn't day/night/topo
     * @param zoom       The zoom level (0-8)
     * @param showGrid   Whether to include to include the chunk grid overlay
     * @param callback   A callback function which will provide a BufferedImage when/if available.  If it returns null, then no image available.
     */
    void requestMapTile(String modId, ResourceKey<Level> dimension, Context.MapType mapType, ChunkPos startChunk, net.minecraft.world.level.ChunkPos endChunk,
                        @Nullable Integer chunkY, int zoom, boolean showGrid, final Consumer<NativeImage> callback);

    /**
     * Note:  This method IS NOT SUPPORTED for most mods. Talk to Techbrew if you need to use this function.
     * <p>
     * This call can be used to enable or disable map types and UIs in a specific dimension or all dimensions.
     *
     * @param dimension The dimension. Use null for all dimensions.
     * @param mapType   The map type
     * @param mapUI     The map UI
     * @param enable    True to enable, false to disable.
     */
    void toggleDisplay(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI, boolean enable);

    /**
     * Note:  This method IS NOT SUPPORTED for most mods. Talk to Techbrew if you need to use this function.
     * <p>
     * This call can be used to enable or disable the use of waypoints in a specific dimension or all dimensions.
     *
     * @param dimension The dimension. Use null for all dimensions.
     * @param mapType   The map type
     * @param mapUI     The map UI
     * @param enable    True to enable, false to disable.
     */
    void toggleWaypoints(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI, boolean enable);

    /**
     * Note:  This method IS NOT SUPPORTED for most mods. Talk to Techbrew if you need to use this function.
     * <p>
     * This call can be used to check if a map type and UI are enabled in a specific dimension or all dimensions.
     *
     * @param dimension The dimension. Use null for all dimensions.
     * @param mapType   The map type
     * @param mapUI     The map UI
     * @return true if enabled
     */
    boolean isDisplayEnabled(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI);

    /**
     * Note:  This method IS NOT SUPPORTED for most mods. Talk to Techbrew if you need to use this function.
     * <p>
     * This call can be used to check if waypoints are enabled in a specific dimension or all dimensions.
     *
     * @param dimension The dimension. Use null for all dimensions.
     * @param mapType   The map type
     * @param mapUI     The map UI
     * @return true if enabled
     */
    boolean isWaypointsEnabled(@Nullable ResourceKey<Level> dimension, Context.MapType mapType, Context.UI mapUI);

    /**
     * This call gets the current user's data path for saving custom addon data specific to the game/world the user is playing in.
     * <p>
     * This is only valid when Journeymap is mapping. Mods ideally should store this value on
     * {@link journeymap.client.api.event.ClientEvent.Type#MAPPING_STARTED} event.
     * <p>
     * Note: Will method return null if not in world or not mapping.
     * The path is flushed right after
     * {@link journeymap.client.api.event.ClientEvent.Type#MAPPING_STOPPED}
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
    List<Waypoint> getAllWaypoints();

    /**
     * Gets all waypoints for the provided dimension from all of journeymap's waypoints.
     * Modifying these waypoints will not change anything in game, they are just a copy.
     *
     * @param dim - The dimension
     * @return The waypoints.
     */
    List<Waypoint> getAllWaypoints(ResourceKey<Level> dim);

    /**
     * Gets the waypoint by display Id for the modId provided.
     *
     * @param modId     - The modId
     * @param displayId - The display ID
     * @return The waypoint.
     */
    @Nullable
    Waypoint getWaypoint(final String modId, final String displayId);

    /**
     * Gets all waypoints for the provided modId
     *
     * @param modId - The modId
     * @return the waypoint list
     */
    List<Waypoint> getWaypoints(final String modId);

    /**
     * NOTE: Mod Devs, please only use when connected to specific servers or use the getter to verify. If multiple mods use this hook
     * it can lead to an infinite loop on the client. Because mapping is restarted and the MAPPING_STARTED is fired again after the worldId is set.
     * If the worldId is the same on the second firing of the event, it ignores it. But, if another mod changes it, it may be different which would cause a reset loop.
     * <p>
     * Sets a custom worldId to identify servers and or worlds when bungeecord style servers send all dims as overworld or such.
     * This is intended to be used on the {@link ClientEvent.Type#MAPPING_STARTED} event.
     *
     * @param identifier - the new worldId
     * @deprecated due to the inherent risk associated with this setter. This may be switched to a singular event in the future.
     */
    @Deprecated
    void setWorldId(String identifier);

    /**
     * Gets the worldId for the current world.
     *
     * @return worldId.
     */
    String getWorldId();
}
