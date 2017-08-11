package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.PolygonOverlay;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

/**
 * Listens to Forge events, creates bed waypoints and slime chunk overlays via the ClientAPI.
 */
class ForgeEventListener
{
    IClientAPI jmAPI;
    HashMap<ChunkCoordIntPair, PolygonOverlay> slimeChunkOverlays;

    /**
     * Constructor.
     *
     * @param jmAPI API implementation
     */
    ForgeEventListener(IClientAPI jmAPI)
    {
        this.jmAPI = jmAPI;
        this.slimeChunkOverlays = new HashMap<ChunkCoordIntPair, PolygonOverlay>();
    }

    /**
     * Listen for Forge PlayerSleepInBedEvents, create a waypoint for the bed.
     * This is just a quick example, and doesn't take into account whether the player successfully slept.
     */
    @SubscribeEvent
    public void onPlayerSlept(PlayerSleepInBedEvent event)
    {
        try
        {
            if (event.entityLiving.getEntityWorld().isRemote)
            {
                if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint))
                {
                    SampleWaypointFactory.createBedWaypoint(jmAPI, event.pos, event.entityLiving.dimension);
                }
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    /**
     * Listen for Forge chunk load, show polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkLoadEvent(ChunkEvent.Load event)
    {
        try
        {
            if (event.world.isRemote)
            {
                if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
                {
                    Chunk chunk = event.getChunk();
                    if (isSlimeChunk(chunk))
                    {
                        ChunkCoordIntPair chunkCoords = chunk.getChunkCoordIntPair();
                        if (!slimeChunkOverlays.containsKey(chunkCoords))
                        {
                            int dimension = event.world.provider.getDimensionId();
                            PolygonOverlay overlay = SamplePolygonOverlayFactory.create(chunkCoords, dimension);
                            slimeChunkOverlays.put(chunkCoords, overlay);
                            jmAPI.show(overlay);
                        }
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
     * Listen for Forge chunk unload, remove polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkUnloadEvent(ChunkEvent.Unload event)
    {
        if (event.world.isRemote)
        {
            if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
            {
                ChunkCoordIntPair chunkCoords = event.getChunk().getChunkCoordIntPair();
                if (!slimeChunkOverlays.containsKey(chunkCoords))
                {
                    PolygonOverlay overlay = slimeChunkOverlays.remove(chunkCoords);
                    if (overlay != null)
                    {
                        jmAPI.remove(overlay);
                    }
                }
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
