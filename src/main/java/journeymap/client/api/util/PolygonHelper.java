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

package journeymap.client.api.util;

import journeymap.client.api.model.MapPoint;
import journeymap.client.api.model.MapPolygon;

/**
 * Utility class related to Polygons
 */
public class PolygonHelper
{
    /**
     * Creates a polygon for the chunk containing worldCoords.
     *
     * @param x world X
     * @param y world Y
     * @param z world Z
     * @return a polygon for the surrounding chunk
     */
    public static MapPolygon createChunkPolygonForWorldCoords(int x, int y, int z)
    {
        return createChunkPolygon(x >> 4, y, z >> 4);
    }


    /**
     * Creates a polygon for the chunk coords.
     *
     * @param chunkX  chunk x
     * @param worldY  y
     * @param chunkZ  chunk z
     * @return polygon
     */
    public static MapPolygon createChunkPolygon(int chunkX, int worldY, int chunkZ)
    {
        MapPoint p = new MapPoint(chunkX << 4, worldY, chunkZ << 4);
        return new MapPolygon(p, p.offset(15, 0), p.offset(15, 15), p.offset(0, 15), p);
    }
}
