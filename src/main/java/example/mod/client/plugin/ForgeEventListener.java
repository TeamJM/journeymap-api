package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.CustomToolBarBuilder;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.IThemeButton;
import journeymap.client.api.display.IThemeToolBar;
import journeymap.client.api.display.PolygonOverlay;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

/**
 * Listens to Forge events, creates bed waypoints and slime chunk overlays via the ClientAPI.
 */
class ForgeEventListener
{
    IClientAPI jmAPI;
    HashMap<ChunkPos, PolygonOverlay> slimeChunkOverlays;

    /**
     * Constructor.
     *
     * @param jmAPI API implementation
     */
    ForgeEventListener(IClientAPI jmAPI)
    {
        this.jmAPI = jmAPI;
        this.slimeChunkOverlays = new HashMap<ChunkPos, PolygonOverlay>();
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
            if (event.getEntityLiving().getCommandSenderWorld().isClientSide)
            {
                if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint))
                {
                    SampleWaypointFactory.createBedWaypoint(jmAPI, event.getPos(), event.getEntity().level.dimension());
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
            if (event.getWorld().isClientSide())
            {
                if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
                {
                    Chunk chunk = (Chunk) event.getChunk();
                    if (isSlimeChunk(chunk))
                    {
                        ChunkPos chunkCoords = chunk.getPos();
                        if (!slimeChunkOverlays.containsKey(chunkCoords))
                        {
                            RegistryKey<World> dimension = ((World) event.getWorld()).dimension();
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
        if (event.getWorld().isClientSide())
        {
            if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
            {
                ChunkPos chunkCoords = event.getChunk().getPos();
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
     * Creates buttons on the right side of the fullscreen map.
     *
     * @param event - The event
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onFullscreenAddonButton(FullscreenDisplayEvent.AddonButtonDisplayEvent event)
    {
        ThemeButtonDisplay buttonDisplay = event.getThemeButtonDisplay();
        IThemeButton button1 = buttonDisplay.addThemeButton("Test1", "alert", b -> System.out.println("ALERT ALERT"));
        IThemeButton button2 = buttonDisplay.addThemeButton("Test2", "grid", b -> System.out.println("ALERT ALERT"));
        IThemeButton button3 = buttonDisplay.addThemeButton("Test3", "day", b -> System.out.println("ALERT ALERT"));
        IThemeButton button4 = buttonDisplay.addThemeButton("Test4", "biome", b -> System.out.println("ALERT ALERT"));
        IThemeButton button5 = buttonDisplay.addThemeButton("Test5", "keys", b -> System.out.println("ALERT ALERT"));
        IThemeButton button6 = buttonDisplay.addThemeToggleButton("Test6 On", "Text 6 Off", "keys", false, b -> System.out.println("ALERT ALERT"));
    }

    /**
     * Adds buttons to the map type button list at the top of the fullscreen map.
     *
     * @param event - The event
     */
    @OnlyIn(Dist.CLIENT)
    public void onFullscreenMapTypeButton(FullscreenDisplayEvent.MapTypeButtonDisplayEvent event)
    {
        event.getThemeButtonDisplay()
                .addThemeButton("Test MapType", "follow", b -> System.out.println("follow"));
    }

    /**
     * Adds a few toolbars to the fullscreen map.
     *
     * @param event - the event.
     */
    @OnlyIn(Dist.CLIENT)
    public void onCustomToolbarEvent(FullscreenDisplayEvent.CustomToolbarEvent event)
    {
        CustomToolBarBuilder barBuilder = event.getCustomToolBarBuilder();
        Screen screen = event.getFullscreen().getMinecraft().screen;
        int startX = screen.width / 2;

        IThemeButton button1 = barBuilder.getThemeButton("Test1", "alert", b -> System.out.println("ALERT ALERT"));
        IThemeButton button2 = barBuilder.getThemeButton("Test2", "grid", b -> System.out.println("ALERT ALERT"));
        IThemeButton button3 = barBuilder.getThemeButton("Test3", "day", b -> System.out.println("ALERT ALERT"));
        IThemeButton button4 = barBuilder.getThemeButton("Test4", "biome", b -> System.out.println("ALERT ALERT"));
        IThemeButton button5 = barBuilder.getThemeButton("Test5", "keys", b -> System.out.println("ALERT ALERT"));
        IThemeToolBar bar = barBuilder.getNewToolbar(button1, button2, button3, button4, button5);
        bar.setLayoutCenteredHorizontal(screen.width / 2, bar.getHeight() * 2, 5, true);


        IThemeButton button8 = barBuilder.getThemeButton("Test4", "biome", b -> System.out.println("ALERT ALERT"));
        IThemeButton button9 = barBuilder.getThemeButton("Test5", "keys", b -> System.out.println("ALERT ALERT"));
        IThemeToolBar bar3 = barBuilder.getNewToolbar(button8, button9);
        bar3.setLayoutDistributedHorizontal(startX, bar.getHeight() * 4, startX + 40, true);

        IThemeButton button0 = barBuilder.getThemeButton("Test4", "biome", b -> System.out.println("ALERT ALERT"));
        IThemeButton button11 = barBuilder.getThemeButton("Test5", "keys", b -> System.out.println("ALERT ALERT"));
        IThemeToolBar bar4 = barBuilder.getNewToolbar(button0, button11);
        bar4.setLayoutHorizontal(startX, bar.getHeight() * 5, 3, true);
    }

    /**
     * Magic formula for slime chunk discovery.
     *
     * @param chunk the chunk
     * @return true if it's a slime chunk
     */
    private boolean isSlimeChunk(Chunk chunk)
    {
        if (!chunk.getLevel().isClientSide())
        {
            return SharedSeedRandom.seedSlimeChunk(chunk.getPos().x, chunk.getPos().z, chunk.getLevel().getServer().getWorldData().worldGenSettings().seed(), 987234911L).nextInt(10) == 0;
        }
        return false;
    }
}
