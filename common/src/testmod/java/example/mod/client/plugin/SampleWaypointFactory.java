/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.common.waypoint.Waypoint;
import journeymap.api.v2.common.waypoint.WaypointFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.awt.Color;

/**
 * Sample factory that creates a waypoint for a player's last bed location.
 */
public final class SampleWaypointFactory
{
    private SampleWaypointFactory()
    {
    }

    public static Waypoint createBedWaypoint(IClientAPI jmAPI, BlockPos bedLocation, int dimension)
    {
        Waypoint bedWaypoint = null;
        try
        {
            bedWaypoint = WaypointFactory.createWaypoint(ExampleMod.MODID, bedLocation, dimension, true);
            bedWaypoint.setColor(Color.BLUE.getRGB());
            bedWaypoint.setIconIdentifier(new ResourceLocation("examplemod:images/bed.png"));
            bedWaypoint.setIconTextureSize(32, 32);
            bedWaypoint.setIconColor(0x00ffff);
            jmAPI.addWaypoint(ExampleMod.MODID, bedWaypoint);
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }

        return bedWaypoint;
    }
}
