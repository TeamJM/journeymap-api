package journeymap.api.v2.common.event;

import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.display.IOverlayListener;
import journeymap.api.v2.client.event.DeathWaypointEvent;
import journeymap.api.v2.client.event.DisplayUpdateEvent;
import journeymap.api.v2.client.event.EntityRadarUpdateEvent;
import journeymap.api.v2.client.event.EntityRegistrationEvent;
import journeymap.api.v2.client.event.FullscreenDisplayEvent;
import journeymap.api.v2.client.event.FullscreenMapEvent;
import journeymap.api.v2.client.event.MappingEvent;
import journeymap.api.v2.client.event.PopupMenuEvent;
import journeymap.api.v2.client.event.RegistryEvent;
import journeymap.api.v2.client.event.WaypointEvent;
import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.common.event.impl.Event;
import journeymap.api.v2.common.event.impl.EventFactory;
import net.minecraft.core.BlockPos;

import java.awt.geom.Point2D;

/**
 * Events consumers subscribe in the {@link IClientPlugin#initialize(IClientAPI)} method on your plugin.
 * Example:
 * <code>DEATH_WAYPOINT_EVENT.subscribe(MOD_ID, Consumer)</code>
 */
public class ClientEventRegistry
{

    /**
     * Indicates a Death Waypoint is about to be created for the player.
     * Event will be a {@link DeathWaypointEvent}, which can be cancelled.
     * Can be cancelled, which will prevent the waypoint creation.
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
     * Cannot be cancelled.
     */
    public static final Event<MappingEvent> MAPPING_EVENT = EventFactory.create(MappingEvent.class);

    /**
     * Indicates that the fullscreen map is going to have a mouse click.
     * This is a staged event. Pre can be canceled, post cannot be canceled.
     * {@link FullscreenMapEvent.ClickEvent}, which can be cancelled.
     */
    public static final Event<FullscreenMapEvent.ClickEvent> FULLSCREEN_MAP_CLICK_EVENT = EventFactory.create(FullscreenMapEvent.ClickEvent.class);

    /**
     * Indicates the start of the mouse dragging.
     * This is a staged event. Pre can be canceled, post cannot be canceled.
     * {@link FullscreenMapEvent.MouseDraggedEvent}, which can be cancelled.
     */
    public static final Event<FullscreenMapEvent.MouseDraggedEvent> FULLSCREEN_MAP_DRAG_EVENT = EventFactory.create(FullscreenMapEvent.MouseDraggedEvent.class);

    /**
     * Indicates moving of the mouse, gets block info where the cursor is pointing.
     * {@link FullscreenMapEvent.MouseMoveEvent}, which can not be cancelled.
     */
    public static final Event<FullscreenMapEvent.MouseMoveEvent> FULLSCREEN_MAP_MOVE_EVENT = EventFactory.create(FullscreenMapEvent.MouseMoveEvent.class);
    /**
     * Register info slots.
     * {@link RegistryEvent}
     */
    public static final Event<RegistryEvent.InfoSlotRegistryEvent> INFO_SLOT_REGISTRY_EVENT_EVENT = EventFactory.create(RegistryEvent.InfoSlotRegistryEvent.class);

    /**
     * Register options in the addon options screen.
     * {@link RegistryEvent}
     */
    public static final Event<RegistryEvent.OptionsRegistryEvent> OPTIONS_REGISTRY_EVENT_EVENT = EventFactory.create(RegistryEvent.OptionsRegistryEvent.class);

    /**
     * This event handles all the CRUD operations of a waypoints.
     * This event is not cancellable.
     */
    public static final Event<WaypointEvent> WAYPOINT_EVENT = EventFactory.create(WaypointEvent.class);

    /**
     * This event is fired when a user right clicks anywhere on the fullscreen map that is not an overlay or waypoint.
     * To target overlays, see {@link IOverlayListener#onOverlayMenuPopup(UIState, Point2D.Double, BlockPos, ModPopupMenu)}
     * This event is cancellable
     */
    public static final Event<PopupMenuEvent.FullscreenPopupMenuEvent> FULLSCREEN_POPUP_MENU_EVENT = EventFactory.create(PopupMenuEvent.FullscreenPopupMenuEvent.class);

    /**
     * This event is fired when a user right-clicks on a waypoint icon.
     * This event is cancellable.
     */
    public static final Event<PopupMenuEvent.WaypointPopupMenuEvent> WAYPOINT_POPUP_MENU_EVENT = EventFactory.create(PopupMenuEvent.WaypointPopupMenuEvent.class);

    /**
     * Used to create custom toolbars on the fullscreen map.
     *
     * @deprecated this event should be a rare usage event, most mods should use {@link FullscreenDisplayEvent.AddonButtonDisplayEvent}.
     * Overuse of this event can cause toolbars to display over other mod's toolbars.
     * USE SPARINGLY!
     * This event is not cancellable
     */
    @Deprecated
    public static final Event<FullscreenDisplayEvent.CustomToolbarEvent> CUSTOM_TOOLBAR_UPDATE_EVENT = EventFactory.create(FullscreenDisplayEvent.CustomToolbarEvent.class);

    /**
     * Used for adding buttons to the maptype theme button list.
     * We currently do not have any hooks to add map types, but this event exists for
     * those that want to add maptypes through your own means.
     * This event is not cancellable
     *
     * @deprecated since this is a special event that requires special modification to use,
     * it is suggested to not use it without first discussing with TeamJM developers.
     */
    @Deprecated
    public static final Event<FullscreenDisplayEvent.MapTypeButtonDisplayEvent> MAP_TYPE_BUTTON_DISPLAY_EVENT = EventFactory.create(FullscreenDisplayEvent.MapTypeButtonDisplayEvent.class);

    /**
     * This event is used for adding buttons to the right panel on the fullscreen map.
     * This event is not cancellable.
     */
    public static final Event<FullscreenDisplayEvent.AddonButtonDisplayEvent> ADDON_BUTTON_DISPLAY_EVENT = EventFactory.create(FullscreenDisplayEvent.AddonButtonDisplayEvent.class);

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
