/*
 *
 * JourneyMap API
 * http://journeymap.info
 * http://bitbucket.org/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2015 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *   + Write your own code that uses this API source code as a dependency.
 *   + Distribute compiled classes of unmodified API source code which your code depends upon.
 *   + Fork and modify API source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified versions of the API source code or compiled artifacts of  modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API code in any way not explicitly granted by this license statement.
 *
 */

package example.client.impl;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory class to provide the Client API implementation from the JourneyMap client mod.
 * Also provides a stub implementation suitable for testing if you don't have a dev jar of JourneyMap.
 */
@Optional.Interface(iface = "journeymap.client.api.IClientAPI", modid = "journeymap")
public final class ClientAPIFactory
{
    public final static Logger LOGGER = LogManager.getLogger("example-journeymap-clientapi");
    public final static String STUB_IMPL_CLASS = StubClientAPI.class.getName();

    /**
     * Whether the JourneyMap client mod has been loaded.
     *
     * @return true if the IClientAPI v1 has been loaded.
     */
    public boolean isJourneyMapPresent()
    {
        return Loader.isModLoaded("journeymap");
    }

    /**
     * Get the ClientAPI instance from the JourneyMap client mod.
     * Logs errors if it can't be found.
     *
     * @return null if the IClientAPI can't be found or accessed.
     */
    @SuppressWarnings("unchecked")
    @Optional.Method(modid = "journeymap")
    public journeymap.client.api.IClientAPI getInstance()
    {
        if (!isJourneyMapPresent())
        {
            return null;
        }

        try
        {
            // ClientAPI is an enum singleton with the name INSTANCE.
            Class<Enum> implClass = (Class<Enum>) Class.forName("journeymap.client.api.ClientAPI");
            return (journeymap.client.api.IClientAPI) Enum.valueOf(implClass, "INSTANCE");
        }
        catch (Throwable e)
        {
            LOGGER.error(journeymap.client.api.IClientAPI.FACTORY_CLASS + " isn't usable!", e);
            return null;
        }
    }

    /**
     * Provides a stub implementation of the IClientAPI suitable for rudimentary integration work
     * without having the JourneyMap client mod jar in your development environment.
     *
     * @return a stub implementation
     */
    @SuppressWarnings("unchecked")
    public StubClientAPI getStub()
    {
        try
        {
            // Instantiate the stub the same way that the real ClientAPI is done.
            // Not because it's necessary in this case, but just to prove it works this way.
            Class<Enum> implClass = (Class<Enum>) Class.forName(STUB_IMPL_CLASS);
            return (StubClientAPI) Enum.valueOf(implClass, "INSTANCE");
        }
        catch (Throwable e)
        {
            LOGGER.error(STUB_IMPL_CLASS + " isn't usable!", e);
            return null;
        }
    }

}
