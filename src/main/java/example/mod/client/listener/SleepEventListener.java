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

package example.mod.client.listener;

import example.mod.client.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Listens to sleep events, creates a waypoint for the bed location via the ClientProxy.IExampleMapFacade interface
 * (if present). Using the facade interface (unique to this example example.mod) prevents the need to directly reference
 * any JourneyMap API classes here.
 */
public class SleepEventListener
{
    /**
     * Listen for Forge PlayerSleepInBedEvents, create a waypoint for the bed if ClientProxy.MapFacade is set.
     */
    @SubscribeEvent
    public void onPlayerSlept(PlayerSleepInBedEvent event)
    {
        if (event.result == EntityPlayer.EnumStatus.OK)
        {
            if (ClientProxy.MapFacade != null && ClientProxy.MapFacade.canShowBedWaypoint())
            {
                ClientProxy.MapFacade.showBedWaypoint(event.pos.getX(), event.pos.getY(), event.pos.getZ(), event.entityPlayer.dimension);
            }
        }
    }
}
