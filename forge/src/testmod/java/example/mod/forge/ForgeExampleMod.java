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
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.invoke.MethodHandles;

/**
 * Forge entry point. Forge discovers this class via the {@link Mod} annotation
 * and forwards Forge events into the loader-agnostic handlers in
 * {@code example.mod.client.plugin.handler}. The JourneyMap plugin classes are
 * discovered separately via the {@code @JourneyMapPlugin} annotation.
 *
 * <p>Forge 1.21.6+ migrated to EventBus 7. Static {@code @SubscribeEvent}
 * methods are registered in bulk via
 * {@code BusGroup.DEFAULT.register(MethodHandles.lookup(), Class)}.</p>
 */
@Mod(ExampleMod.MODID)
public class ForgeExampleMod
{
    public ForgeExampleMod()
    {
        BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeExampleMod.class);
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
