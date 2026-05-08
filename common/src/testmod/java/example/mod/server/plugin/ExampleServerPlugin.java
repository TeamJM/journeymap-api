/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.server.plugin;

import example.mod.ExampleMod;
import example.mod.common.plugin.CommonEventListener;
import journeymap.api.v2.common.JourneyMapPlugin;
import journeymap.api.v2.server.IServerAPI;
import journeymap.api.v2.server.IServerPlugin;

/**
 * Loader-agnostic JourneyMap server plugin. Discovered by JourneyMap on the
 * server side via the {@link JourneyMapPlugin} annotation.
 */
@JourneyMapPlugin(apiVersion = "2.0.0")
public class ExampleServerPlugin implements IServerPlugin
{
    private static ExampleServerPlugin INSTANCE;

    private IServerAPI jmServerApi;
    private ServerEventListener serverEventListener;

    public ExampleServerPlugin()
    {
        INSTANCE = this;
    }

    public static ExampleServerPlugin getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void initialize(final IServerAPI jmServerApi)
    {
        this.jmServerApi = jmServerApi;
        this.serverEventListener = new ServerEventListener(jmServerApi);
        CommonEventListener.register();
        ExampleMod.LOGGER.info("Initialized %s", getClass().getName());
    }

    @Override
    public String getModId()
    {
        return ExampleMod.MODID;
    }

    public IServerAPI getApi()
    {
        return jmServerApi;
    }

    public ServerEventListener getServerEventListener()
    {
        return serverEventListener;
    }
}
