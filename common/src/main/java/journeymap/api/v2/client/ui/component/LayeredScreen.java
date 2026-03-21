package journeymap.api.v2.client.ui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class LayeredScreen extends Screen
{
    protected Minecraft minecraft;
    protected Screen backgroundScreen;

    protected LayeredScreen(Component component)
    {
        super(component);
        this.minecraft = Minecraft.getInstance();
    }

    public void display()
    {
        if (this.minecraft.screen != null)
        {
            this.backgroundScreen = this.minecraft.screen;
            this.minecraft.screen = this;
            this.added();
            super.init(minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
            minecraft.getNarrator().saySystemNow(this.getNarrationMessage());
        }
        else
        {
            this.minecraft.setScreen(this);
        }
    }

    /**
     * use the non-deprecated version instead
     *
     * @param minecraft
     * @param width
     * @param height
     */
    @Deprecated(forRemoval = true)
    public void resize(Minecraft minecraft, int width, int height)
    {
        this.resize(width, height);
    }

    public void resize(int width, int height)
    {
        super.resize(width, height);
        if (this.backgroundScreen != null)
        {
            this.backgroundScreen.resize(this.width, this.height);
        }
    }

    @Override
    @Deprecated // do not call super.extractRenderState, call super.extractPopupScreen instead
    public final void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        if (this.backgroundScreen != null)
        // render background screen.
        {
            this.backgroundScreen.extractRenderStateWithTooltipAndSubtitles(graphics, -1, -1, partialTicks);
        }
        graphics.nextStratum();
        this.renderPopupScreenBackground(graphics, mouseX, mouseY, partialTicks);
        graphics.nextStratum();
        this.renderPopupScreen(graphics, mouseX, mouseY, partialTicks);
    }

    protected void renderPopupScreen(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
    }

    protected void renderPopupScreenBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public final void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        // no use
    }

    @Override
    public void onClose()
    {
        this.popLayer();
    }

    public void popLayer()
    {
        if (this.minecraft.screen != null)
        {
            this.minecraft.screen.removed();
        }
        if (this.backgroundScreen != null)
        {
            this.minecraft.screen = this.backgroundScreen;
        }
        else
        {
            this.minecraft.setScreen(null);
        }
    }

    public Screen getBackgroundScreen()
    {
        return this.backgroundScreen;
    }
}
