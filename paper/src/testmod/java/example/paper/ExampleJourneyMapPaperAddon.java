/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */
package example.paper;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Minimal example Paper entry point for a JourneyMap addon. This class is a
 * pure Bukkit shim: it is NOT a JourneyMap plugin and does NOT carry
 * {@code @JourneyMapPlugin}.
 *
 * The canonical annotated {@code IServerPlugin} example is
 * {@code example.mod.server.plugin.ExampleServerPlugin} in {@code common/src/testmod}.
 * JM's Paper plugin scanner discovers annotated classes by classpath annotation
 * scan, not by {@code instanceof JavaPlugin}, so the addon's Bukkit entry point
 * and its {@code IServerPlugin} should be separate classes. Combining them in
 * one class breaks at JM-side instantiation because Bukkit's {@code JavaPlugin}
 * no-arg constructor throws when invoked outside a {@code PluginClassLoader}.
 *
 * When run via {@code :paper:runTestmodServer} (no JM Paper plugin present),
 * only onEnable fires, verifying the API classes load on a real Paper classpath.
 * Full addon discovery requires running this plugin against a Paper server that
 * has JM Paper installed alongside a real annotated {@code IServerPlugin} on
 * the same classpath.
 */
public final class ExampleJourneyMapPaperAddon extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getLogger().info("Example JM Paper shim loaded.");
    }
}
