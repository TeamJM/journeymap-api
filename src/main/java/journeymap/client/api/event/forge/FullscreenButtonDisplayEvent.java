package journeymap.client.api.event.forge;

import journeymap.client.api.display.ThemeButtonDisplay;
import journeymap.client.api.model.IFullscreen;
import net.minecraftforge.eventbus.api.Event;

public class FullscreenButtonDisplayEvent extends Event
{
    private final IFullscreen fullscreen;
    private final ThemeButtonDisplay themeButtonDisplay;

    private FullscreenButtonDisplayEvent(IFullscreen fullscreen, ThemeButtonDisplay themeButtonDisplay)
    {
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
     * Used for adding buttons to the right panel.
     */
    public static class AddonButtonDisplayEvent extends FullscreenButtonDisplayEvent
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
     */
    @Deprecated
    public static class MapTypeButtonDisplayEvent extends FullscreenButtonDisplayEvent
    {

        public MapTypeButtonDisplayEvent(IFullscreen fullscreen, ThemeButtonDisplay themeButtonDisplay)
        {
            super(fullscreen, themeButtonDisplay);
        }
    }
}
