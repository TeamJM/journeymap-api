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

/**
 * One transparent stratum above a column's surface block (water, glass, ice, leaves), as blended into the
 * surface color. Read-only.
 */
public interface IStrataEntry
{
    /**
     * @return the stratum's color (RGB)
     */
    int color();

    /**
     * @return the stratum's opacity (0..1)
     */
    float alpha();

    /**
     * @return the block light at the stratum (0..15)
     */
    int lightLevel();

    /**
     * @return the summed light opacity of the strata above this one
     */
    int lightAttenuation();

    /**
     * @return whether a fluid lies above this stratum
     */
    boolean fluidAbove();
}
