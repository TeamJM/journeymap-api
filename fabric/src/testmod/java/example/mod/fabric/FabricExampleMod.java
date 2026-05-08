/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.fabric;

import example.mod.client.plugin.handler.BedWaypointHandler;
import example.mod.client.plugin.handler.SlimeChunkOverlayHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;

/**
 * Fabric entry point. Wires Fabric API events into the loader-agnostic handlers
 * in {@code example.mod.client.plugin.handler}. The JourneyMap plugin classes
 * are discovered separately via the {@code journeymap} entrypoint in
 * {@code fabric.mod.json}.
 */
public class FabricExampleMod implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        EntitySleepEvents.START_SLEEPING.register((entity, pos) ->
                BedWaypointHandler.onPlayerSlept(
                        pos,
                        entity.level().dimension(),
                        entity.level().isClientSide()));

        ClientChunkEvents.CHUNK_LOAD.register((world, chunk) ->
                SlimeChunkOverlayHandler.onChunkLoad(chunk, world.dimension(), world.isClientSide()));

        ClientChunkEvents.CHUNK_UNLOAD.register((world, chunk) ->
                SlimeChunkOverlayHandler.onChunkUnload(chunk.getPos(), world.isClientSide()));
    }
}
