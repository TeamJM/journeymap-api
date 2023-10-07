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

package journeymap.client.api.util;

import com.google.common.base.Strings;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.JourneyMapPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Enum singleton used by JourneyMap to load and initialize plugins.  A plugin class must be annotated with
 * the {@link JourneyMapPlugin} annotation and also implement the {@link IClientPlugin} interface.
 */
@ParametersAreNonnullByDefault
public enum PluginHelper
{
    INSTANCE;

    public final static Logger LOGGER = LogManager.getLogger("journeymap");
//    public final static Type PLUGIN_ANNOTATION_NAME = Type.getType(ClientPlugin.class);
    public final static String PLUGIN_INTERFACE_NAME = IClientPlugin.class.getSimpleName();

    protected Map<String, IClientPlugin> plugins = null;
    protected boolean initialized;


    /**
     * Called by JourneyMap during it's preInitialization phase to find plugin classes
     * included in other mods and then instantiate them.
     * <p>
     * Mods which are testing integration can also call this in a dev environment
     * and pass in a stub implementation, but must never do so in production code.
     *
     * @return map of instantiated plugins, keyed by modId
     */
    public Map<String, IClientPlugin> preInitPlugins(List<String> pluginList)
    {
        if (plugins == null)
        {
            HashMap<String, IClientPlugin> discovered = new HashMap<String, IClientPlugin>();

            for (String className : pluginList)
            {
                try
                {
                    Class<?> pluginClass = Class.forName(className);
                    if (IClientPlugin.class.isAssignableFrom(pluginClass))
                    {
                        if (pluginClass.isAnnotationPresent(JourneyMapPlugin.class))
                        {
                            Class<? extends IClientPlugin> interfaceImplClass = pluginClass.asSubclass(IClientPlugin.class);
                            JourneyMapPlugin annotationClass = pluginClass.getDeclaredAnnotation(JourneyMapPlugin.class);
                            if (inVersionRange(annotationClass.apiVersion()))
                            {
                                IClientPlugin instance = interfaceImplClass.getDeclaredConstructor().newInstance();
                                String modId = instance.getModId();
                                if (Strings.isNullOrEmpty(modId))
                                {
                                    throw new Exception("IClientPlugin.getModId() must return a non-empty, non-null value");
                                }
                                if (discovered.containsKey(modId))
                                {
                                    Class otherPluginClass = discovered.get(modId).getClass();
                                    throw new Exception(String.format("Multiple plugins trying to use the same modId: %s and %s", interfaceImplClass, otherPluginClass));
                                }
                                discovered.put(modId, instance);
                                LOGGER.info(String.format("Found @%s: %s", JourneyMapPlugin.class.getSimpleName(), className));
                            }
                            else
                            {
                                LOGGER.error("Found @{}: {}, but there is a version incompatibility, skipping plugin version {}, required version {}",
                                        PLUGIN_INTERFACE_NAME, className, annotationClass.apiVersion(), "2.0.0");
                            }
                        }
                        else
                        {
                            LOGGER.error(String.format("Found @%s: %s, but it is not annotated with %s",
                                    PLUGIN_INTERFACE_NAME, className, JourneyMapPlugin.class.getSimpleName()));
                        }
                    }
                    else
                    {
                        LOGGER.error(String.format("Found @%s: %s, but it doesn't implement %s",
                                JourneyMapPlugin.class.getSimpleName(), className, PLUGIN_INTERFACE_NAME));
                        System.out.println(List.of(pluginClass.getInterfaces()));
                    }
                }
                catch (Exception e)
                {
                    LOGGER.error(String.format("Found @%s: %s, but failed to instantiate it: %s",
                            JourneyMapPlugin.class.getSimpleName(), className, e.getMessage()), e);
                }
            }

            if (discovered.isEmpty())
            {
                LOGGER.info("No plugins for JourneyMap API discovered.");
            }

            plugins = Collections.unmodifiableMap(discovered);
        }

        return plugins;
    }

    static boolean inVersionRange(String pluginApiVersion)
    {
        // TODO: add version parsing logic
        return true;
    }

    /**
     * Called by JourneyMap during its initialization phase.  Can only be called once per runtime.
     * <p>
     * Mods which are testing integration can also call this in a dev environment
     * and pass in a stub implementation, but must never do so in production code.
     *
     * @param clientAPI Client API implementation
     * @return list of initialized plugins, null if plugin discovery never occurred
     */
    public Map<String, IClientPlugin> initPlugins(IClientAPI clientAPI)
    {
        if (plugins == null)
        {
            // Exception used just to show a trace back to whoever shouldn't have called this.
            LOGGER.warn("Plugin discovery never occurred.", new IllegalStateException());
        }
        else if (!initialized)
        {
            LOGGER.info(String.format("Initializing plugins with Client API: %s", clientAPI.getClass().getName()));

            HashMap<String, IClientPlugin> discovered = new HashMap<String, IClientPlugin>(plugins);
            Iterator<IClientPlugin> iter = discovered.values().iterator();
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
            plugins = Collections.unmodifiableMap(discovered);
            initialized = true;
        }
        else
        {
            // Exception used just to show a trace back to whoever shouldn't have called this.
            LOGGER.warn("Plugins already initialized!", new IllegalStateException());
        }

        return plugins;
    }

    /**
     * Get the map of plugins, keyed by modId.
     *
     * @return null if {@link #preInitPlugins(List)} hasn't been called yet
     */
    public Map<String, IClientPlugin> getPlugins()
    {
        return plugins;
    }
}
