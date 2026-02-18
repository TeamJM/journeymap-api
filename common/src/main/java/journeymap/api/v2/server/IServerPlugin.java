package journeymap.api.v2.server;

import journeymap.api.v2.common.IJourneyMapPlugin;
import journeymap.api.v2.common.JourneyMapPlugin;

/**
 * Interface used by JourneyMap to initialize server plugins and provide the Server API.
 * <p>
 * Implementation classes must have a no-arg constructor and also have the {@link JourneyMapPlugin} annotation.
 */
public interface IServerPlugin extends IJourneyMapPlugin<IServerAPI>
{
    /**
     * Called by JourneyMap during the init phase of mod loading.  Your implementation
     * should retain a reference to the IServerAPI passed in, since that is what your plugin
     * will use to add overlays, etc. to JourneyMap.
     * <p>
     * This is also a good time to subscribe to any desired Events.
     *
     * @param jmServerApi Server API implementation
     */
    @Override
    void initialize(final IServerAPI jmServerApi);
}
