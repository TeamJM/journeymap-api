package journeymap.api.v2.client.fullscreen;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public interface ThemeButtonDisplay
{
    /**
     * Creates and adds a theme toggle button with a separate on/off label to the toolbar.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param icon - The icon name. Icon Identifier
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull Identifier icon, boolean toggled, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme button with a separate on/off label to the toolbar.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param icon - The icon name. Icon Identifier
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull Identifier icon, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme toggle button with a separate on/off label to the toolbar.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param icon - The icon name. Icon Identifier
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(@NotNull String label, @NotNull Identifier icon, boolean toggled, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme button with a separate on/off label to the toolbar.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param icon - The icon name. Icon Identifier
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(@NotNull String label, @NotNull Identifier icon, @NotNull IThemeButton.Action onPress);

}
