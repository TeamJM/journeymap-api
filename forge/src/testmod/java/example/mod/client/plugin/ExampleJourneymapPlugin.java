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
import journeymap.client.api.JourneyMapPlugin;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.ParametersAreNonnullByDefault;

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
@JourneyMapPlugin(apiVersion = "2.0.0")
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

}
