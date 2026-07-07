package journeymap.api.v2.client.event;

import journeymap.api.v2.client.fullscreen.IFullscreen;

/*
 * PORT NOTE (1.16.5 -> 1.12.2): the PoseStack (matrix-stack) render-context parameter/field has no
 * equivalent in 1.12.2's fixed-function GuiScreen rendering (no matrix-stack object is threaded through
 * draw calls; transforms are applied directly via GlStateManager against the current GL state). Dropped
 * rather than stubbed with a meaningless placeholder type. See task-4.2-report.md DECISIONS.
 */

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

    public FullscreenRenderEvent(IFullscreen fullscreen, int mouseX, int mouseY, float partialTicks)
    {
        super(false, fullscreen.getUiState().dimension);
        this.fullscreen = fullscreen;
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
}
