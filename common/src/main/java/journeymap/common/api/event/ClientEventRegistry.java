package journeymap.common.api.event;

import journeymap.client.api.event.DeathWaypointEvent;
import journeymap.client.api.event.DisplayUpdateEvent;
import journeymap.client.api.event.EntityRadarUpdateEvent;
import journeymap.client.api.event.FullscreenDisplayEvent;
import journeymap.client.api.event.FullscreenMapEvent;
import journeymap.client.api.event.MappingEvent;
import journeymap.client.api.event.PopupMenuEvent;
import journeymap.client.api.event.RegistryEvent;
import journeymap.client.api.event.WaypointEvent;
import journeymap.common.api.event.impl.Event;
import journeymap.common.api.event.impl.EventFactory;

public class ClientEventRegistry
{

    /**
     * Indicates a Death Waypoint is about to be created for the player.
     * Event will be a {@link DeathWaypointEvent}, which can be cancelled.
     */
    public static final Event<DeathWaypointEvent> DEATH_WAYPOINT_EVENT = EventFactory.create(DeathWaypointEvent.class);

    /**
     * Indicates a change in the display characteristics of the specified UI.
     * Event will be a {@link DisplayUpdateEvent}, which can not be cancelled.
     */
    public static final Event<DisplayUpdateEvent> DISPLAY_UPDATE_EVENT = EventFactory.create(DisplayUpdateEvent.class);

    /**
     * Indicates JourneyMap has started or stopped mapping chunks in the dimension.
     * Event will be a {@link MappingEvent}.
     */
    public static final Event<MappingEvent> MAPPING_EVENT = EventFactory.create(MappingEvent.class);

    /**
     * Indicates that the fullscreen map is going to have a mouse click
     * {@link FullscreenMapEvent.ClickEvent.Pre}, which can be cancelled.
     * {@link FullscreenMapEvent.ClickEvent.Post}, which can not be cancelled.
     * <p>
     * Indicates the start of the mouse dragging.
     * {@link FullscreenMapEvent.MouseDraggedEvent.Pre}, which can be cancelled.
     * {@link FullscreenMapEvent.MouseDraggedEvent.Post}, which can not be cancelled.
     * <p>
     * Indicates moving of the mouse, gets block info where the cursor is pointing.
     * {@link FullscreenMapEvent.MouseMoveEvent}, which can not be cancelled.
     */
    public static final Event<FullscreenMapEvent> FULLSCREEN_MAP_EVENT = EventFactory.create(FullscreenMapEvent.class);

    /**
     * Indicates registry events.
     * {@link RegistryEvent}
     */
    public static final Event<RegistryEvent> REGISTRY_EVENT = EventFactory.create(RegistryEvent.class);

    /**
     * Waypoint events, includes save, add, update, delete.
     */
    public static final Event<WaypointEvent> WAYPOINT_EVENT = EventFactory.create(WaypointEvent.class);

    /**
     * {@link PopupMenuEvent.FullscreenPopupMenuEvent}
     */
    public static final Event<PopupMenuEvent.FullscreenPopupMenuEvent> FULLSCREEN_POPUP_MENU_EVENT = EventFactory.create(PopupMenuEvent.FullscreenPopupMenuEvent.class);

    /**
     * {@link PopupMenuEvent.WaypointPopupMenuEvent}
     */
    public static final Event<PopupMenuEvent.WaypointPopupMenuEvent> WAYPOINT_POPUP_MENU_EVENT = EventFactory.create(PopupMenuEvent.WaypointPopupMenuEvent.class);

    /**
     * {@link FullscreenDisplayEvent.CustomToolbarEvent }
     */
    public static final Event<FullscreenDisplayEvent.CustomToolbarEvent> CUSTOM_TOOLBAR_UPDATE_EVENT = EventFactory.create(FullscreenDisplayEvent.CustomToolbarEvent.class);

    /**
     * {@link FullscreenDisplayEvent.MapTypeButtonDisplayEvent}
     */
    public static final Event<FullscreenDisplayEvent.MapTypeButtonDisplayEvent> MAP_TYPE_BUTTON_DISPLAY_EVENT = EventFactory.create(FullscreenDisplayEvent.MapTypeButtonDisplayEvent.class);

    /**
     * {@link FullscreenDisplayEvent.AddonButtonDisplayEvent}
     */
    public static final Event<FullscreenDisplayEvent.AddonButtonDisplayEvent> ADDON_BUTTON_DISPLAY_EVENT = EventFactory.create(FullscreenDisplayEvent.AddonButtonDisplayEvent.class);

    /**
     * {@link EntityRadarUpdateEvent}
     */
    public static final Event<EntityRadarUpdateEvent> ENTITY_RADAR_UPDATE_EVENT = EventFactory.create(EntityRadarUpdateEvent.class);

}
