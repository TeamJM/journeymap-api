package journeymap.api.v2.client.ui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

/*
 * PORT NOTE (1.16.5 -> 1.12.2): 1.12.2's GuiScreen predates the PoseStack-based render pipeline and the
 * narrator subsystem, so this class is adapted to the fixed-function GuiScreen lifecycle
 * (drawScreen/initGui/setWorldAndResolution/onGuiClosed) rather than a line-for-line rename. See
 * task-4.2-report.md DECISIONS for details.
 */
public abstract class LayeredScreen extends GuiScreen
{
    protected Minecraft minecraft;
    protected GuiScreen backgroundScreen;

    // Guards popLayer() against re-entry; see the comment in popLayer() for why this is required.
    private boolean closing;

    /**
     * The {@code component} parameter is unused on 1.12.2 - GuiScreen has no title/narration concept
     * here - and is kept only for source parity with the 1.16.5 API.
     */
    protected LayeredScreen(ITextComponent component)
    {
        this.minecraft = Minecraft.getMinecraft();
    }

    public void display()
    {
        this.closing = false;
        if (this.minecraft.currentScreen != null)
        {
            this.backgroundScreen = this.minecraft.currentScreen;
            this.minecraft.currentScreen = this;
            ScaledResolution scaledResolution = new ScaledResolution(minecraft);
            this.setWorldAndResolution(minecraft, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
        else
        {
            this.minecraft.displayGuiScreen(this);
        }
    }

    @Override
    public void onResize(Minecraft minecraft, int width, int height)
    {
        super.onResize(minecraft, width, height);
        if (this.backgroundScreen != null)
        {
            this.backgroundScreen.onResize(this.minecraft, this.width, this.height);
        }
    }

    @Override
    @Deprecated // do not call super.drawScreen, call renderPopupScreen instead
    public final void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (this.backgroundScreen != null)
        // render background screen.
        {
            this.backgroundScreen.drawScreen(-1, -1, partialTicks);
        }
        // translate z +2000
        GlStateManager.translate(0.0F, 0.0F, 2000F);

        this.renderPopupScreenBackground(mouseX, mouseY, partialTicks);
        this.renderPopupScreen(mouseX, mouseY, partialTicks);
    }

    protected void renderPopupScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void renderPopupScreenBackground(int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public final void drawDefaultBackground()
    {
        // no use
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == Keyboard.KEY_ESCAPE)
        {
            // Esc must not take the vanilla path (super.keyTyped -> displayGuiScreen(null)): after our
            // onGuiClosed() restored backgroundScreen, displayGuiScreen(null) would keep running and
            // unconditionally assign currentScreen = null, clobbering the restore and closing the
            // background screen along with this popup. Pop the layer directly and stop here instead;
            // popLayer() itself calls displayGuiScreen(null) when there is no background to restore.
            this.popLayer();
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onGuiClosed()
    {
        this.popLayer();
    }

    public void popLayer()
    {
        // Re-entry guard: 1.12.2's GuiScreen has a single lifecycle hook, onGuiClosed(), where the
        // 1.16.5 original had two distinct ones (onClose() for a self-triggered close, removed() for
        // the cleanup Minecraft.setScreen() runs on the outgoing screen). Minecraft.displayGuiScreen()
        // invokes currentScreen.onGuiClosed() before it reassigns currentScreen, and currentScreen
        // == this for as long as this layer is showing - so both the normal close path and a direct
        // popLayer() call re-enter this method via onGuiClosed(). Without this guard that re-entry is
        // unbounded (popLayer() -> onGuiClosed() -> popLayer() -> ...), causing a StackOverflowError.
        if (this.closing)
        {
            return;
        }
        this.closing = true;

        if (this.backgroundScreen != null)
        {
            this.minecraft.currentScreen = this.backgroundScreen;
        }
        else
        {
            this.minecraft.displayGuiScreen(null);
        }
    }

    public GuiScreen getBackgroundScreen()
    {
        return this.backgroundScreen;
    }
}
