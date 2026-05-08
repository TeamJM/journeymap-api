/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin.handler;

import example.mod.ExampleMod;
import example.mod.client.plugin.SampleWaypointFactory;
import journeymap.api.v2.client.IClientAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Loader-agnostic handler for "player slept" events. Each loader has a thin
 * dispatcher that forwards its own sleep event into {@link #onPlayerSlept}.
 */
public final class BedWaypointHandler
{
    private static IClientAPI jmAPI;

    private BedWaypointHandler()
    {
    }

    public static void setApi(IClientAPI api)
    {
        jmAPI = api;
    }

    public static void onPlayerSlept(BlockPos bedLocation, ResourceKey<Level> dimension, boolean clientSide)
    {
        if (jmAPI == null || !clientSide)
        {
            return;
        }
        try
        {
            SampleWaypointFactory.createBedWaypoint(jmAPI, bedLocation, dimension);
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }
}
