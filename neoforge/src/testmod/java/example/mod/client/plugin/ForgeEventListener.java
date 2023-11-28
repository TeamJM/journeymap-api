package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.Context;
import journeymap.client.api.display.CustomToolBarBuilder;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.display.IThemeButton;
import journeymap.client.api.display.IThemeToolBar;
import journeymap.client.api.display.PolygonOverlay;
import journeymap.client.api.display.ThemeButtonDisplay;
import journeymap.client.api.event.DeathWaypointEvent;
import journeymap.client.api.event.EntityRadarUpdateEvent;
import journeymap.client.api.event.FullscreenDisplayEvent;
import journeymap.client.api.event.MappingEvent;
import journeymap.client.api.event.RegistryEvent;
import journeymap.common.api.event.ClientEventRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSleepInBedEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;

import java.util.HashMap;

/**
 * Listens to Forge events, creates bed waypoints and slime chunk overlays via the ClientAPI.
 */
class ForgeEventListener
{
    IClientAPI jmAPI;
    HashMap<ChunkPos, PolygonOverlay> slimeChunkOverlays;

    private ClientProperties clientProperties;

    /**
     * Constructor.
     *
     * @param jmAPI API implementation
     */
    ForgeEventListener(IClientAPI jmAPI)
    {
        this.jmAPI = jmAPI;
        this.slimeChunkOverlays = new HashMap<>();
        ClientEventRegistry.ADDON_BUTTON_DISPLAY_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenAddonButton);
        ClientEventRegistry.MAP_TYPE_BUTTON_DISPLAY_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenMapTypeButton);
        ClientEventRegistry.CUSTOM_TOOLBAR_UPDATE_EVENT.subscribe(ExampleMod.MODID, this::onCustomToolbarEvent);
        ClientEventRegistry.ENTITY_RADAR_UPDATE_EVENT.subscribe(ExampleMod.MODID, this::onRadarEntityUpdateEvent);
        ClientEventRegistry.MAPPING_EVENT.subscribe(ExampleMod.MODID, this::mappingStageEvent);
        ClientEventRegistry.DEATH_WAYPOINT_EVENT.subscribe(ExampleMod.MODID, this::onDeathpoint);
        ClientEventRegistry.REGISTRY_EVENT.subscribe(ExampleMod.MODID, this::registryEvent);
    }

    private void registryEvent(RegistryEvent event)
    {

        switch (event.getRegistryType())
        {
            case OPTIONS -> this.clientProperties = new ClientProperties();
            case INFO_SLOT ->
            {
                ((RegistryEvent.InfoSlotRegistryEvent) event)
                        .register(ExampleMod.MODID, "Current Millis", 1000, () -> "Millis: " + System.currentTimeMillis());
                ((RegistryEvent.InfoSlotRegistryEvent) event)
                        .register(ExampleMod.MODID, "Current Ticks", 10, ForgeEventListener::getTicks);
            }
        }
    }

    void mappingStageEvent(MappingEvent event)
    {
        if (event.getStage() == MappingEvent.Stage.MAPPING_STARTED)
        {
            onMappingStarted(event);
        }
        else
        {
            // When mapping has stopped, remove all overlays
            // Clear everything
            jmAPI.removeAll(ExampleMod.MODID);
        }
    }

    /**
     * When mapping has started, generate a bunch of random overlays.
     *
     * @param event client event
     */
    void onMappingStarted(MappingEvent event)
    {
        // Create a bunch of random Image Overlays around the player
        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Image))
        {
            BlockPos pos = Minecraft.getInstance().player.blockPosition();
            SampleImageOverlayFactory.create(jmAPI, pos, 5, 256, 128);
        }

        // Create a bunch of random Marker Overlays around the player
        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Marker))
        {
            net.minecraft.core.BlockPos pos = Minecraft.getInstance().player.blockPosition();
            SampleMarkerOverlayFactory.create(jmAPI, pos, 64, 256);
        }

        // Create a waypoint for the player's bed location.  The ForgeEventListener
        // will keep it updated if the player sleeps elsewhere.
//        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint)) // TODO
//        {
        BlockPos pos = Minecraft.getInstance().player.getSleepingPos().orElse(new BlockPos(0, 0, 0));
        SampleWaypointFactory.createBedWaypoint(jmAPI, pos, event.dimension);
