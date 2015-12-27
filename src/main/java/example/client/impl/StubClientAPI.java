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

package example.client.impl;

import com.google.common.collect.LinkedHashMultimap;
import cpw.mods.fml.common.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a stub implementation of the IClientAPI suitable for rudimentary integration work
 * without having the JourneyMap client mod jar in your development environment.
 * <p/>
 * Don't reference INSTANCE directly.  Use the ClientAPIFactory instead so your code will
 * be more closely aligned with the final form needed to use the real JourneyMap ClientAPI.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "journeymap.client.api.IClientAPI", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IWaypointDef", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.overlay.IImageOverlay", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.overlay.IMarkerOverlay", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.overlay.IPolygonOverlay", modid = "journeymap")
})
enum StubClientAPI implements journeymap.client.api.IClientAPI
{
    INSTANCE;

    private LinkedHashMultimap<String, String> waypointIds = LinkedHashMultimap.create();
    private LinkedHashMultimap<String, String> imageIds = LinkedHashMultimap.create();
    private LinkedHashMultimap<String, String> markerIds = LinkedHashMultimap.create();
    private LinkedHashMultimap<String, String> polygonIds = LinkedHashMultimap.create();

    @Override
    public boolean getPlayerAcceptsWaypoints(String modId)
    {
        return true;
    }

    @Override
    public boolean getPlayerAcceptsOverlays(String modId)
    {
        return true;
    }

    @Override
    public void addWaypoint(String modId, journeymap.client.api.model.IWaypointDef waypointDefinition)
    {
        waypointIds.put(modId, waypointDefinition.getWaypointId());
        log("Added waypoint " + waypointDefinition.getWaypointId());
    }

    @Override
    public void removeWaypoint(String modId, String waypointId)
    {
        waypointIds.remove(modId, waypointId);
        log("Removed waypoint " + waypointId);
    }

    @Override
    public boolean getWaypointExists(String modId, String waypointId)
    {
        return waypointIds.containsEntry(modId, waypointId);
    }

    @Override
    public List<String> getWaypointIds(String modId)
    {
        return new ArrayList<String>(waypointIds.get(modId));
    }

    @Override
    public void addMarker(String modId, journeymap.client.api.overlay.IMarkerOverlay IMarkerOverlay)
    {
        markerIds.put(modId, IMarkerOverlay.getDisplayId());
        log("Added marker " + IMarkerOverlay.getDisplayId());
    }

    @Override
    public List<String> getMarkerIds(String modId)
    {
        return new ArrayList<String>(markerIds.get(modId));
    }

    @Override
    public void addImage(String modId, journeymap.client.api.overlay.IImageOverlay IImageOverlay)
    {
        imageIds.put(modId, IImageOverlay.getDisplayId());
    }

    @Override
    public void removeImage(String modId, String imageId)
    {
        imageIds.remove(modId, imageId);
    }

    @Override
    public boolean getImageExists(String modId, String imageId)
    {
        return imageIds.containsEntry(modId, imageId);
    }

    @Override
    public List<String> getImageIds(String modId)
    {
        return new ArrayList<String>(imageIds.get(modId));
    }

    @Override
    public void removeMarker(String modId, String markerId)
    {
        markerIds.remove(modId, markerId);
        log("Removed marker " + markerId);
    }

    @Override
    public boolean getMarkerExists(String modId, String markerId)
    {
        return markerIds.containsEntry(modId, markerId);
    }

    @Override
    public void addPolygon(String modId, journeymap.client.api.overlay.IPolygonOverlay polygonOverlay)
    {
        polygonIds.put(modId, polygonOverlay.getDisplayId());
        log("Added polygon " + polygonOverlay.getDisplayId());
    }

    @Override
    public List<String> getPolygonIds(String modId)
    {
        return new ArrayList<String>(polygonIds.get(modId));
    }

    @Override
    public void removePolygon(String modId, String polygonId)
    {
        polygonIds.remove(modId, polygonId);
        log("Removed polygon " + polygonId);
    }

    @Override
    public boolean getPolygonExists(String modId, String polygonId)
    {
        return polygonIds.containsEntry(modId, polygonId);
    }

    private void log(String message)
    {
        ClientAPIFactory.LOGGER.info(String.format("[%s] %s", getClass().getSimpleName(), message));
    }
}
