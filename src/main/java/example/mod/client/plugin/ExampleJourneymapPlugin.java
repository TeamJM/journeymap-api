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

package example.mod.client.plugin;

import example.mod.ExampleMod;
import example.mod.client.ClientProxy;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.event.ClientEvent;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Example plugin implementation. To prevent classloader errors if JourneyMap isn't loaded
 * (and thus the API classes aren't loaded), this class isn't referenced anywhere directly in the mod.
 * <p/>
 * The @journeymap.client.api.ClientPlugin annotation makes this plugin class discoverable to JourneyMap,
 * which will create an instance of it and then call initialize on it.
 * <p/>
 * The
 */
@ParametersAreNonnullByDefault
@journeymap.client.api.ClientPlugin
public class ExampleJourneymapPlugin implements journeymap.client.api.IClientPlugin
{
    /**
     * Called by JourneyMap during the init phase of example.mod loading.  Your implementation
     * should retain a reference to the IClientAPI passed in, since that is what your plugin
     * will use to add overlays, etc. to JourneyMap.
     *
     * @param api Client API implementation
     */
    @Override
    public void initialize(final IClientAPI api)
    {
        // Set ClientProxy.ExampleMapFacade with an implementation that uses the JourneyMap IClientAPI under the covers.
        ClientProxy.MapFacade = new ExampleMapFacade(api);
    }

    /**
     * Called by JourneyMap on the main Minecraft thread when a {@link journeymap.client.api.event.ClientEvent} occurs.
     * Be careful to minimize the time spent in this method so you don't lag the game.
     * <p/>
     * If the event type is {@link journeymap.client.api.event.ClientEvent.Type#DISPLAY_STARTED},
     * this is a signal to {@link journeymap.client.api.IClientAPI#show(journeymap.client.api.display.Displayable)}
     * all relevant Displayables for the {@link journeymap.client.api.event.ClientEvent#dimension} indicated.
     * (Note: ModWaypoints with persisted==true will already be shown.)
     *
     * @param event the event
     */
    @Override
    public void onEvent(journeymap.client.api.event.ClientEvent event)
    {
        try
        {
            ExampleMod.LOGGER.info("ClientEvent: " + event.type);

            if (ClientProxy.MapFacade != null && event.type == ClientEvent.Type.DISPLAY_STARTED)
            {
                ClientProxy.MapFacade.refreshMap(event.dimension);
            }
            else
            {
                event.cancel();
            }
        }
        catch (Throwable t)
        {
            ExampleMod.LOGGER.error(t.getMessage(), t);
        }
    }

}
