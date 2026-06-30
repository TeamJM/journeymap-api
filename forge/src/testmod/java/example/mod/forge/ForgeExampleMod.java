/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.forge;

import example.mod.ExampleMod;
import example.mod.client.plugin.handler.BedWaypointHandler;
import example.mod.client.plugin.handler.SlimeChunkOverlayHandler;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Forge entry point. Forge discovers this class via the {@link Mod} annotation
 * and forwards Forge events into the loader-agnostic handlers in
 * {@code example.mod.client.plugin.handler}. The JourneyMap plugin classes are
 * discovered separately via the {@code @JourneyMapPlugin} annotation.
 *
 * <p>Forge 1.20.1 uses EventBus 5. The static {@code @SubscribeEvent} game
 * events below are registered on the Forge event bus via
 * {@code MinecraftForge.EVENT_BUS.register(Class)}.</p>
 */
@Mod(ExampleMod.MODID)
public class ForgeExampleMod
{
    public ForgeExampleMod()
    {
        MinecraftForge.EVENT_BUS.register(ForgeExampleMod.class);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        BedWaypointHandler.onPlayerSlept(
                event.getPos(),
                event.getEntity().level().dimension(),
                event.getEntity().level().isClientSide());
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event)
    {
        if (event.getChunk() instanceof LevelChunk levelChunk && event.getLevel() instanceof Level level)
        {
            SlimeChunkOverlayHandler.onChunkLoad(levelChunk, level.dimension(), event.getLevel().isClientSide());
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event)
    {
        SlimeChunkOverlayHandler.onChunkUnload(event.getChunk().getPos(), event.getLevel().isClientSide());
    }
}
