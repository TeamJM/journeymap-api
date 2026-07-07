package journeymap.api.v2.client.display;

import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.api.v2.client.util.UIState;
import journeymap.api.v2.common.util.BlockPos;

import java.awt.geom.Point2D;

/**
 * Interface for receiving user events related to an Overlay.
 */
public interface IOverlayListener
{
    /**
     * Called when the Overlay is actively displayed.
     *
     * @param mapState current UIState of the UI where the overlay is active.
     */
    default void onActivate(UIState mapState)
    {

    }

    /**
     * Called when Overlay is no longer displayed.
     *
     * @param mapState current UIState of the UI where the overlay is inactive.
     */
    default void onDeactivate(UIState mapState)
    {

    }

    /**
     * Called when the mouse moves within the bounds of the overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     */
    default void onMouseMove(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition)
    {

    }

    /**
     * Called when the mouse first leaves the bounds of the overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     */
    default void onMouseOut(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition)
    {

    }

    /**
     * Called when the mouse is clicked within the bounds of the overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     * @param button        the mouse button clicked
     * @param doubleClick   true if the mouse button was double-clicked
     * @return true if click event can bubble up to other overlays which occupy the same area.
     */
    default boolean onMouseClick(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick)
    {
        return true;
    }

    /**
     * Called when the popup menu is displayed on an overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     * @param modPopupMenu  the modMenuPopup
     */
    default void onOverlayMenuPopup(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, ModPopupMenu modPopupMenu)
    {

    }
}
