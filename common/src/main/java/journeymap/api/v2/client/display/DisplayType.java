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

package journeymap.api.v2.client.display;

import java.util.HashMap;

/**
 * Enum of display types which are usable in the JourneyMap Client API
 * and the class reference to each one's implementation.
 */
public enum DisplayType
{
    Image(ImageOverlay.class),
    Marker(MarkerOverlay.class),
    Polygon(PolygonOverlay.class);

    /**
     * Class to type lookup, built once when the enum initializes. It used to be built lazily on the first
     * {@link #of(Class)} call in an unsynchronized map; two threads constructing their first Displayable at
     * the same time (JourneyMap's automap region threads) could see the half-built map and get an
     * IllegalArgumentException for a perfectly valid class.
     */
    private static final HashMap<Class<? extends Displayable>, DisplayType> REVERSE_LOOKUP = new HashMap<>();

    static
    {
        for (DisplayType type : DisplayType.values())
        {
            REVERSE_LOOKUP.put(type.getImplClass(), type);
        }
    }
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
        DisplayType displayType = REVERSE_LOOKUP.get(implClass);
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