//        }

        // Create some random complex polygon overlays
        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
        {
            BlockPos playerPos = Minecraft.getInstance().player.blockPosition();
            SampleComplexPolygonOverlayFactory.create(jmAPI, playerPos, event.dimension, 256);
        }

        // Slime chunk Polygon Overlays are created by the ForgeEventListener
        // as chunks load, so no need to do anything here.
    }

    /**
     * Do something when JourneyMap is about to create a Deathpoint.
     */
    void onDeathpoint(DeathWaypointEvent event)
    {
        // Could cancel the event, which would prevent the Deathpoint from actually being created.
        // For now, don't do anything.
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
            if (event.getEntity().getCommandSenderWorld().isClientSide)
            {
//                if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint)) // TODO
//                {
                SampleWaypointFactory.createBedWaypoint(jmAPI, event.getPos(), event.getEntity().level().dimension());
//                }
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
            if (event.getLevel().isClientSide())
            {
                if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
                {
                    LevelChunk chunk = (LevelChunk) event.getChunk();
                    if (isSlimeChunk(chunk))
                    {
                        ChunkPos chunkCoords = chunk.getPos();
                        if (!slimeChunkOverlays.containsKey(chunkCoords))
                        {
                            ResourceKey<Level> dimension = ((Level) event.getLevel()).dimension();
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
        if (event.getLevel().isClientSide())
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
    public void onFullscreenAddonButton(FullscreenDisplayEvent.AddonButtonDisplayEvent event)
    {
        ThemeButtonDisplay buttonDisplay = event.getThemeButtonDisplay();
        IThemeButton button1 = buttonDisplay.addThemeButton("Test1", getIcon("alert"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button2 = buttonDisplay.addThemeButton("Test2", getIcon("grid"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button3 = buttonDisplay.addThemeButton("Test3", getIcon("day"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button4 = buttonDisplay.addThemeButton("Test4", getIcon("biome"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button5 = buttonDisplay.addThemeButton("Test5", getIcon("keys"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button6 = buttonDisplay.addThemeToggleButton("Test6 On", "Text 6 Off", getIcon("keys"), false, b -> System.out.println("ALERT ALERT"));
    }

    /**
     * Adds buttons to the map type button list at the top of the fullscreen map.
     *
     * @param event - The event
     */
    @OnlyIn(Dist.CLIENT)
    public void onFullscreenMapTypeButton(FullscreenDisplayEvent.MapTypeButtonDisplayEvent event)
    {
        event.getThemeButtonDisplay().addThemeButton("Test MapType", getIcon("follow"), b -> System.out.println("follow"));
    }

    /**
     * Adds a few toolbars to the fullscreen map.
     *
     * @param event - the event.
     */

    public void onCustomToolbarEvent(FullscreenDisplayEvent.CustomToolbarEvent event)
    {
        CustomToolBarBuilder barBuilder = event.getCustomToolBarBuilder();
        Screen screen = event.getFullscreen().getMinecraft().screen;
        int startX = screen.width / 2;

        IThemeButton button1 = barBuilder.getThemeButton("Test1", getIcon("alert"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button2 = barBuilder.getThemeButton("Test2", getIcon("grid"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button3 = barBuilder.getThemeButton("Test3", getIcon("day"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button4 = barBuilder.getThemeButton("Test4", getIcon("biome"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button5 = barBuilder.getThemeButton("Test5", getIcon("keys"), b -> System.out.println("ALERT ALERT"));
        IThemeToolBar bar = barBuilder.getNewToolbar(button1, button2, button3, button4, button5);
        bar.setLayoutCenteredHorizontal(screen.width / 2, bar.getHeight() * 2, 5, true);


        IThemeButton button8 = barBuilder.getThemeButton("Test4", getIcon("biome"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button9 = barBuilder.getThemeButton("Test5", getIcon("keys"), b -> System.out.println("ALERT ALERT"));
        IThemeToolBar bar3 = barBuilder.getNewToolbar(button8, button9);
        bar3.setLayoutDistributedHorizontal(startX, bar.getHeight() * 4, startX + 40, true);

        IThemeButton button0 = barBuilder.getThemeButton("Test4", getIcon("biome"), b -> System.out.println("ALERT ALERT"));
        IThemeButton button11 = barBuilder.getThemeButton("Test5", getIcon("keys"), b -> System.out.println("ALERT ALERT"));
        IThemeToolBar bar4 = barBuilder.getNewToolbar(button0, button11);
        bar4.setLayoutHorizontal(startX, bar.getHeight() * 5, 3, true);
    }

    public void onRadarEntityUpdateEvent(EntityRadarUpdateEvent event)
    {

        if (event.getActiveUiState().ui.equals(Context.UI.Minimap))
        {
            if (((Component) event.getWrappedEntity().getEntityRef().get().getName()).getString().contains("slime"))
            {
                event.getWrappedEntity().setColor(0x0000FF);
                event.getWrappedEntity().setCustomName("SLIME");
            }
        }
    }

    /**
     * Magic formula for slime chunk discovery.
     *
     * @param chunk the chunk
     * @return true if it's a slime chunk
     */
    private boolean isSlimeChunk(LevelChunk chunk)
    {
        if (!chunk.getLevel().isClientSide())
        {
            return WorldgenRandom.seedSlimeChunk(chunk.getPos().x, chunk.getPos().z, chunk.getLevel().getServer().getWorldData().worldGenOptions().seed(), 987234911L).nextInt(10) == 0;
        }
        return false;
    }

    private ResourceLocation getIcon(String string)
    {
        return new ResourceLocation("journeymap", "/resources/assets/journeymap/theme/flat/icon/" + string + ".png");
    }

    private static String getTicks()
    {
        return "Ticks: " + Minecraft.getInstance().gui.getGuiTicks();
    }
}
