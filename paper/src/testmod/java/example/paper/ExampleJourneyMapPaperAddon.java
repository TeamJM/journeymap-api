/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */
package example.paper;

import journeymap.api.v2.common.JourneyMapPlugin;
import journeymap.api.v2.server.IServerAPI;
import journeymap.api.v2.server.IServerPlugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Minimal example JourneyMap addon for Paper. This is both:
 *  - a Paper plugin (extends JavaPlugin) so Bukkit loads it on server start; and
 *  - a JourneyMap addon (implements IServerPlugin, annotated @JourneyMapPlugin)
 *    so the JM Paper module's plugin scanner discovers it.
 *
 * When run via :paper:runTestmodServer (no JM Paper plugin present), only
 * onEnable fires — verifying the API classes load on a real Paper classpath.
 * Full addon discovery requires running this plugin against a Paper server that
 * has JM Paper installed.
 */
@JourneyMapPlugin(apiVersion = "2.0.0")
public final class ExampleJourneyMapPaperAddon extends JavaPlugin implements IServerPlugin
{
    private static final String MOD_ID = "examplejmaddon";

    private IServerAPI jmServerApi;

    @Override
    public void onEnable()
    {
        getLogger().info("Example JM Paper addon loaded.");
    }

    @Override
    public void initialize(final IServerAPI jmServerApi)
    {
        this.jmServerApi = jmServerApi;
        getLogger().info("Example JM Paper addon initialized with IServerAPI.");
    }

    @Override
    public String getModId()
    {
        return MOD_ID;
    }
}
