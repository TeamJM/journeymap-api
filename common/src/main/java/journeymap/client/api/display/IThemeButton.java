package journeymap.client.api.display;

import net.minecraft.client.gui.components.Button;

public interface IThemeButton
{
    /**
     * Sets the toggled to value.
     *
     * @param toggled to toggle.
     */
    void setToggled(Boolean toggled);

    /**
     * Gets true if toggled, false if not.
     *
     * @return is toggled.
     */
    Boolean getToggled();

    /**
     * Gets if the button is active, if enabled and toggled
     *
     * @return if active.
     */
    boolean isActive();

    /**
     * Switch the toggle value.
     */
    void toggle();

    /**
     * Sets the on and off labels
     *
     * @param labelOn  - The on label.
     * @param labelOff - The off label.
     */
    void setLabels(String labelOn, String labelOff);

    /**
     * Gets the root button.
     *
     * @return - The root button.
     */
    Button getButton();

    /**
     * Sets the button to draw or ot.
     *
     * @param drawButton - To draw the button.
     */
    void setDrawButton(boolean drawButton);

    /**
     * Sets so it cannot be toggled off.
     * Only works for ToggleTheme buttons.
     * When toggled, it cannot be untoggled and becomes unclickable.
     *
     * @param staysOn - The stays on.
     */
    void setStaysOn(boolean staysOn);

    /**
     * Enables or disables the button.
     *
     * @param enabled - To enable.
     */
    void setEnabled(boolean enabled);

    /**
     * Sets tooltip.
     * Can be a list of strings or i18n keys.
     *
     * @param tooltip the tooltip
     */
    void setTooltip(String... tooltip);

    /**
     * The button action.
     */
    interface Action
    {
        void doAction(IThemeButton button);
    }
}
