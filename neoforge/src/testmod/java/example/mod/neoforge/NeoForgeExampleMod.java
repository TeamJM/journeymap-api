/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.neoforge;

import example.mod.ExampleMod;
import example.mod.client.plugin.handler.BedWaypointHandler;
import example.mod.client.plugin.handler.SlimeChunkOverlayHandler;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;

/**
 * NeoForge entry point. NeoForge discovers this class via the {@link Mod}
 * annotation and forwards NeoForge events into the loader-agnostic handlers in
 * {@code example.mod.client.plugin.handler}. The JourneyMap plugin classes are
 * discovered separately via the {@code @JourneyMapPlugin} annotation.
 *
 * <p>NeoForge 21.1 dropped the old client-side {@code PlayerSleepInBedEvent};
 * the closest replacement, {@link CanPlayerSleepEvent}, is server-only. The
 * bed-waypoint handler is no-op when called from the server side, so this
 * dispatcher passes through {@code clientSide=false} as a documentation example
 * and the actual waypoint creation still happens on the client via the JM
 * mapping-started flow in {@code ClientEventListener}.</p>
 */
@Mod(ExampleMod.MODID)
public class NeoForgeExampleMod
{
    public NeoForgeExampleMod(IEventBus modEventBus)
    {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerSleep(CanPlayerSleepEvent event)
    {
        BedWaypointHandler.onPlayerSlept(
                event.getPos(),
                event.getEntity().level().dimension(),
                event.getEntity().level().isClientSide());
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event)
    {
        if (event.getChunk() instanceof LevelChunk levelChunk && event.getLevel() instanceof Level level)
        {
            SlimeChunkOverlayHandler.onChunkLoad(levelChunk, level.dimension(), event.getLevel().isClientSide());
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event)
    {
        SlimeChunkOverlayHandler.onChunkUnload(event.getChunk().getPos(), event.getLevel().isClientSide());
    }
}
