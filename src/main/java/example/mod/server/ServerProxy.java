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

package example.mod.server;

import example.mod.CommonProxy;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;


/**
 * Server-sided proxy.  Currently unused.
 */

public class ServerProxy implements CommonProxy
{

    @Override
    public void commonSetupEvent(FMLCommonSetupEvent event)
    {

    }

    @Override
    public void imcEnqueue(InterModEnqueueEvent event)
    {

    }

    @Override
    public void imcHandle(InterModProcessEvent event)
    {

    }

    @Override
    public void loadCompleteEvent(FMLLoadCompleteEvent event)
    {

    }

    @Override
    public void clientSetupEvent(FMLClientSetupEvent event)
    {

    }

    @Override
    public void serverStartingEvent(ServerStartingEvent event)
    {

    }

    @Override
    public void dedicatedServerSetupEvent(FMLDedicatedServerSetupEvent event)
    {

    }
}
