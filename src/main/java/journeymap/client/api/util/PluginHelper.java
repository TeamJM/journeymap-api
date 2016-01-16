/*
 * JourneyMap API (http://journeymap.info)
 * http://bitbucket.org/TeamJM/journeymap-api
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

package journeymap.client.api.util;

import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Used by JourneyMap to load and initialize plugins.  A plugin class must be annotated with
 * the {@link ClientPlugin} annotation and also implement the {@link IClientPlugin} interface.
 */
public enum PluginHelper
{
    INSTANCE;

    public final static Logger LOGGER = LogManager.getLogger("journeymap");
    public final static String PLUGIN_ANNOTATION_NAME = ClientPlugin.class.getCanonicalName();
    public final static String PLUGIN_INTERFACE_NAME = IClientPlugin.class.getSimpleName();

    protected List<IClientPlugin> plugins;
    protected boolean initialized;

    /**
     * Called by JourneyMap during preInit phase of mod loading.
     * <p/>
     * Mods which are testing integration can also call this in a dev environment
     * and pass in a stub implementation, but must never do so in production code.
     *
     * @param event preInit
     */
    public void findPlugins(FMLPreInitializationEvent event)
    {
        if (plugins == null)
        {
            ASMDataTable asmDataTable = event.getAsmData();
            this.plugins = new ArrayList<IClientPlugin>();
            Set<ASMDataTable.ASMData> asmDataSet = asmDataTable.getAll(PLUGIN_ANNOTATION_NAME);

            for (ASMDataTable.ASMData asmData : asmDataSet)
            {
                String className = asmData.getClassName();
                try
                {
                    Class<?> pluginClass = Class.forName(className);
                    if (IClientPlugin.class.isAssignableFrom(pluginClass))
                    {
                        Class<? extends IClientPlugin> interfaceImplClass = pluginClass.asSubclass(IClientPlugin.class);
                        IClientPlugin instance = interfaceImplClass.newInstance();
                        plugins.add(instance);
                        LOGGER.info(String.format("Found @%s: %s", PLUGIN_ANNOTATION_NAME, className));
                    }
                    else
                    {
                        LOGGER.error(String.format("Found @%s: %s, but it doesn't implement %s",
                                PLUGIN_ANNOTATION_NAME, className, PLUGIN_INTERFACE_NAME));
                    }
                }
                catch (Exception e)
                {
                    LOGGER.error(String.format("Found @%s: %s, but failed to instantiate it: %s",
                            PLUGIN_ANNOTATION_NAME, className, e.getMessage()), e);
                }
            }
        }
    }

    /**
     * Called by JourneyMap during init phase of mod loading.  Can only be called once per runtime.
     * <p/>
     * Mods which are testing integration can also call this in a dev environment
     * and pass in a stub implementation, but must never do so in production code.
     *
     * @param clientAPI Client API implementation
     */
    public void initializePlugins(IClientAPI clientAPI)
    {
        synchronized (INSTANCE)
        {
            if (plugins == null)
            {
                // Exception used just to show a trace back to whoever shouldn't have called this.
                LOGGER.warn("Plugins haven't been found, can't initialize!", new IllegalStateException());
                return;
            }

            if (!initialized)
            {
                LOGGER.info(String.format("Initializing plugins with Client API: %s", clientAPI.getClass().getName()));
                Iterator<IClientPlugin> iter = plugins.iterator();
                while (iter.hasNext())
                {
                    IClientPlugin plugin = iter.next();
                    try
                    {
                        plugin.initialize(clientAPI);
                        LOGGER.info(String.format("Initialized %s: %s", PLUGIN_INTERFACE_NAME, plugin.getClass().getName()));
                    }
                    catch (Exception e)
                    {
                        LOGGER.error("Failed to initialize IClientPlugin: " + plugin.getClass().getName(), e);
                        iter.remove();
                    }
                }

                // Finalize the list
                plugins = Collections.unmodifiableList(plugins);
                initialized = true;
            }
            else
            {
                // Exception used just to show a trace back to whoever shouldn't have called this.
                LOGGER.warn("Plugins already initialized!", new IllegalStateException());
            }
        }
    }

    /**
     * Get the list of plugins found.
     *
     * @return null if {@link #findPlugins(FMLPreInitializationEvent)} hasn't been called yet
     */
    public List<IClientPlugin> getPlugins()
    {
        return plugins;
    }
}
