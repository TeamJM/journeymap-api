/**
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

package journeymap.api.v2.client.map;

import net.minecraft.world.level.ChunkPos;

import javax.annotation.Nullable;

/**
 * One chunk's scan results, produced once per mapping pass and shared by every {@link IMapLayer}: the
 * surface column data of all 256 columns and, for the active cave slices, the per-slice column data.
 * Read-only.
 */
public interface IChunkScan
{
    /**
     * @return the chunk position
     */
    ChunkPos chunkPos();

    /**
     * @param x chunk-local x (0..15)
     * @param z chunk-local z (0..15)
     * @return the column's surface data, or {@code null} when the column could not be scanned
     */
    @Nullable
    IColumnData surfaceColumn(int x, int z);

    /**
     * @param sliceIndex a cave slice index
     * @return the slice's column data indexed {@code [x][z]}, or {@code null} when the slice was not scanned
     *         for this chunk
     */
    @Nullable
    ICaveSliceData[][] caveSlice(int sliceIndex);
}
