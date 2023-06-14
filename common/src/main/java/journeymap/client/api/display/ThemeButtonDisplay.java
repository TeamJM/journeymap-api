package journeymap.client.api.display;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface ThemeButtonDisplay
{
    /**
     * Creates and adds a theme toggle button with a separate on/off label to the toolbar.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull ResourceLocation icon, boolean toggled, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme button with a separate on/off label to the toolbar.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull ResourceLocation icon, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme toggle button with a separate on/off label to the toolbar.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(@NotNull String label, @NotNull ResourceLocation icon, boolean toggled, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme button with a separate on/off label to the toolbar.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param icon - The icon name. Icon resourceLocation
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(@NotNull String label, @NotNull ResourceLocation icon, @NotNull IThemeButton.Action onPress);

}
