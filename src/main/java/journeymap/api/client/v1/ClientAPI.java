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
 *   + Write your own code that uses this API source code as a dependency.
 *   + Distribute compiled classes of unmodified API source code which your code depends upon.
 *   + Fork and modify API source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified versions of the API source code or compiled artifacts of  modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API code in any way not explicitly granted by this license statement.
 *
 */

package journeymap.api.client.v1;

import java.util.List;

/**
 * Definition for the JourneyMap Client API v1
 */
public interface ClientAPI
{
    /**
     * The highest API version present in the JourneyMap client mod.
     *
     * @return the highest version
     */
    public int getMaxApiVersion();

    /**
     * Check whether player will accept waypoint additions/removals by your mod.
     *
     * @param modId Mod id
     * @return true if player accepts addition/removal of waypoints
     */
    public boolean checkPlayerWaypointOptIn(String modId);

    /**
     * Check whether player will accept map overlays (markers and polygons) from your mod.
     *
     * @param modId Mod id
     * @return true if player accepts display of map overlays
     */
    public boolean checkPlayerMapOverlayOptIn(String modId);

    /**
     * Suggest a waypoint to the player. The outcome is based on what the PlayerResponse is
     * regarding your mod and waypoint suggestions.  Note that just because a player accepts
     * a suggested waypoint, doesn't mean they'll keep it forever.
     *
     * @param modId         Mod id
     * @param specification Specification for the waypoint to display.
     * @see #checkPlayerWaypointOptIn(String)
     */
    public void addWaypoint(String modId, WaypointDefinition specification);

    /**
     * Remove a player's waypoint, if it exists. The outcome is based on what the PlayerResponse is
     * regarding your mod and waypoint suggestions.
     *
     * @param modId      Mod id
     * @param waypointId A unique id (within your mod) for the waypoint.
     * @see #checkPlayerWaypointOptIn(String)
     */
    public void removeWaypoint(String modId, String waypointId);

    /**
     * Check whether the player has a waypoint with the given waypointId associated with your mod.
     * If the PlayerResponse regarding waypoints from your mod is Block, this will always return false.
     *
     * @param modId      Mod id
     * @param waypointId A unique id (within your mod) for the waypoint.
     * @return true if a waypoint with the id exists.
     */
    public boolean getWaypointExists(String modId, String waypointId);

    /**
     * Gets a list of player waypoint ids associated with your mod.
     * If the PlayerResponse regarding waypoints from your mod is Block, an empty list will be returned.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     */
    public List<String> getWaypointIds(String modId);

    /**
     * Attempt to show a marker on one or more map views.  The outcome is based on
     * what the PlayerResponse is regarding your mod and map overlays.
     * <p/>
     * If a marker with the provided featureId already exists, the new one will replace the old one.
     *
     * @param modId         Mod id
     * @param specification Specification for the map marker.
     * @see #checkPlayerMapOverlayOptIn(String)
     */
    public void addMarker(String modId, MarkerOverlay specification);

    /**
     * Gets a list of map marker ids associated with your mod.
     * If the PlayerResponse regarding map overlays from your mod is Block, an empty list will be returned.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     */
    public List<String> getMarkerIds(String modId);

    /**
     * Remove a map marker, if it exists. The outcome is based on what the PlayerResponse is
     * regarding your mod and map overlays.
     *
     * @param modId    Mod id
     * @param markerId A unique id (within your mod) for the map marker.
     */
    public void removeMarker(String modId, String markerId);

    /**
     * Check whether the player has a map marker with the given markerId associated with your mod.
     * If the PlayerResponse regarding map overlays from your mod is Block, this will always return false.
     *
     * @param modId    Mod id
     * @param markerId A unique id (within your mod) for the map marker.
     * @return true if a waypoint with the id exists.
     */
    public boolean getMarkerExists(String modId, String markerId);

    /**
     * Attempt to show a polygon on one or more map views.  The outcome is based on
     * what the PlayerResponse is regarding your mod and map overlays.
     * <p/>
     * If a polygon with the provided featureId already exists, the new one will replace the old one.
     *
     * @param modId         Mod id
     * @param specification Specification of the polygon to display
     * @see #checkPlayerMapOverlayOptIn(String)
     */
    public void showPolygon(String modId, PolygonOverlay specification);

    /**
     * Gets a list of polygon ids associated with your mod.  If the PlayerResponse regarding map overlays from your
     * mod is Block, an empty list will be returned.
     *
     * @param modId Mod id
     * @return A list, possibly empty.
     */
    public List<String> getPolygonIds(String modId);

    /**
     * Remove a polygon, if it exists. The outcome is based on what the PlayerResponse is
     * regarding your mod and map overlays.
     *
     * @param modId     Mod id
     * @param polygonId A unique id (within your mod) for the polygon.
     */
    public void removePolygon(String modId, String polygonId);

    /**
     * Check whether the player has a polygon with the given markerId associated with your mod.
     * If the PlayerResponse regarding map overlays from your mod is Block, this will always return false.
     *
     * @param modId     Mod id
     * @param polygonId A unique id (within your mod) for the polygon.
     * @return true if a polygon with the id exists.
     */
    public boolean getPolygonExists(String modId, String polygonId);
}
