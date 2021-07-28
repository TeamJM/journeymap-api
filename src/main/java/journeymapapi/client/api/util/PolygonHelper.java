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

package journeymapapi.client.api.util;

import journeymapapi.client.api.model.MapPolygon;
import net.minecraft.core.BlockPos;

/**
 * Utility class related to Polygons.
 */
public class PolygonHelper
{
    /**
     * Creates a polygon for the chunk containing worldCoords, starting with the lower-left (southwest) corner
     * and going counter-clockwise.
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
     * Creates a polygon for the chunk coords, starting with the lower-left (southwest) corner
     * and going counter-clockwise.
     *
     * @param chunkX chunk x
     * @param y      block y
     * @param chunkZ chunk z
     * @return polygon
     */
    public static MapPolygon createChunkPolygon(int chunkX, int y, int chunkZ)
    {
        int x = chunkX << 4;
        int z = chunkZ << 4;
        net.minecraft.core.BlockPos sw = new net.minecraft.core.BlockPos(x, y, z + 16);
        net.minecraft.core.BlockPos se = new BlockPos(x + 16, y, z + 16);
        net.minecraft.core.BlockPos ne = new net.minecraft.core.BlockPos(x + 16, y, z);
        net.minecraft.core.BlockPos nw = new net.minecraft.core.BlockPos(x, y, z);

        return new MapPolygon(sw, se, ne, nw);
    }
}
