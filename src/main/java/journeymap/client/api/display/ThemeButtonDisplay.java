package journeymap.client.api.display;

public interface ThemeButtonDisplay
{
    /**
     * Creates a theme toggle button with a separate on/off label.
     *
     * @param labelOn  - The on label.
     * @param labelOff - The off label.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(String labelOn, String labelOff, String iconName, boolean toggled, Action onPress);

    /**
     * Creates a theme button with a separate on/off label.
     *
     * @param labelOn  - The on label.
     * @param labelOff - The off label.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(String labelOn, String labelOff, String iconName, Action onPress);

    /**
     * Creates a theme toggle button with a separate on/off label.
     *
     * @param label    - The label.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param toggled  - If button starts toggled.
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeToggleButton(String label, String iconName, boolean toggled, Action onPress);

    /**
     * Creates a theme button with a separate on/off label.
     *
     * @param label    - The label.
     * @param iconName - The icon name. Icons must be located in /resources/assets/journeymap/theme/flat/icon
     * @param onPress  - The onpress action.
     * @return IThemeButton
     */
    IThemeButton addThemeButton(String label, String iconName, Action onPress);

    /**
     * The button action.
     */
    interface Action
    {
        void doAction(IThemeButton button);
    }
}
