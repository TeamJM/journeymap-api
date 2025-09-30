package journeymap.api.v2.client.ui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
            this.init(minecraft, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
            minecraft.getNarrator().saySystemNow(this.getNarrationMessage());
        }
        else
        {
            this.minecraft.setScreen(this);
        }
    }

    public void resize(Minecraft minecraft, int width, int height)
    {
        super.resize(minecraft, width, height);
        if (this.backgroundScreen != null)
        {
            this.backgroundScreen.resize(this.minecraft, this.width, this.height);
        }
    }

    @Override
    @Deprecated // do not call super.render, call super.renderPopupScreen instead
    public final void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        if (this.backgroundScreen != null)
        // render background screen.
        {
            this.backgroundScreen.renderWithTooltipAndSubtitles(graphics, -1, -1, partialTicks);
        }
        graphics.nextStratum();
        this.renderPopupScreenBackground(graphics, mouseX, mouseY, partialTicks);
        graphics.nextStratum();
        this.renderPopupScreen(graphics, mouseX, mouseY, partialTicks);
    }

    protected void renderPopupScreen(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    protected void renderPopupScreenBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public final void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
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
