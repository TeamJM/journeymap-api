/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.display.DisplayType;
import journeymap.client.api.event.ClientEvent;
import journeymap.client.api.event.DeathWaypointEvent;
import journeymap.client.api.event.RegistryEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;

import static journeymap.client.api.event.ClientEvent.Type.DEATH_WAYPOINT;
import static journeymap.client.api.event.ClientEvent.Type.MAPPING_STARTED;
import static journeymap.client.api.event.ClientEvent.Type.MAPPING_STOPPED;
import static journeymap.client.api.event.ClientEvent.Type.REGISTRY;

/**
 * Example plugin implementation by the example mod. To prevent classloader errors if JourneyMap isn't loaded
 * (and thus the API classes aren't loaded), this class isn't referenced anywhere directly in the mod.
 * <p>
 * The @journeymap.client.api.ClientPlugin annotation makes this plugin class discoverable to JourneyMap,
 * which will create an instance of it and then call initialize on it.
 * <p>
 * The
 */
@ParametersAreNonnullByDefault
@journeymap.client.api.ClientPlugin
public class ExampleJourneymapPlugin implements IClientPlugin
{
    // API reference
    private IClientAPI jmAPI = null;

    // Forge listener reference
    private ForgeEventListener forgeEventListener;
    private ClientProperties clientProperties;

    private static ExampleJourneymapPlugin INSTANCE;

    public ExampleJourneymapPlugin()
    {
        INSTANCE = this;
    }

    public static ExampleJourneymapPlugin getInstance()
    {
        return INSTANCE;
    }

    public ClientProperties getClientProperties()
    {
        return clientProperties;
    }

    /**
     * Called by JourneyMap during the init phase of mod loading.  The IClientAPI reference is how the mod
     * will add overlays, etc. to JourneyMap.
     *
     * @param jmAPI Client API implementation
     */
    @Override
    public void initialize(final IClientAPI jmAPI)
    {
        // Set ClientProxy.SampleWaypointFactory with an implementation that uses the JourneyMap IClientAPI under the covers.
        this.jmAPI = jmAPI;

        // Register listener for forge events
        forgeEventListener = new ForgeEventListener(jmAPI);
        MinecraftForge.EVENT_BUS.register(forgeEventListener);

        // Subscribe to desired ClientEvent types from JourneyMap
        this.jmAPI.subscribe(getModId(), EnumSet.of(DEATH_WAYPOINT, MAPPING_STARTED, MAPPING_STOPPED, REGISTRY));

        ExampleMod.LOGGER.info("Initialized " + getClass().getName());
    }

    /**
     * Used by JourneyMap to associate a modId with this plugin.
     */
    @Override
    public String getModId()
    {
        return ExampleMod.MODID;
    }

    /**
     * Called by JourneyMap on the main Minecraft thread when a {@link journeymap.client.api.event.ClientEvent} occurs.
     * Be careful to minimize the time spent in this method so you don't lag the game.
     * <p>
     * You must call {@link IClientAPI#subscribe(String, EnumSet)} at some point to subscribe to these events, otherwise this
     * method will never be called.
     * <p>
     * If the event type is {@link journeymap.client.api.event.ClientEvent.Type#DISPLAY_UPDATE},
     * this is a signal to {@link journeymap.client.api.IClientAPI#show(journeymap.client.api.display.Displayable)}
     * all relevant Displayables for the {@link journeymap.client.api.event.ClientEvent#dimension} indicated.
     * (Note: ModWaypoints with persisted==true will already be shown.)
     *
     * @param event the event
     */
    @Override
    public void onEvent(ClientEvent event)
    {
        try
        {
            switch (event.type)
            {
                case MAPPING_STARTED:
                    onMappingStarted(event);
                    break;

                case MAPPING_STOPPED:
                    onMappingStopped(event);
                    break;

                case DEATH_WAYPOINT:
                    onDeathpoint((DeathWaypointEvent) event);
                    break;
                case REGISTRY:
                    RegistryEvent registryEvent = (RegistryEvent) event;
                    switch(registryEvent.getRegistryType()) {
                        case OPTIONS:
                            this.clientProperties = new ClientProperties();
                            break;
                        case INFO_SLOT:
                            ((RegistryEvent.InfoSlotRegistryEvent)registryEvent)
                                    .register("Current Millis", 1000, ()-> "Millis: " + System.currentTimeMillis());
                            ((RegistryEvent.InfoSlotRegistryEvent)registryEvent)
                                    .register("Current Ticks", 10, ExampleJourneymapPlugin::getTicks);
                            break;
                    }
                    break;
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

    private static String getTicks() {
        return "Ticks: " + Minecraft.getInstance().gui.getGuiTicks();
    }


    /**
     * When mapping has started, generate a bunch of random overlays.
     *
     * @param event client event
     */
    void onMappingStarted(ClientEvent event)
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
        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Waypoint))
        {
            BlockPos pos = Minecraft.getInstance().player.getSleepingPos().orElse(new BlockPos(0, 0, 0));
            SampleWaypointFactory.createBedWaypoint(jmAPI, pos, event.dimension);
        }

        // Create some random complex polygon overlays
        if (jmAPI.playerAccepts(ExampleMod.MODID, DisplayType.Polygon))
        {
            BlockPos pos = Minecraft.getInstance().player.blockPosition();
            SampleComplexPolygonOverlayFactory.create(jmAPI, pos, event.dimension, 256);
        }

        // Slime chunk Polygon Overlays are created by the ForgeEventListener
        // as chunks load, so no need to do anything here.
    }

    /**
     * When mapping has stopped, remove all overlays
     *
     * @param event client event
     */
    void onMappingStopped(ClientEvent event)
    {
        // Clear everything
        jmAPI.removeAll(ExampleMod.MODID);
    }

    /**
     * Do something when JourneyMap is about to create a Deathpoint.
     */
    void onDeathpoint(DeathWaypointEvent event)
    {
        // Could cancel the event, which would prevent the Deathpoint from actually being created.
        // For now, don't do anything.
    }

}
