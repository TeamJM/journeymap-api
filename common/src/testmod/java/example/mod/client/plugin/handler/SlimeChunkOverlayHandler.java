/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin.handler;

import example.mod.ExampleMod;
import example.mod.client.plugin.SamplePolygonOverlayFactory;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.display.DisplayType;
import journeymap.api.v2.client.display.PolygonOverlay;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.WorldgenRandom;

import java.util.HashMap;
import java.util.Map;

/**
 * Loader-agnostic chunk-load and chunk-unload handlers. Each loader has a thin
 * dispatcher that forwards its own chunk events into the methods here.
 *
 * <p>Logic: when a slime chunk loads, show a green polygon overlay; when the
 * chunk unloads, remove the overlay.</p>
 */
public final class SlimeChunkOverlayHandler
{
    private static final Map<ChunkPos, PolygonOverlay> SLIME_OVERLAYS = new HashMap<>();
    private static IClientAPI jmAPI;

    private SlimeChunkOverlayHandler()
    {
    }

    public static void setApi(IClientAPI api)
    {
        jmAPI = api;
    }

    public static void onChunkLoad(LevelChunk chunk, ResourceKey<Level> dimension, boolean clientSide)
    {
        if (jmAPI == null || !clientSide)
        {
            return;
        }
        try
        {
            if (!jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
            {
                return;
            }
            if (!isSlimeChunk(chunk))
            {
                return;
            }
            ChunkPos chunkCoords = chunk.getPos();
            if (SLIME_OVERLAYS.containsKey(chunkCoords))
            {
                return;
            }
            PolygonOverlay overlay = SamplePolygonOverlayFactory.create(chunkCoords, dimension);
            SLIME_OVERLAYS.put(chunkCoords, overlay);
            jmAPI.show(overlay);
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    public static void onChunkUnload(ChunkPos chunkCoords, boolean clientSide)
    {
        if (jmAPI == null || !clientSide)
        {
            return;
        }
        PolygonOverlay overlay = SLIME_OVERLAYS.remove(chunkCoords);
        if (overlay != null)
        {
            jmAPI.remove(overlay);
        }
    }

    private static boolean isSlimeChunk(LevelChunk chunk)
    {
        if (chunk.getLevel().isClientSide()
                || chunk.getLevel().getServer() == null
                || !(chunk.getLevel() instanceof ServerLevel serverLevel))
        {
            return false;
        }
        long seed = serverLevel.getSeed();
        return WorldgenRandom.seedSlimeChunk(chunk.getPos().x(), chunk.getPos().z(), seed, 987234911L).nextInt(10) == 0;
    }
}
