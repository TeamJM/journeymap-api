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

package journeymap.client.api.overlay;

import journeymap.client.api.model.IMapPolygon;

import java.util.List;

/**
 * A polygon overlay describes a geometric area on a map.  For example: An area of claimed chunks.
 */
public interface IPolygonOverlay extends IOverlay
{
    /**
     * A polygon of the outer area to be displayed.
     *
     * @return the outer area
     */
    IMapPolygon getOuterArea();

    /**
     * (optional) A list of polygons treated as holes inside the outerArea
     *
     * @return the holes
     */
    List<IMapPolygon> getHoles();

    /**
     * Line thickness of the polygon edges.
     *
     * @return the stroke width
     */
    float getStrokeWidth();

    /**
     * Line color (rgb) of the polygon edges.
     *
     * @return the stroke color
     */
    int getStrokeColor();

    /**
     * Line opacity (between 0 and 1) of the polygon edges.
     *
     * @return the stroke opacity
     */
    float getStrokeOpacity();

    /**
     * Fill color (rgb) of the polygon.
     *
     * @return the fill color
     */
    int getFillColor();

    /**
     * Fill opacity of the polygon (between 0 and 1).
     *
     * @return the fill opacity
     */
    float getFillOpacity();
}
