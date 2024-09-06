package journeymap.api.v2.common.event;

import journeymap.api.v2.client.display.IOverlayListener;
import journeymap.api.v2.client.event.FullscreenDisplayEvent;
import journeymap.api.v2.client.event.FullscreenMapEvent;
import journeymap.api.v2.client.event.PopupMenuEvent;
import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.common.event.impl.Event;
import net.minecraft.core.BlockPos;

import java.awt.geom.Point2D;

public class FullscreenEventRegistry
{
    /**
     * Indicates that the fullscreen map is going to have a mouse click.
     * This is a staged event. Pre can be canceled, post cannot be canceled.
     * {@link FullscreenMapEvent.ClickEvent}, which can be cancelled.
     */
    public static final Event<FullscreenMapEvent.ClickEvent> FULLSCREEN_MAP_CLICK_EVENT = ClientEventRegistry.FULLSCREEN_MAP_CLICK_EVENT;

    /**
     * Indicates the start of the mouse dragging.
     * This is a staged event. Pre can be canceled, post cannot be canceled.
     * {@link FullscreenMapEvent.MouseDraggedEvent}, which can be cancelled.
     */
    public static final Event<FullscreenMapEvent.MouseDraggedEvent> FULLSCREEN_MAP_DRAG_EVENT = ClientEventRegistry.FULLSCREEN_MAP_DRAG_EVENT;

    /**
     * Indicates moving of the mouse, gets block info where the cursor is pointing.
     * {@link FullscreenMapEvent.MouseMoveEvent}, which can not be cancelled.
     */
    public static final Event<FullscreenMapEvent.MouseMoveEvent> FULLSCREEN_MAP_MOVE_EVENT = ClientEventRegistry.FULLSCREEN_MAP_MOVE_EVENT;

    /**
     * This event is fired when a user right clicks anywhere on the fullscreen map that is not an overlay or waypoint.
     * To target overlays, see {@link IOverlayListener#onOverlayMenuPopup(UIState, Point2D.Double, BlockPos, ModPopupMenu)}
     * This event is cancellable
     */
    public static final Event<PopupMenuEvent.FullscreenPopupMenuEvent> FULLSCREEN_POPUP_MENU_EVENT = ClientEventRegistry.FULLSCREEN_POPUP_MENU_EVENT;

    /**
     * This event is fired when a user right-clicks on a waypoint icon.
     * This event is cancellable.
     */
    public static final Event<PopupMenuEvent.WaypointPopupMenuEvent> WAYPOINT_POPUP_MENU_EVENT = ClientEventRegistry.WAYPOINT_POPUP_MENU_EVENT;

    /**
     * Used to create custom toolbars on the fullscreen map.
     */
    public static final Event<FullscreenDisplayEvent.CustomToolbarEvent> CUSTOM_TOOLBAR_UPDATE_EVENT = ClientEventRegistry.CUSTOM_TOOLBAR_UPDATE_EVENT;

    /**
     * Used for adding buttons to the maptype theme button list.
     * We currently do not have any hooks to add map types, but this event exists for
     * those that want to add maptypes through your own means.
     * This event is not cancellable
     *
     * @deprecated since this is a special event that requires special modification to use,
     * it is suggested to not use it without first discussing with TeamJM developers.
     */
    @Deprecated()
    public static final Event<FullscreenDisplayEvent.MapTypeButtonDisplayEvent> MAP_TYPE_BUTTON_DISPLAY_EVENT = ClientEventRegistry.MAP_TYPE_BUTTON_DISPLAY_EVENT;

    /**
     * This event is used for adding buttons to the right panel on the fullscreen map.
     * This event is not cancellable.
     */
    public static final Event<FullscreenDisplayEvent.AddonButtonDisplayEvent> ADDON_BUTTON_DISPLAY_EVENT = ClientEventRegistry.ADDON_BUTTON_DISPLAY_EVENT;


}
