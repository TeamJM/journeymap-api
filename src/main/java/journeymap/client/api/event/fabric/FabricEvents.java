package journeymap.client.api.event.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class FabricEvents
{

    /**
     * {@link PopupMenuEvent.FullscreenPopupMenuEvent}
     */
    public static final Event<FullscreenPopupMenu> FULLSCREEN_POPUP_MENU_EVENT = EventFactory.createArrayBacked(FullscreenPopupMenu.class, callbacks -> event -> {
        for (FullscreenPopupMenu callback : callbacks)
        {
            callback.onDisplay(event);
            if (event.isCanceled())
            {
                return;
            }
        }

    });

    /**
     * {@link PopupMenuEvent.WaypointPopupMenuEvent}
     */
    public static final Event<WaypointPopupMenu> WAYPOINT_POPUP_MENU_EVENT = EventFactory.createArrayBacked(WaypointPopupMenu.class, callbacks -> event -> {
        for (WaypointPopupMenu callback : callbacks)
        {
            callback.onDisplay(event);
            if (event.isCanceled())
            {
                return;
            }
        }
    });

    /**
     * {@link FullscreenDisplayEvent.CustomToolbarEvent }
     */
    public static final Event<CustomToolbarUpdate> CUSTOM_TOOLBAR_UPDATE_EVENT = EventFactory.createArrayBacked(CustomToolbarUpdate.class, callbacks -> event -> {
        for (CustomToolbarUpdate callback : callbacks)
        {
            callback.onUpdate(event);
        }
    });

    /**
     * {@link FullscreenDisplayEvent.MapTypeButtonDisplayEvent}
     */
    public static final Event<MapTypeButtonDisplay> MAP_TYPE_BUTTON_DISPLAY_EVENT = EventFactory.createArrayBacked(MapTypeButtonDisplay.class, callbacks -> event -> {
        for (MapTypeButtonDisplay callback : callbacks)
        {
            callback.onDisplay(event);
        }
    });

    /**
     * {@link FullscreenDisplayEvent.AddonButtonDisplayEvent}
     */
    public static final Event<AddonButtonDisplay> ADDON_BUTTON_DISPLAY_EVENT = EventFactory.createArrayBacked(AddonButtonDisplay.class, callbacks -> event -> {
        for (AddonButtonDisplay callback : callbacks)
        {
            callback.onDisplay(event);
        }
    });

    /**
     * {@link EntityRadarUpdateEvent}
     */
    public static final Event<EntityRadarUpdate> ENTITY_RADAR_UPDATE_EVENT = EventFactory.createArrayBacked(EntityRadarUpdate.class, callbacks -> event -> {
        for (EntityRadarUpdate callback : callbacks)
        {
            callback.onUpdate(event);
            if (event.isCanceled())
            {
                return;
            }
        }
    });

    @FunctionalInterface
    public interface WaypointPopupMenu
    {
        void onDisplay(PopupMenuEvent.WaypointPopupMenuEvent event);
    }

    @FunctionalInterface
    public interface FullscreenPopupMenu
    {
        void onDisplay(PopupMenuEvent.FullscreenPopupMenuEvent event);
    }

    @FunctionalInterface
    public interface CustomToolbarUpdate
    {
        void onUpdate(FullscreenDisplayEvent.CustomToolbarEvent event);
    }

    @FunctionalInterface
    public interface MapTypeButtonDisplay
    {
        void onDisplay(FullscreenDisplayEvent.MapTypeButtonDisplayEvent event);
    }

    @FunctionalInterface
    public interface AddonButtonDisplay
    {
        void onDisplay(FullscreenDisplayEvent.AddonButtonDisplayEvent event);
    }

    @FunctionalInterface
    public interface EntityRadarUpdate
    {
        void onUpdate(EntityRadarUpdateEvent event);
    }


}
