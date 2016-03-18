package journeymap.client.api.display;

import journeymap.client.api.util.UIState;
import net.minecraft.util.math.BlockPos;

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
    void onActivate(UIState mapState);

    /**
     * Called when Overlay is no longer displayed.
     *
     * @param mapState current UIState of the UI where the overlay is inactive.
     */
    void onDeactivate(UIState mapState);

    /**
     * Called when the mouse moves within the bounds of the overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     */
    void onMouseMove(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition);

    /**
     * Called when the mouse first leaves the bounds of the overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     */
    void onMouseOut(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition);

    /**
     * Called when the mouse is clicked within the bounds of the overlay.
     *
     * @param mapState      current UIState of the UI where the overlay is active.
     * @param mousePosition screen coordinates of the mouse
     * @param blockPosition the block position under the mouse
     * @param button        the mouse button clicked
     * @param doubleClick   true if the mouse button was double-clicked
     *
     * @return true if click event can bubble up to other overlays which occupy the same area.
     */
    boolean onMouseClick(UIState mapState, Point2D.Double mousePosition, BlockPos blockPosition, int button, boolean doubleClick);
}
