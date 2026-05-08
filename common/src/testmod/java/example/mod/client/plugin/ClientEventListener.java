/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.display.Context;
import journeymap.api.v2.client.display.DisplayType;
import journeymap.api.v2.client.event.DeathWaypointEvent;
import journeymap.api.v2.client.event.DisplayUpdateEvent;
import journeymap.api.v2.client.event.EntityRadarUpdateEvent;
import journeymap.api.v2.client.event.EntityRegistrationEvent;
import journeymap.api.v2.client.event.FullscreenDisplayEvent;
import journeymap.api.v2.client.event.FullscreenMapEvent;
import journeymap.api.v2.client.event.FullscreenRenderEvent;
import journeymap.api.v2.client.event.InfoSlotDisplayEvent;
import journeymap.api.v2.client.event.MappingEvent;
import journeymap.api.v2.client.event.PopupMenuEvent;
import journeymap.api.v2.client.event.RegistryEvent;
import journeymap.api.v2.client.fullscreen.CustomToolBarBuilder;
import journeymap.api.v2.client.fullscreen.IThemeButton;
import journeymap.api.v2.client.fullscreen.IThemeToolBar;
import journeymap.api.v2.client.fullscreen.ThemeButtonDisplay;
import journeymap.api.v2.common.event.ClientEventRegistry;
import journeymap.api.v2.common.event.FullscreenEventRegistry;
import journeymap.api.v2.common.event.MinimapEventRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

/**
 * Subscribes to every client-side JourneyMap event registry. Each handler is a
 * minimal demo of how to read/use the event payload.
 *
 * <p>Scope: this class holds only client UI / mapping events. Cross-side waypoint
 * events live in {@link example.mod.common.plugin.CommonEventListener}; server-only
 * events live in {@link example.mod.server.plugin.ServerEventListener}.</p>
 */
public class ClientEventListener
{
    private final IClientAPI jmAPI;
    private ClientProperties clientProperties;

    public ClientEventListener(IClientAPI jmAPI)
    {
        this.jmAPI = jmAPI;
        registerClientEvents();
        registerFullscreenEvents();
        registerMinimapEvents();
    }

    public ClientProperties getClientProperties()
    {
        return clientProperties;
    }

    // -- ClientEventRegistry --------------------------------------------------

    private void registerClientEvents()
    {
        ClientEventRegistry.MAPPING_EVENT.subscribe(ExampleMod.MODID, this::onMappingEvent);
        ClientEventRegistry.DISPLAY_UPDATE_EVENT.subscribe(ExampleMod.MODID, this::onDisplayUpdate);
        ClientEventRegistry.DEATH_WAYPOINT_EVENT.subscribe(ExampleMod.MODID, this::onDeathWaypoint);
        ClientEventRegistry.ENTITY_RADAR_UPDATE_EVENT.subscribe(ExampleMod.MODID, this::onRadarEntityUpdate);
        ClientEventRegistry.ENTITY_REGISTRATION_EVENT.subscribe(ExampleMod.MODID, this::onEntityRegistration);
        ClientEventRegistry.OPTIONS_REGISTRY_EVENT.subscribe(ExampleMod.MODID, this::onOptionsRegistry);
        ClientEventRegistry.INFO_SLOT_REGISTRY_EVENT.subscribe(ExampleMod.MODID, this::onInfoSlotRegistry);
    }

    private void onMappingEvent(MappingEvent event)
    {
        if (event.getStage() == MappingEvent.Stage.MAPPING_STARTED)
        {
            spawnSampleOverlays(event);
        }
        else
        {
            jmAPI.removeAll(ExampleMod.MODID);
        }
    }

