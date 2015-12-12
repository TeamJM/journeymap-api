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
            private List<String> waypointIds = new ArrayList<String>();
            private List<String> markerIds = new ArrayList<String>();
            private List<String> polygonIds = new ArrayList<String>();

            @Override
            public int getMaxApiVersion()
            {
                return 1;
            }

            @Override
            public boolean checkPlayerWaypointOptIn(String modId)
            {
                return true;
            }

            @Override
            public boolean checkPlayerMapOverlayOptIn(String modId)
            {
                return true;
            }

            @Override
            public void addWaypoint(String modId, WaypointDefinition specification)
            {
                waypointIds.add(specification.getWaypointId());
                log("Added waypoint " + specification.getWaypointId());
            }

            @Override
            public void removeWaypoint(String modId, String waypointId)
            {
                waypointIds.remove(waypointId);
                log("Removed waypoint " + waypointId);
            }

            @Override
            public boolean getWaypointExists(String modId, String waypointId)
            {
                return waypointIds.contains(waypointId);
            }

            @Override
            public List<String> getWaypointIds(String modId)
            {
                return new ArrayList<String>(waypointIds);
            }

            @Override
            public void addMarker(String modId, MarkerOverlay specification)
            {
                markerIds.add(specification.getMarkerId());
                log("Added marker " + specification.getMarkerId());
            }

            @Override
            public List<String> getMarkerIds(String modId)
            {
                return new ArrayList<String>(markerIds);
            }

            @Override
            public void removeMarker(String modId, String markerId)
            {
                markerIds.remove(markerId);
                log("Removed marker " + markerId);
            }

            @Override
            public boolean getMarkerExists(String modId, String markerId)
            {
                return markerIds.contains(markerId);
            }

            @Override
            public void showPolygon(String modId, PolygonOverlay specification)
            {
                polygonIds.add(specification.getPolygonId());
                log("Added polygon " + specification.getPolygonId());
            }

            @Override
            public List<String> getPolygonIds(String modId)
            {
                return new ArrayList<String>(polygonIds);
            }

            @Override
            public void removePolygon(String modId, String polygonId)
            {
                polygonIds.remove(polygonId);
                log("Removed polygon " + polygonId);
            }

            @Override
            public boolean getPolygonExists(String modId, String polygonId)
            {
                return polygonIds.contains(polygonId);
            }

            private void log(String message)
            {
                LOGGER.info(String.format("[%s Stub] %s", IMPL_NAME, message));
            }
        };
    }
}
