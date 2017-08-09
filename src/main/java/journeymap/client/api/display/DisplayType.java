/*
 * JourneyMap API (http://journeymap.info)
 * http://bitbucket.org/TeamJM/journeymap-api
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

package journeymap.client.api.display;

import java.util.HashMap;

/**
 * Enum of display types which are usable in the JourneyMap Client API
 * and the class reference to each one's implementation.
 */
public enum DisplayType
{
    Image(ImageOverlay.class),
    Marker(MarkerOverlay.class),
    Polygon(PolygonOverlay.class),
    Waypoint(Waypoint.class),
    WaypointGroup(journeymap.client.api.display.WaypointGroup.class);

    private static HashMap<Class<? extends Displayable>, DisplayType> reverseLookup;
    private final Class<? extends Displayable> implClass;

    DisplayType(Class<? extends Displayable> implClass)
    {
        this.implClass = implClass;
    }

    /**
     * Gets the DisplayType corresponding to the implementation class.
     *
     * @param implClass the displayable class
     * @throws IllegalArgumentException if it's not a valid implementation class
     */
    public static DisplayType of(Class<? extends Displayable> implClass)
    {
        if (reverseLookup == null)
        {
            reverseLookup = new HashMap<Class<? extends Displayable>, DisplayType>();
            for (DisplayType type : DisplayType.values())
            {
                reverseLookup.put(type.getImplClass(), type);
            }
        }
        DisplayType displayType = reverseLookup.get(implClass);
        if (displayType == null)
        {
            throw new IllegalArgumentException("Not a valid Displayable implementation: " + implClass);
        }
        return displayType;
    }

    /**
     * Gets the implementation class for the DisplayType.
     *
     * @return the class
     */
    public Class<? extends Displayable> getImplClass()
    {
        return implClass;
    }

}