    private void spawnSampleOverlays(MappingEvent event)
    {
        BlockPos pos = Minecraft.getInstance().player.blockPosition();

        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Image))
        {
            SampleImageOverlayFactory.create(jmAPI, pos, 5, 256, 128);
        }
        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Marker))
        {
            SampleMarkerOverlayFactory.create(jmAPI, pos, 64, 256);
        }

        BlockPos sleepPos = Minecraft.getInstance().player.getSleepingPos().orElse(new BlockPos(0, 0, 0));
        SampleWaypointFactory.createBedWaypoint(jmAPI, sleepPos, event.dimension);

        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
        {
            SampleComplexPolygonOverlayFactory.create(jmAPI, pos, event.dimension, 256);
        }
    }

    private void onDisplayUpdate(DisplayUpdateEvent event)
    {
        ExampleMod.LOGGER.debug("Display update for %s", event.uiState);
    }

    private void onDeathWaypoint(DeathWaypointEvent event)
    {
        // Cancel here to suppress the death waypoint creation.
    }

    private void onRadarEntityUpdate(EntityRadarUpdateEvent event)
    {
        if (event.getActiveUiState().ui.equals(Context.UI.Minimap))
        {
            String name = event.getWrappedEntity().getEntityRef().get().getName().getString();
            if (name.toLowerCase().contains("slime"))
            {
                event.getWrappedEntity().setColor(0x0000FF);
                event.getWrappedEntity().setCustomName("SLIME");
            }
        }
    }

    private void onEntityRegistration(EntityRegistrationEvent event)
    {
        // Register additional entities here. The example mod has nothing to add.
    }

    private void onOptionsRegistry(RegistryEvent.OptionsRegistryEvent event)
    {
        this.clientProperties = new ClientProperties();
    }

    private void onInfoSlotRegistry(RegistryEvent.InfoSlotRegistryEvent event)
    {
        event.register(ExampleMod.MODID, "Current Millis", 1000, () -> "Millis: " + System.currentTimeMillis());
        event.register(ExampleMod.MODID, "Current Ticks", 10, ClientEventListener::getTicks);
    }

    // -- FullscreenEventRegistry ---------------------------------------------

    private void registerFullscreenEvents()
    {
        FullscreenEventRegistry.FULLSCREEN_MAP_CLICK_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenClick);
        FullscreenEventRegistry.FULLSCREEN_MAP_DRAG_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenDrag);
        FullscreenEventRegistry.FULLSCREEN_MAP_MOVE_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenMove);
        FullscreenEventRegistry.FULLSCREEN_POPUP_MENU_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenPopupMenu);
        FullscreenEventRegistry.WAYPOINT_POPUP_MENU_EVENT.subscribe(ExampleMod.MODID, this::onWaypointPopupMenu);
        FullscreenEventRegistry.CUSTOM_TOOLBAR_UPDATE_EVENT.subscribe(ExampleMod.MODID, this::onCustomToolbar);
        FullscreenEventRegistry.ADDON_BUTTON_DISPLAY_EVENT.subscribe(ExampleMod.MODID, this::onAddonButtons);
        FullscreenEventRegistry.MAP_TYPE_BUTTON_DISPLAY_EVENT.subscribe(ExampleMod.MODID, this::onMapTypeButton);
        FullscreenEventRegistry.FULLSCREEN_RENDER_EVENT.subscribe(ExampleMod.MODID, this::onFullscreenRender);
    }

    private void onFullscreenClick(FullscreenMapEvent.ClickEvent event)
    {
        ExampleMod.LOGGER.debug("Fullscreen click stage=%s button=%s", event.getStage(), event.getButton());
    }

    private void onFullscreenDrag(FullscreenMapEvent.MouseDraggedEvent event)
    {
        ExampleMod.LOGGER.debug("Fullscreen drag stage=%s", event.getStage());
    }

    private void onFullscreenMove(FullscreenMapEvent.MouseMoveEvent event)
    {
        // Fires constantly while the mouse moves over the fullscreen map.
    }

    private void onFullscreenPopupMenu(PopupMenuEvent.FullscreenPopupMenuEvent event)
    {
        event.getPopupMenu().addMenuItem("Hello from " + ExampleMod.MODID, b -> ExampleMod.LOGGER.info("hello"));
    }

    private void onWaypointPopupMenu(PopupMenuEvent.WaypointPopupMenuEvent event)
    {
        event.getPopupMenu().addMenuItem("Log waypoint", b ->
                ExampleMod.LOGGER.info("waypoint name=%s", event.getWaypoint().getName()));
    }

    private void onCustomToolbar(FullscreenDisplayEvent.CustomToolbarEvent event)
    {
        CustomToolBarBuilder barBuilder = event.getCustomToolBarBuilder();
        Screen screen = event.getFullscreen().getMinecraft().screen;
        int startX = screen.width / 2;

        IThemeButton b1 = barBuilder.getThemeButton("Test1", icon("alert"), b -> System.out.println("ALERT"));
        IThemeButton b2 = barBuilder.getThemeButton("Test2", icon("grid"), b -> System.out.println("GRID"));
        IThemeButton b3 = barBuilder.getThemeButton("Test3", icon("day"), b -> System.out.println("DAY"));
        IThemeButton b4 = barBuilder.getThemeButton("Test4", icon("biome"), b -> System.out.println("BIOME"));
        IThemeButton b5 = barBuilder.getThemeButton("Test5", icon("keys"), b -> System.out.println("KEYS"));

        IThemeToolBar bar = barBuilder.getNewToolbar(b1, b2, b3, b4, b5);
        bar.setLayoutCenteredHorizontal(screen.width / 2, bar.getHeight() * 2, 5, true);

        IThemeToolBar bar3 = barBuilder.getNewToolbar(
                barBuilder.getThemeButton("Test4", icon("biome"), b -> System.out.println("BIOME")),
                barBuilder.getThemeButton("Test5", icon("keys"), b -> System.out.println("KEYS")));
        bar3.setLayoutDistributedHorizontal(startX, bar.getHeight() * 4, startX + 40, true);

        IThemeToolBar bar4 = barBuilder.getNewToolbar(
                barBuilder.getThemeButton("Test4", icon("biome"), b -> System.out.println("BIOME")),
                barBuilder.getThemeButton("Test5", icon("keys"), b -> System.out.println("KEYS")));
        bar4.setLayoutHorizontal(startX, bar.getHeight() * 5, 3, true);
    }

    private void onAddonButtons(FullscreenDisplayEvent.AddonButtonDisplayEvent event)
    {
        ThemeButtonDisplay buttonDisplay = event.getThemeButtonDisplay();
        buttonDisplay.addThemeButton("Test1", icon("alert"), b -> System.out.println("ALERT"));
        buttonDisplay.addThemeButton("Test2", icon("grid"), b -> System.out.println("GRID"));
        buttonDisplay.addThemeButton("Test3", icon("day"), b -> System.out.println("DAY"));
        buttonDisplay.addThemeButton("Test4", icon("biome"), b -> System.out.println("BIOME"));
        buttonDisplay.addThemeButton("Test5", icon("keys"), b -> System.out.println("KEYS"));
        buttonDisplay.addThemeToggleButton("Test6 On", "Test6 Off", icon("keys"), false, b -> System.out.println("toggle"));
    }

    private void onMapTypeButton(FullscreenDisplayEvent.MapTypeButtonDisplayEvent event)
    {
        event.getThemeButtonDisplay()
                .addThemeButton("Test MapType", icon("follow"), b -> System.out.println("follow"));
    }

    private void onFullscreenRender(FullscreenRenderEvent event)
    {
        // Custom over-the-map render goes here. Left empty for the example.
    }

    // -- MinimapEventRegistry ------------------------------------------------

    private void registerMinimapEvents()
    {
        MinimapEventRegistry.INFO_SLOT_DISPLAY_EVENT.subscribe(ExampleMod.MODID, this::onInfoSlotDisplay);
    }

    private void onInfoSlotDisplay(InfoSlotDisplayEvent event)
    {
        // Reorder, replace, or insert entries in event.getInfoSlotMap() here.
    }

    // -- helpers --------------------------------------------------------------

    private static Identifier icon(String name)
    {
        return Identifier.fromNamespaceAndPath("journeymap", "/resources/assets/journeymap/theme/flat/icon/" + name + ".png");
    }

    private static String getTicks()
    {
        return "Ticks: " + Minecraft.getInstance().gui.getGuiTicks();
    }
}
