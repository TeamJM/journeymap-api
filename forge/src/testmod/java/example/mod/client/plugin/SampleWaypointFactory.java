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

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.common.api.waypoint.Waypoint;
import journeymap.common.api.waypoint.WaypointIcon;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.awt.Color;

/**
 * Sample factory that creates a waypoint.
 */
class SampleWaypointFactory
{
    /**
     * ExampleMod will create a waypoint for the bed slept in at the provided coordinates.
     *
     * @param bedLocation
     * @param dimension
     */
    static Waypoint createBedWaypoint(IClientAPI jmAPI, BlockPos bedLocation, ResourceKey<Level> dimension)
    {
        Waypoint bedWaypoint = null;
        try
        {
            // Icon for waypoint
            WaypointIcon bedIcon = new WaypointIcon(new ResourceLocation("examplemod:images/bed.png"), 32, 32);
            bedIcon.setColor(0x00ffff);

            // Waypoint itself
            bedWaypoint = new Waypoint(ExampleMod.MODID, "bed_" + dimension.registry().getPath(), bedLocation, Color.BLUE , dimension);
            bedWaypoint.setIcon(bedIcon);

            // Add or update
//            jmAPI.show(bedWaypoint); // TODO

        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }

        return bedWaypoint;
    }
}
