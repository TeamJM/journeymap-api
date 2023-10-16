package journeymap.common.api.event;

import journeymap.client.api.IClientAPI;
import journeymap.client.api.display.ModPopupMenu;
import journeymap.client.api.event.DeathWaypointEvent;
import journeymap.client.api.event.DisplayUpdateEvent;
import journeymap.client.api.event.EntityRadarUpdateEvent;
import journeymap.client.api.event.FullscreenDisplayEvent;
import journeymap.client.api.event.FullscreenMapEvent;
import journeymap.client.api.event.MappingEvent;
import journeymap.client.api.event.PopupMenuEvent;
import journeymap.client.api.event.RegistryEvent;
import journeymap.client.api.event.WaypointEvent;
import journeymap.client.api.util.UIState;
import journeymap.common.api.event.impl.Event;
import journeymap.common.api.event.impl.EventFactory;
import net.minecraft.core.BlockPos;

import java.awt.geom.Point2D;

/**
 * Events consumers subscribe in the {@link journeymap.client.api.IClientPlugin#initialize(IClientAPI)} method on your plugin.
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
     * This event handles all the CRUD operations of a waypoints.
     * This event is not cancellable.
     */
    public static final Event<WaypointEvent> WAYPOINT_EVENT = EventFactory.create(WaypointEvent.class);

    /**
     * This event is fired when a user right clicks anywhere on the fullscreen map that is not an overlay or waypoint.
     * To target overlays, see {@link journeymap.client.api.display.IOverlayListener#onOverlayMenuPopup(UIState, Point2D.Double, BlockPos, ModPopupMenu)}
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
     * This event is not cancellable
     */
    public static final Event<FullscreenDisplayEvent.AddonButtonDisplayEvent> ADDON_BUTTON_DISPLAY_EVENT = EventFactory.create(FullscreenDisplayEvent.AddonButtonDisplayEvent.class);

    /**
     * This event is fired when JourneyMap updates an entity before it is displayed on the map.
     * This event is cancellable, when cancelled, it will prevent the entity from being displayed on the map.
     */
    public static final Event<EntityRadarUpdateEvent> ENTITY_RADAR_UPDATE_EVENT = EventFactory.create(EntityRadarUpdateEvent.class);

}
