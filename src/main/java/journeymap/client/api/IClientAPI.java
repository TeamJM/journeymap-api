/*
 *
 * JourneyMap API
 * http://journeymap.info
 * http://bitbucket.org/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2015 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 * + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 * + Write your own code that uses, modifies, or extends the example source code in example.* packages
 * + Distribute compiled classes of unmodified API source code in journeymap.* packages
 * + Fork and modify any source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified API source code from journeymap.* packages or compiled classes of modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API source code or example source code in any way not explicitly granted
 *        by this license statement.
 *
 */

package journeymap.client.api;

import journeymap.client.api.model.IWaypointDef;
import journeymap.client.api.overlay.IImageOverlay;
import journeymap.client.api.overlay.IMarkerOverlay;
import journeymap.client.api.overlay.IPolygonOverlay;

import java.util.List;

/**
 * Definition for the JourneyMap Client API v1.
 *
 * To get the working ClientAPI at runtime, use the following code:
 * <code>
 *  // ClientAPI is an enum singleton with the name INSTANCE.
 *  if(Loader.isModLoaded("journeymap")) {
 *     Class<Enum> implClass = (Class<Enum>) Class.forName("journeymap.client.api.ClientAPI");
 *     return (IClientAPI) Enum.valueOf(implClass, "INSTANCE");
 *  }
 * </code>
 */
public interface IClientAPI
{
    String API_OWNER = "journeymap";
    String API_VERSION = "@API_VERSION@";
    String API_PROVIDES_API = "journeymap|client-api";
    String API_PROVIDES_MODEL = "journeymap|client-api-model";
    String API_PROVIDES_OVERLAY = "journeymap|client-api-overlay";
    String FACTORY_CLASS = "journeymap.client.api.ClientAPI";

    /**
     * Check whether player will accept waypoints from your mod.
     *
     * @param modId Mod id
     * @return true if player accepts addition/removal of waypoints
     */
    boolean getPlayerAcceptsWaypoints(String modId);

    /**
     * Check whether player will accept map overlays (markers and polygons) from your mod.
     *
     * @param modId Mod id
     * @return true if player accepts display of map overlays
     */
    boolean getPlayerAcceptsOverlays(String modId);

    /**
     * Add a waypoint for the player. If getPlayerAcceptsWaypoints returns false,
     * this method does nothing.  Also note that just because a player accepts
     * a suggested waypoint, doesn't mean they'll keep it forever.
     *
     * @param modId         Mod id
     * @param waypointDefinition Defines the waypoint to display.
     * @see #getPlayerAcceptsWaypoints(String)
     */
    void addWaypoint(String modId, IWaypointDef waypointDefinition);

    /**
     * Remove a player's waypoint, if it exists. If getPlayerAcceptsWaypoints returns false,
     * this method does nothing.
     *
     * @param modId      Mod id
     * @param waypointId A unique id (within your mod) for the waypoint.
     * @see #getPlayerAcceptsWaypoints(String)
     */
    void removeWaypoint(String modId, String waypointId);

    /**
     * Check whether the player has a waypoint with the given waypointId associated with your mod.
     * If getPlayerAcceptsWaypoints returns false, this method does nothing.
     *
     * @param modId      Mod id
     * @param waypointId A unique id (within your mod) for the waypoint.
     * @return true if a waypoint with the id exists.
     */
    boolean getWaypointExists(String modId, String waypointId);

    /**
     * Gets a list of player waypoint ids associated with your mod.
     * If getPlayerAcceptsWaypoints returns false, this method does nothing.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     * @see #getPlayerAcceptsWaypoints(String)
     */
    List<String> getWaypointIds(String modId);

    /**
     * Attempt to show a marker on one or more map views. If rMapOverlayOptIn returns false,
     * this method does nothing.
     * <p/>
     * If a MarkerOverlay with the same markerId already exists, the new one will replace the old one.
     *
     * @param modId         Mod id
     * @param markerOverlay Describes the marker overlay to display.
     * @see #getPlayerAcceptsOverlays(String)
     */
    void addMarker(String modId, IMarkerOverlay markerOverlay);

    /**
     * Remove a MarkerOverlay, if it exists. If getPlayerAcceptsOverlays returns false,
     * this method does nothing.
     *
     * @param modId    Mod id
     * @param markerId A unique id (within your mod) for the map marker.
     */
    void removeMarker(String modId, String markerId);

    /**
     * Check whether the player has a map marker with the given markerId associated with your mod.
     * If getPlayerAcceptsOverlays returns false, this method returns false.
     *
     * @param modId    Mod id
     * @param markerId A unique id (within your mod) for the MarkerOverlay.
     * @return true if a waypoint with the id exists.
     */
    boolean getMarkerExists(String modId, String markerId);

    /**
     * Gets a list of MarkerOverlay ids associated with your mod.
     * If getPlayerAcceptsWaypoints returns false, an empty list will be returned.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     */
    List<String> getMarkerIds(String modId);

    /**
     * Attempt to show a image on one or more map views.  If getPlayerAcceptsOverlays returns false,
     * this method does nothing.
     * <p/>
     * If an ImageOverlay with same imageId already exists, the new one will replace the old one.
     *
     * @param modId        Mod id
     * @param imageOverlay Describes the image overlay to display.
     * @see #getPlayerAcceptsOverlays(String)
     */
    void addImage(String modId, IImageOverlay imageOverlay);

    /**
     * Remove an ImageOverlay, if it exists. If getPlayerAcceptsOverlays returns false,
     * this method does nothing.
     *
     * @param modId   Mod id
     * @param imageId A unique id (within your mod) for the map image.
     */
    void removeImage(String modId, String imageId);

    /**
     * Check whether the player has an ImageOverlay with the given imageId associated with your mod.
     * If getPlayerAcceptsOverlays returns false, this method returns false.
     *
     * @param modId   Mod id
     * @param imageId A unique id (within your mod) for the map image.
     * @return true if a waypoint with the id exists.
     */
    boolean getImageExists(String modId, String imageId);

    /**
     * Gets a list of ImageOverlay imageIds associated with your mod. If getPlayerAcceptsOverlays returns false,
     * an empty list will be returned.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     */
    List<String> getImageIds(String modId);
    
    /**
     * Attempt to show a PolygonOverlay on one or more map views.  If getPlayerAcceptsOverlays returns false,
     * this method does nothing.
     * <p/>
     * If a PolygonOverlay with the same polygonId already exists, the new one will replace the old one.
     *
     * @param modId         Mod id
     * @param polygonOverlay Defines the polygon overlay to display
     * @see #getPlayerAcceptsOverlays(String)
     */
    void addPolygon(String modId, IPolygonOverlay polygonOverlay);

    /**
     * Remove a PolygonOverlay, if it exists. If getPlayerAcceptsOverlays returns false,
     * this method does nothing.
     *
     * @param modId     Mod id
     * @param polygonId A unique id (within your mod) for the polygon.
     */
    void removePolygon(String modId, String polygonId);

    /**
     * Check whether the player has a PolygonOverlay with the given polygonId associated with your mod.
     * If getPlayerAcceptsOverlays returns false, this method returns false.
     *
     * @param modId     Mod id
     * @param polygonId A unique id (within your mod) for the polygon.
     * @return true if a polygon with the id exists.
     */
    boolean getPolygonExists(String modId, String polygonId);

    /**
     * Gets a list of PolygonOverlay polygonIds associated with your mod.  If getPlayerAcceptsOverlays
     * returns false, an empty list will be returned.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     */
    List<String> getPolygonIds(String modId);

}
