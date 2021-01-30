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

package example.mod;

import example.mod.client.ClientProxy;
import example.mod.server.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example mod showing how to use the JourneyMap API.
 */
@Mod(ExampleMod.MODID)
@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class ExampleMod
{
    public static final String MODID = "examplemod-jm";
    public static final String VERSION = "1.6";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MODID);


    /**
     * The constant proxy.
     */
    public static final CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public ExampleMod()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commonSetupEvent(FMLCommonSetupEvent event)
    {
        proxy.commonSetupEvent(event);
        LOGGER.info("ExampleMod commonSetupEvent done");
    }

    @SubscribeEvent
    public void imcEnqueue(InterModEnqueueEvent event)
    {
        proxy.imcEnqueue(event);
        LOGGER.info("ExampleMod imcEnqueue done");
    }

    @SubscribeEvent
    public void imcHandle(InterModProcessEvent event)
    {
        proxy.imcHandle(event);
        LOGGER.info("ExampleMod imcHandle done");
    }

    @SubscribeEvent
    public void loadCompleteEvent(FMLLoadCompleteEvent event)
    {
        proxy.loadCompleteEvent(event);
        LOGGER.info("ExampleMod loadCompleteEvent done");
    }

    @SubscribeEvent
    public void clientSetupEvent(FMLClientSetupEvent event)
    {
        proxy.clientSetupEvent(event);
        LOGGER.info("ExampleMod clientSetupEvent done");
    }

    @SubscribeEvent
    public void serverStartingEvent(FMLServerStartingEvent event)
    {
        proxy.serverStartingEvent(event);
        LOGGER.info("ExampleMod serverStartingEvent done");
    }

    @SubscribeEvent
    public void dedicatedServerSetupEvent(FMLDedicatedServerSetupEvent event)
    {
        proxy.dedicatedServerSetupEvent(event);
        LOGGER.info("ExampleMod dedicatedServerSetupEvent done");
    }
}
