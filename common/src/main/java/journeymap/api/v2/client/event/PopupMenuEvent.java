package journeymap.api.v2.client.event;

import journeymap.api.v2.client.display.IOverlayListener;
import journeymap.api.v2.client.fullscreen.IFullscreen;
import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.common.waypoint.Waypoint;
import net.minecraft.util.math.BlockPos;

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
     * To target overlays, see {@link IOverlayListener#onOverlayMenuPopup(UIState, Point2D.Double, BlockPos, ModPopupMenu)}
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
