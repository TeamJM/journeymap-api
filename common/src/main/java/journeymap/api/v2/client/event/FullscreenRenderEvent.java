package journeymap.api.v2.client.event;

import journeymap.api.v2.client.fullscreen.IFullscreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/**
 * This event fired after map tiles and all entities, polygons, waypoints and before the buttons.
 * Use it to do any custom rendering on the map.
 * <p>
 * This event cannot be canceled.
 */
public class FullscreenRenderEvent extends ClientEvent
{
    private final IFullscreen fullscreen;
    private final int mouseX;
    private final int mouseY;
    private final float partialTicks;
    private final GuiGraphicsExtractor graphics;

    public FullscreenRenderEvent(IFullscreen fullscreen, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        super(false, fullscreen.getUiState().dimension);
        this.fullscreen = fullscreen;
        this.graphics = graphics;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTicks = partialTicks;
    }

    public IFullscreen getFullscreen()
    {
        return fullscreen;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    public float getPartialTicks()
    {
        return partialTicks;
    }

    public GuiGraphicsExtractor getGraphics()
    {
        return graphics;
    }
}
