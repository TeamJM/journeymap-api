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

package journeymap.api.v2.client;

import journeymap.api.v2.common.IJourneyMapPlugin;
import journeymap.api.v2.common.JourneyMapPlugin;
import journeymap.api.v2.common.event.ClientEventRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Interface used by JourneyMap to initialize client plugins and provide the Client API.
 * <p>
 * Implementation classes must have a no-arg constructor and also have the {@link JourneyMapPlugin} annotation.
 */
@ParametersAreNonnullByDefault
public interface IClientPlugin extends IJourneyMapPlugin<IClientAPI>
{
    /**
     * Called by JourneyMap during the init phase of mod loading.  Your implementation
     * should retain a reference to the IClientAPI passed in, since that is what your plugin
     * will use to add overlays, etc. to JourneyMap.
     * <p>
     * This is also a good time to call {@link ClientEventRegistry} to subscribe to any
     * desired Events.
     *
     * @param jmClientApi Client API implementation
     */
    void initialize(final IClientAPI jmClientApi);

}
