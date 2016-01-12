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

package example.mod.client.facade;

import net.minecraft.world.ChunkCoordIntPair;

/**
 * Interface abstraction for map-related actions that this ExampleMod can do,
 * but without directly referencing any JourneyMap API classes.
 */
public interface IExampleMapFacade
{
    /**
     * Refresh ExampleMod's displayables for the given dimension.
     *
     * @param dimension
     */
    void refreshMap(int dimension);

    /**
     * Whether or not ExampleMod can provide slime chunk overlays.
     *
     * @return true if polygon overlays accepted
     */
    boolean canShowSlimeChunks();

    /**
     * Show an overlay for the chunk to indicate it is a slime chunk.
     *
     * @param chunkCoords
     * @param dimension
     */
    void showSlimeChunk(ChunkCoordIntPair chunkCoords, int dimension);

    /**
     * Remove the slime chunk overlay for the coords.
     *
     * @param chunkCoords
     * @param dimension
     */
    void removeSlimeChunk(ChunkCoordIntPair chunkCoords, int dimension);

    /**
     * Whether or not ExampleMod can provide a bed waypoint.
     *
     * @return true if waypoints accepted
     */
    boolean canShowBedWaypoint();

    /**
     * Create a waypoint for the bed slept in at the provided coordinates.
     *
     * @param x
     * @param y
     * @param z
     * @param dimension
     */
    void showBedWaypoint(int x, int y, int z, int dimension);
}
