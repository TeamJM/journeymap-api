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
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Loader-agnostic chunk-load and chunk-unload handlers. Each loader has a thin
 * dispatcher that forwards its own chunk events into the methods here.
 *
 * <p>Logic: when a slime chunk loads, show a green polygon overlay; when the
 * chunk unloads, remove the overlay.</p>
 */
public final class SlimeChunkOverlayHandler
{
    private static final Map<ChunkCoordIntPair, PolygonOverlay> SLIME_OVERLAYS = new HashMap<>();
    private static IClientAPI jmAPI;

    private SlimeChunkOverlayHandler()
    {
    }

    public static void setApi(IClientAPI api)
    {
        jmAPI = api;
    }

    public static void onChunkLoad(Chunk chunk, int dimension, boolean clientSide)
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
            // 1.7.10 Chunk has no getPos(); its chunk coordinate is the pair of public final int
            // fields xPosition/zPosition.
            ChunkCoordIntPair chunkCoords = new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
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

    public static void onChunkUnload(ChunkCoordIntPair chunkCoords, boolean clientSide)
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

    private static boolean isSlimeChunk(Chunk chunk)
    {
        // 1.7.10 Chunk has no getWorld(); its World is the public field worldObj. World also has
        // no getMinecraftServer() at all (only WorldServer does), so the instanceof check has to
        // come before that lookup rather than short-circuiting alongside it.
        if (chunk.worldObj.isRemote || !(chunk.worldObj instanceof WorldServer))
        {
            return false;
        }
        WorldServer worldServer = (WorldServer) chunk.worldObj;
        // WorldServer#getMinecraftServer() has no MCP name in stable_12; call the stable_12
        // (func_) name directly, per the mapping-table's member-name-drift rule.
        if (worldServer.func_73046_m() == null)
        {
            return false;
        }
        long seed = worldServer.getSeed();
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        // Classic vanilla slime-chunk algorithm (same formula used by EntitySlime.canSpawnHere).
        Random slimeRandom = new Random(seed
                + (long) (chunkX * chunkX * 4987142)
                + (long) (chunkX * 5947611)
                + (long) (chunkZ * chunkZ) * 4392871L
                + (long) (chunkZ * 389711) ^ 987234911L);
        return slimeRandom.nextInt(10) == 0;
    }
}
