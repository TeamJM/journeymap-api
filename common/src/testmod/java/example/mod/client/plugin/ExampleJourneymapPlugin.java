/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import example.mod.ExampleMod;
import example.mod.client.plugin.handler.BedWaypointHandler;
import example.mod.client.plugin.handler.SlimeChunkOverlayHandler;
import example.mod.common.plugin.CommonEventListener;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.common.JourneyMapPlugin;

/**
 * Loader-agnostic JourneyMap client plugin. Discovered by JourneyMap via the
 * {@link JourneyMapPlugin} annotation; Fabric also references this class from
 * the {@code journeymap} entrypoint in {@code fabric.mod.json}.
 */
@JourneyMapPlugin(apiVersion = "2.0.0")
public class ExampleJourneymapPlugin implements IClientPlugin
{
    private static ExampleJourneymapPlugin INSTANCE;

    private IClientAPI jmAPI;
    private ClientEventListener clientEventListener;

    public ExampleJourneymapPlugin()
    {
        INSTANCE = this;
    }

    public static ExampleJourneymapPlugin getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void initialize(final IClientAPI jmAPI)
    {
        this.jmAPI = jmAPI;

        this.clientEventListener = new ClientEventListener(jmAPI);
        CommonEventListener.register();

        // Hand the API to the loader-agnostic handlers; loader dispatchers will
        // forward sleep/chunk events into them.
        BedWaypointHandler.setApi(jmAPI);
        SlimeChunkOverlayHandler.setApi(jmAPI);

        ExampleMod.LOGGER.info("Initialized %s", getClass().getName());
    }

    @Override
    public String getModId()
    {
        return ExampleMod.MODID;
    }

    public IClientAPI getApi()
    {
        return jmAPI;
    }

    public ClientProperties getClientProperties()
    {
        return clientEventListener == null ? null : clientEventListener.getClientProperties();
    }
}
