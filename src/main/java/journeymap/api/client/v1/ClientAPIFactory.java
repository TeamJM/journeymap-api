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

import com.google.common.collect.LinkedHashMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class to provide the JourneyMap Client API implementation
 * from the loaded mod.  If the mod isn't present, a stub implementation
 * is returned.
 */
public class ClientAPIFactory
{
    final static Logger LOGGER = LogManager.getLogger("journeymap-api");
    final static String IMPL_NAME = "JourneyMap ClientAPI v1";
    final static String IMPL_CLASS = "journeymap.api.client.v1.impl.ClientAPIImpl";
    final static String IMPL_METHOD = "getInstance";

    /**
     * Whether the JourneyMap client mod (with a version containing the API) has been loaded.
     *
     * @return true if the ClientAPI v1 has been loaded.
     */
    public static boolean isJourneyMapPresent()
    {
        try
        {
            Class.forName(IMPL_CLASS);
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }

    /**
     * Get the ClientAPI instance from the JourneyMap client mod.
     *
     * @return null if the ClientAPI can't be found or accessed.
     */
    public static ClientAPI getInstance()
    {
        try
        {
            Class implClass = Class.forName(IMPL_CLASS);
            Method implMethod = implClass.getMethod(IMPL_METHOD);
            return (ClientAPI) implMethod.invoke(implClass);
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.info(IMPL_NAME + " not found");
        }
        catch (NoSuchMethodException e)
        {
            LOGGER.error(IMPL_NAME + " is corrupt.", e);
        }
        catch (InvocationTargetException e)
        {
            LOGGER.error(IMPL_NAME + " isn't usable.", e);
        }
        catch (IllegalAccessException e)
        {
            LOGGER.error(IMPL_NAME + " isn't usable.", e);
        }

        return null;
    }

    /**
     * Provides a stub implementation of the ClientAPI suitable for rudimentary integration work
     * without having the JourneyMap client mod jar in your development environment.
     *
     * @return a stub implementation
     */
    public static ClientAPI getStub()
    {
        return new ClientAPI()
        {
            private LinkedHashMultimap<String, String> waypointIds = LinkedHashMultimap.create();
            private LinkedHashMultimap<String, String> imageIds = LinkedHashMultimap.create();
            private LinkedHashMultimap<String, String> markerIds = LinkedHashMultimap.create();
            private LinkedHashMultimap<String, String> polygonIds = LinkedHashMultimap.create();

            @Override
            public int getMaxApiVersion()
            {
                return 1;
            }

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
            public void addWaypoint(String modId, WaypointDefinition waypointDefinition)
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
            public void addMarker(String modId, MarkerOverlay markerOverlay)
            {
                markerIds.put(modId, markerOverlay.getMarkerId());
                log("Added marker " + markerOverlay.getMarkerId());
            }

            @Override
            public List<String> getMarkerIds(String modId)
            {
                return new ArrayList<String>(markerIds.get(modId));
            }

            @Override
            public void addImage(String modId, ImageOverlay imageOverlay)
            {
                imageIds.put(modId, imageOverlay.getImageId());
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
            public void addPolygon(String modId, PolygonOverlay polygonOverlay)
            {
                polygonIds.put(modId, polygonOverlay.getPolygonId());
                log("Added polygon " + polygonOverlay.getPolygonId());
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
                LOGGER.info(String.format("[%s Stub] %s", IMPL_NAME, message));
            }
        };
    }
}
