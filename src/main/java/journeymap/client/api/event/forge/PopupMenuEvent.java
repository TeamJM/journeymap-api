package journeymap.client.api.event.forge;

import journeymap.client.api.display.ModPopupMenu;
import journeymap.client.api.display.Waypoint;
import journeymap.client.api.model.IFullscreen;
import journeymap.client.api.util.UIState;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.awt.geom.Point2D;

@Cancelable
public class PopupMenuEvent extends Event
{
    private final ModPopupMenu popupMenu;
    private final Layer layer;
    private final IFullscreen fullscreen;


    /**
     * This event is used for adding items to the right click menu on the fullscreen map.
     * It is fired when a user right clicks on the fullscreen map before drawing the popup menu.
     *
     * @param popupMenu  - The menu builder.
     * @param layer      - The mapping layer which fired the event.
     * @param fullscreen - The fullscreen hook.
     */
    public PopupMenuEvent(ModPopupMenu popupMenu, Layer layer, IFullscreen fullscreen)
    {
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
