package journeymap.client.api.display;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface CustomToolBarBuilder
{
    /**
     * Creates and gets a theme toggle button with a separate on/off label.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton getThemeToggleButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull ResourceLocation icon, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and gets a theme button with a separate on/off label.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton getThemeButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull ResourceLocation icon, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and gets a theme toggle button with a separate on/off label.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton getThemeToggleButton(@NotNull String label, @NotNull ResourceLocation icon, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and gets a theme button with a separate on/off label.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton getThemeButton(@NotNull String label, @NotNull ResourceLocation icon, @NotNull IThemeButton.Action onPress);


    /**
     * Gets a new toolbar instance.
     * All the buttons must be added here.
     *
     * @param themeButtons - The theme buttons.
     * @return The toolbar instance.
     */
    IThemeToolBar getNewToolbar(IThemeButton... themeButtons);
}
