package journeymap.api.v2.server.event;

import journeymap.api.v2.common.event.impl.CommonEvent;

/**
 * Fired on the server when it is time for server plugins to register their
 * configuration options.
 * <p>
 * Server plugins should create their {@link journeymap.api.v2.common.option.Option}
 * instances in a handler for this event (subscribed via
 * {@link journeymap.api.v2.common.event.ServerEventRegistry#OPTIONS_REGISTRY_EVENT}).
 * Options self-register into {@link journeymap.api.v2.common.option.OptionsRegistry}
 * when constructed, and JourneyMap reads that registry to build the server addon
 * options shown in the Server Admin options screen.
 * <p>
 * This event is not cancellable.
 */
public class ServerOptionsRegistryEvent extends CommonEvent
{
    public ServerOptionsRegistryEvent()
    {
        super(false, Side.Server);
    }
}
