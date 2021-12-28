package journeymap.client.api.event.forge;

import journeymap.client.api.display.ModPopupMenu;
import net.minecraftforge.eventbus.api.Event;

public class PopupMenuEvent extends Event
{
    private ModPopupMenu popupMenu;
    private Layer layer;

    public PopupMenuEvent(ModPopupMenu popupMenu, Layer layer)
    {
        this.popupMenu = popupMenu;
        this.layer = layer;
    }

    public ModPopupMenu getPopupMenu()
    {
        return popupMenu;
    }

    public Layer getLayer()
    {
        return layer;
    }

    public enum Layer
    {
        WAYPOINT,
        INFO
    }
}
