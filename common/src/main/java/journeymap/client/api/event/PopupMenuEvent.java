package journeymap.client.api.event;

import journeymap.client.api.display.ModPopupMenu;
import journeymap.client.api.model.IFullscreen;
import journeymap.client.api.util.UIState;
import journeymap.common.api.event.impl.ClientEvent;
import journeymap.common.api.waypoint.Waypoint;
import net.minecraft.core.BlockPos;

import java.awt.geom.Point2D;

/**
 * This event is used for adding items to the right click menu on the fullscreen map.
 * It is fired when a user right-clicks on the fullscreen map before drawing the popup menu.
 * This event is not cancellable, when cancelled no popup will appear on right click.
 */
public class PopupMenuEvent extends ClientEvent
{
    private final ModPopupMenu popupMenu;
    private final Layer layer;
    private final IFullscreen fullscreen;


    /**
     * This event is used for adding items to the right click menu on the fullscreen map.
     * It is fired when a user right-clicks on the fullscreen map before drawing the popup menu.
     * This event is cancellable.
     *
     * @param popupMenu  - The menu builder.
     * @param layer      - The mapping layer which fired the event.
     * @param fullscreen - The fullscreen hook.
     */
    public PopupMenuEvent(ModPopupMenu popupMenu, Layer layer, IFullscreen fullscreen)
    {
        super(true);
        this.popupMenu = popupMenu;
        this.layer = layer;
        this.fullscreen = fullscreen;
    }

    public ModPopupMenu getPopupMenu()
    {
        return popupMenu;
    }

    public Layer getLayer()
    {
        return layer;
    }

    public IFullscreen getFullscreen()
    {
        return fullscreen;
    }

    /**
     * This event is fired when a user right clicks anywhere on the fullscreen map that is not an overlay or waypoint.
     * To target overlays, see {@link journeymap.client.api.display.IOverlayListener#onOverlayMenuPopup(UIState, Point2D.Double, BlockPos, ModPopupMenu)}
     * This event is cancellable
     */
    public static class FullscreenPopupMenuEvent extends PopupMenuEvent
    {

        public FullscreenPopupMenuEvent(ModPopupMenu popupMenu, IFullscreen fullscreen)
        {
            super(popupMenu, Layer.FULLSCREEN, fullscreen);
        }
    }

    /**
     * This event is fired when a user right-clicks on a waypoint icon.
     * This event is cancellable.
     */
    public static class WaypointPopupMenuEvent extends PopupMenuEvent
    {

        private final Waypoint waypoint;

        public WaypointPopupMenuEvent(ModPopupMenu popupMenu, IFullscreen fullscreen, Waypoint waypoint)
        {
            super(popupMenu, Layer.WAYPOINT, fullscreen);
            this.waypoint = waypoint;
        }

        public Waypoint getWaypoint()
        {
            return waypoint;
        }
    }

    public enum Layer
    {
        WAYPOINT,
        FULLSCREEN
    }
}
