package journeymap.api.v2.server;

import journeymap.api.v2.common.IJourneyMapPlugin;
import journeymap.api.v2.common.JourneyMapPlugin;

/**
 * Interface used by JourneyMap to initialize client plugins and provide the Client API.
 * <p>
 * Implementation classes must have a no-arg constructor and also have the {@link JourneyMapPlugin} annotation.
 */
public interface IServerPlugin extends IJourneyMapPlugin
{
    /**
     * Called by JourneyMap during the init phase of mod loading.  Your implementation
     * should retain a reference to the IServerAPI passed in, since that is what your plugin
     * will use to add overlays, etc. to JourneyMap.
     * <p>
     * This is also a good time to subscribe to any desired Events.
     *
     * @param jmServerApi Client API implementation
     */
    void initialize(final IServerAPI jmServerApi);

}
