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

package example.mod.client;

import example.mod.CommonProxy;
import example.mod.ExampleMod;
import example.mod.client.facade.IExampleMapFacade;
import example.mod.client.listener.ChunkEventListener;
import example.mod.client.listener.SleepEventListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

/**
 * Client-sided proxy. Using a MapFacade interface (unique to this example mod's functionality) prevents
 * the need to directly reference any JourneyMap API classes here.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    // Populated by the ExampleJourneymapPlugin class if/when it is initialized by JourneyMap.
    public static IExampleMapFacade MapFacade;

    // List of event listeners
    private ArrayList<Object> eventListeners = new ArrayList<Object>();

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        if (Loader.isModLoaded("journeymap"))
        {
            // If the plugin was loaded by JourneyMap, MapFacade will be set during init.
            if (MapFacade != null)
            {
                eventListeners.add(new ChunkEventListener());
                eventListeners.add(new SleepEventListener());

                // Register event listeners
                for (Object listener : eventListeners)
                {
                    MinecraftForge.EVENT_BUS.register(listener);
                }
            }
            else
            {
                // You probably have an older release that doesn't implement the API.
                ExampleMod.LOGGER.warn("JourneyMap didn't instantiate the plugin");
            }
        }
        else
        {
            ExampleMod.LOGGER.info("JourneyMap not loaded. Much sadness, much getting lost.");
        }
    }

}
