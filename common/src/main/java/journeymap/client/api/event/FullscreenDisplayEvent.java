package journeymap.client.api.event;

import journeymap.client.api.display.CustomToolBarBuilder;
import journeymap.client.api.display.ThemeButtonDisplay;
import journeymap.client.api.model.IFullscreen;
import journeymap.common.api.event.impl.ClientEvent;

/**
 * This event is used for adding buttons to existing tool-bars or creating your own toolbars on the fullscreen map.
 * This is event is not cancellable.
 */
public class FullscreenDisplayEvent extends ClientEvent
{
    private final IFullscreen fullscreen;
    private final ThemeButtonDisplay themeButtonDisplay;

    private FullscreenDisplayEvent(IFullscreen fullscreen, ThemeButtonDisplay themeButtonDisplay)
    {
        super(false);
        this.fullscreen = fullscreen;
        this.themeButtonDisplay = themeButtonDisplay;
    }

    public IFullscreen getFullscreen()
    {
        return fullscreen;
    }

    public ThemeButtonDisplay getThemeButtonDisplay()
    {
        return themeButtonDisplay;
    }

    /**
     * This event is used for adding buttons to the right panel on the fullscreen map.
     * This event is not cancellable
     */
    public static class AddonButtonDisplayEvent extends FullscreenDisplayEvent
    {

        public AddonButtonDisplayEvent(IFullscreen fullscreen, ThemeButtonDisplay themeButtonDisplay)
        {
            super(fullscreen, themeButtonDisplay);
        }
    }

    /**
     * Used for adding buttons to the maptype theme button list.
     * We currently do not have any hooks to add map types, but this event exists for
     * those that want to add maptypes through your own means.
     * This event is not cancellable
     *
     * @deprecated since this is a special event that requires special modification uses, it is suggested to not use it.
     */
    @Deprecated
    public static class MapTypeButtonDisplayEvent extends FullscreenDisplayEvent
    {

        public MapTypeButtonDisplayEvent(IFullscreen fullscreen, ThemeButtonDisplay themeButtonDisplay)
        {
            super(fullscreen, themeButtonDisplay);
        }
    }

    /**
     * Used to create custom toolbars on the fullscreen map.
     *
     * @deprecated this event should be a rare usage event, most mods should use {@link AddonButtonDisplayEvent}.
     * Overuse of this event can cause toolbars to display over other mod's toolbars.
     * USE SPARINGLY!
     * This event is not cancellable
     */
    @Deprecated
    public static class CustomToolbarEvent extends ClientEvent
    {

        private final CustomToolBarBuilder customToolBarBuilder;
        private final IFullscreen fullscreen;

        public CustomToolbarEvent(IFullscreen fullscreen, CustomToolBarBuilder customToolBarBuilder)
        {
            super(false);
            this.fullscreen = fullscreen;
            this.customToolBarBuilder = customToolBarBuilder;
        }

        public CustomToolBarBuilder getCustomToolBarBuilder()
        {
            return customToolBarBuilder;
        }

        public IFullscreen getFullscreen()
        {
            return fullscreen;
        }
    }
}
