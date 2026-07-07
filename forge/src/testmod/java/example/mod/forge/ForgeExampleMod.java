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
import journeymap.api.v2.common.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.ChunkEvent;

/**
 * Forge entry point. Forge discovers this class via the {@link Mod} annotation
 * and forwards Forge events into the loader-agnostic handlers in
 * {@code example.mod.client.plugin.handler}. The JourneyMap plugin classes are
 * discovered separately via the {@code @JourneyMapPlugin} annotation.
 *
 * <p>Forge 1.7.10 uses the legacy FML EventBus. The static {@code @SubscribeEvent} game
 * events below are registered on the Forge event bus via
 * {@code MinecraftForge.EVENT_BUS.register(Class)}.</p>
 */
@Mod(modid = ExampleMod.MODID)
public class ForgeExampleMod
{
    public ForgeExampleMod()
    {
        MinecraftForge.EVENT_BUS.register(ForgeExampleMod.class);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        // 1.7.10 PlayerSleepInBedEvent carries raw x/y/z ints (no BlockPos yet) and its player is
        // the public field entityPlayer, not a getEntityPlayer() accessor. EntityPlayer's own
        // World is the inherited field worldObj, and World.provider.dimensionId is a plain int
        // field, not a getDimension() method.
        BedWaypointHandler.onPlayerSlept(
                new BlockPos(event.x, event.y, event.z),
                event.entityPlayer.worldObj.provider.dimensionId,
                event.entityPlayer.worldObj.isRemote);
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event)
    {
        // 1.7.10 ChunkEvent.getChunk() still exists, but ChunkEvent has no getWorld(); its World
        // is the public field world (inherited from WorldEvent).
        if (event.getChunk() instanceof Chunk && event.world instanceof World)
        {
            Chunk chunk = event.getChunk();
            World world = event.world;
            SlimeChunkOverlayHandler.onChunkLoad(chunk, world.provider.dimensionId, world.isRemote);
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event)
    {
        // 1.7.10 Chunk has no getPos(); its chunk coordinate is the pair of public final int
        // fields xPosition/zPosition.
        Chunk chunk = event.getChunk();
        SlimeChunkOverlayHandler.onChunkUnload(new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition), event.world.isRemote);
    }
}
