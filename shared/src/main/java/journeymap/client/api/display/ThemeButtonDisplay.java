package journeymap.client.api.display;

import org.jetbrains.annotations.NotNull;

public interface ThemeButtonDisplay
{
    /**
     * Creates and adds a theme toggle button with a separate on/off label to the toolbar.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull String iconName, boolean toggled, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme button with a separate on/off label to the toolbar.
     *
     * @param labelOn  - The on label, can be the display string or i18n key.
     * @param labelOff - The off label, can be the display string or i18n key.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull String iconName, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme toggle button with a separate on/off label to the toolbar.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(@NotNull String label, @NotNull String iconName, boolean toggled, @NotNull IThemeButton.Action onPress);

    /**
     * Creates and adds a theme button with a separate on/off label to the toolbar.
     *
     * @param label    - The label, can be the display string or i18n key.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(@NotNull String label, @NotNull String iconName, @NotNull IThemeButton.Action onPress);

}
