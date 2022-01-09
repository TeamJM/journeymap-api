package journeymap.client.api.display;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;

public interface ModPopupMenu
{
    /**
     * Adds a button with an action when clicked to the popup menu.
     *
     * @param label  - The label, can be the display string or i18n key.
     * @param action - The action - this is an anonymous function, similar to Button.onPress
     */
    ModPopupMenu addMenuItem(String label, Action action);

    /**
     * Helper method to display a screen on top of the fullscreen map when a user clicks the option.
     *
     * @param label  - The label, can be the display string or i18n key.
     * @param screen - The screen.
     */
    ModPopupMenu addMenuItemScreen(String label, Screen screen);

    /**
     * Creates a sublist popup menu.
     *
     * @param label - The label, can be the display string or i18n key.
     * @return - the menu for the sublist.
     */
    ModPopupMenu createSubItemList(String label);

    /**
     * The action when the menu button is clicked.
     */
    interface Action
    {
        void doAction(BlockPos blockPos);
    }
}
