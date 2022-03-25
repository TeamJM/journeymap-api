package journeymap.client.api.event.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class FabricEvents
{
    public static final Event<FullscreenPopupMenu> FULLSCREEN_POPUP_MENU_EVENT = EventFactory.createArrayBacked(FullscreenPopupMenu.class, callbacks -> event -> {
        for (FullscreenPopupMenu callback : callbacks)
        {
            PopupMenuEvent.FullscreenPopupMenuEvent popupMenuEvent = callback.onDisplay(event);
            if (popupMenuEvent.isCanceled())
            {
                return event;
            }
        }
        return event;
    });

    public static final Event<WaypointPopupMenu> WAYPOINT_POPUP_MENU_EVENT = EventFactory.createArrayBacked(WaypointPopupMenu.class, callbacks -> event -> {
        for (WaypointPopupMenu callback : callbacks)
        {
            PopupMenuEvent.WaypointPopupMenuEvent popupMenuEvent = callback.onDisplay(event);
            if (popupMenuEvent.isCanceled())
            {
                return event;
            }
        }
        return event;
    });

    public static final Event<CustomToolbarUpdate> CUSTOM_TOOLBAR_UPDATE_EVENT = EventFactory.createArrayBacked(CustomToolbarUpdate.class, callbacks -> event -> {
        for (CustomToolbarUpdate callback : callbacks)
        {
            callback.onUpdate(event);
        }
        return event;
    });


    public static final Event<MapTypeButtonDisplay> MAP_TYPE_BUTTON_DISPLAY_EVENT = EventFactory.createArrayBacked(MapTypeButtonDisplay.class, callbacks -> event -> {
        for (MapTypeButtonDisplay callback : callbacks)
        {
            callback.onDisplay(event);
        }
        return event;
    });


    public static final Event<AddonButtonDisplay> ADDON_BUTTON_DISPLAY_EVENT = EventFactory.createArrayBacked(AddonButtonDisplay.class, callbacks -> event -> {
        for (AddonButtonDisplay callback : callbacks)
        {
            callback.onDisplay(event);
        }
        return event;
    });


    public static final Event<EntityRadarUpdate> ENTITY_RADAR_UPDATE_EVENT = EventFactory.createArrayBacked(EntityRadarUpdate.class, callbacks -> event -> {
        for (EntityRadarUpdate callback : callbacks)
        {
            EntityRadarUpdateEvent entityRadarUpdateEvent = callback.onUpdate(event);
            if (entityRadarUpdateEvent.isCanceled())
            {
                return entityRadarUpdateEvent;
            }
        }
        return event;
    });

    @FunctionalInterface
    public interface WaypointPopupMenu
    {
        PopupMenuEvent.WaypointPopupMenuEvent onDisplay(PopupMenuEvent.WaypointPopupMenuEvent event);
    }

    @FunctionalInterface
    public interface FullscreenPopupMenu
    {
        PopupMenuEvent.FullscreenPopupMenuEvent onDisplay(PopupMenuEvent.FullscreenPopupMenuEvent event);
    }

    @FunctionalInterface
    public interface CustomToolbarUpdate
    {
        FullscreenDisplayEvent.CustomToolbarEvent onUpdate(FullscreenDisplayEvent.CustomToolbarEvent event);
    }

    @FunctionalInterface
    public interface MapTypeButtonDisplay
    {
        FullscreenDisplayEvent.MapTypeButtonDisplayEvent onDisplay(FullscreenDisplayEvent.MapTypeButtonDisplayEvent event);
    }

    @FunctionalInterface
    public interface AddonButtonDisplay
    {
        FullscreenDisplayEvent.AddonButtonDisplayEvent onDisplay(FullscreenDisplayEvent.AddonButtonDisplayEvent event);
    }

    @FunctionalInterface
    public interface EntityRadarUpdate
    {
        EntityRadarUpdateEvent onUpdate(EntityRadarUpdateEvent event);
    }


}
