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

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.ModWaypoint;
import journeymap.client.api.model.MapImage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

/**
 * Sample factory that creates a waypoint.
 */
class SampleModWaypointFactory
{
    /**
     * ExampleMod will create a waypoint for the bed slept in at the provided coordinates.
     *
     * @param bedLocation
     * @param dimension
     */
    static ModWaypoint createBedWaypoint(IClientAPI jmAPI, BlockPos bedLocation, int dimension)
    {
        ModWaypoint bedWaypoint = null;
        try
        {
            // Icon for waypoint
            MapImage bedIcon = new MapImage(new ResourceLocation("examplemod:images/bed.png"), 32, 32)
                    .setAnchorX(16)
                    .setAnchorY(32);

            // Waypoint itself
            bedWaypoint = new ModWaypoint(ExampleMod.MODID, "bed_" + dimension, "Handy Locations", "Bed",
                    dimension, bedLocation, bedIcon, 0xffffff, true);

            // Add or update
            jmAPI.show(bedWaypoint);

        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }

        return bedWaypoint;
    }
}
