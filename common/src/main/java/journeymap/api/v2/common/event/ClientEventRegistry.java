package journeymap.api.v2.common.event;

import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.event.DisplayUpdateEvent;
import journeymap.api.v2.client.event.EntityRadarUpdateEvent;
import journeymap.api.v2.client.event.EntityRegistrationEvent;
import journeymap.api.v2.client.event.MappingEvent;
import journeymap.api.v2.client.event.RegistryEvent;
import journeymap.api.v2.common.event.impl.Event;
import journeymap.api.v2.common.event.impl.EventFactory;

/**
 * Events consumers subscribe in the {@link IClientPlugin#initialize(IClientAPI)} method on your plugin.
 * Example:
 * <code>DEATH_WAYPOINT_EVENT.subscribe(MOD_ID, Consumer)</code>
 */
public class ClientEventRegistry
{

    /**
     * Indicates a change in the display characteristics of the specified UI.
     * Event will be a {@link DisplayUpdateEvent}, which can not be cancelled.
     */
    public static final Event<DisplayUpdateEvent> DISPLAY_UPDATE_EVENT = EventFactory.create(DisplayUpdateEvent.class);

    /**
     * Indicates JourneyMap has started or stopped mapping chunks in the dimension.
     * Event will be a {@link MappingEvent}.
     * Cannot be cancelled.
     */
    public static final Event<MappingEvent> MAPPING_EVENT = EventFactory.create(MappingEvent.class);

    /**
     * Register info slots.
     * {@link RegistryEvent}
     */
    public static final Event<RegistryEvent.InfoSlotRegistryEvent> INFO_SLOT_REGISTRY_EVENT = EventFactory.create(RegistryEvent.InfoSlotRegistryEvent.class);

    /**
     * Register options in the addon options screen.
     * {@link RegistryEvent}
     */
    public static final Event<RegistryEvent.OptionsRegistryEvent> OPTIONS_REGISTRY_EVENT = EventFactory.create(RegistryEvent.OptionsRegistryEvent.class);

    /**
     * This event is fired when JourneyMap updates an entity before it is displayed on the map.
     * This event is cancellable, when cancelled, it will prevent the entity from being displayed on the map.
     */
    public static final Event<EntityRadarUpdateEvent> ENTITY_RADAR_UPDATE_EVENT = EventFactory.create(EntityRadarUpdateEvent.class);

    /**
     * This event is fired very early in mod loading so that JourneyMap has a handle on possible entities to display on the map
     * This event is not cancellable.
     */
    public static final Event<EntityRegistrationEvent> ENTITY_REGISTRATION_EVENT = EventFactory.create(EntityRegistrationEvent.class);

}
