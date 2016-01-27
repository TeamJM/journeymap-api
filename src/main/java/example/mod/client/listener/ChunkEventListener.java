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

import example.mod.ExampleMod;
import example.mod.client.ClientProxy;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Listens to Forge events, creates waypoints and overlays via this example.mod's ClientProxy.IExampleMapFacade interface
 * (if present). Using a facade circumvents the need to directly reference any JourneyMap API classes here.
 */
public class ChunkEventListener
{
    /**
     * Listen for Forge chunk load, show polygon overlay if it is a slime chunk and if ClientProxy.MapFacade is set.
     */
    @SubscribeEvent
    public void onChunkLoadEvent(ChunkEvent.Load event)
    {
        try
        {
            if (event.world.isRemote)
            {
                if (ClientProxy.MapFacade.canShowSlimeChunks())
                {
                    Chunk chunk = event.getChunk();
                    if (isSlimeChunk(chunk))
                    {
                        ClientProxy.MapFacade.showSlimeChunk(chunk.getChunkCoordIntPair(), event.world.provider.getDimensionId());
                    }
                }
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    /**
     * Listen for Forge chunk unload, remove polygon overlay if it is a slime chunk and if ClientProxy.MapFacade is set.
     */
    @SubscribeEvent
    public void onChunkUnloadEvent(ChunkEvent.Unload event)
    {
        if (ClientProxy.MapFacade != null && ClientProxy.MapFacade.canShowSlimeChunks())
        {
            Chunk chunk = event.getChunk();
            if (isSlimeChunk(chunk))
            {
                ClientProxy.MapFacade.removeSlimeChunk(chunk.getChunkCoordIntPair(), event.world.provider.getDimensionId());
            }
        }
    }

    /**
     * Magic formula for slime chunk discovery.
     *
     * @param chunk the chunk
     * @return true if it's a slime chunk
     */
    private boolean isSlimeChunk(Chunk chunk)
    {
        return chunk.getRandomWithSeed(987234911L).nextInt(10) == 0;
    }
}
