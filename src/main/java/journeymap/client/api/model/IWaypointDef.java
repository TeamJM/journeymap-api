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

package journeymap.client.api.model;

/**
 * Definition for a waypoint that is offered to a user.
 */
public interface IWaypointDef
{
    /**
     * Unique id (scoped to your mod)
     */
    String getWaypointId();

    /**
     * (Optional) Group or category name for the waypoint.
     */
    String getWaypointGroupName();

    /**
     * Waypoint name.
     */
    String getWaypointName();

    /**
     * Waypoint location.
     */
    IMapPoint getPoint();

    /**
     * Color for waypoint label.
     *
     * @return rgb int
     */
    int getColor();

    /**
     * Dimensions where waypoint should be displayed.
     */
    int[] getDimensions();

    /**
     * Icon specification for waypoint.
     *
     * @return spec
     */
    IMapIcon getIcon();

    /**
     * If the icon is saved to a file by JourneyMap, this determines the filename that will be used.
     *
     * @return icon filename
     */
    String getIconName();
}
