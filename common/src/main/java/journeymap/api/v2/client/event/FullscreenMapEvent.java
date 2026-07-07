package journeymap.api.v2.client.event;

import journeymap.api.v2.client.fullscreen.IBlockInfo;
import journeymap.api.v2.common.util.BlockPos;

import java.awt.geom.Point2D;

/**
 * Event classes for the Fullscreen map.
 */
public class FullscreenMapEvent extends ClientEvent
{
    private final BlockPos location;

    /**
     * Map clicked event, fired when a user clicks on the map.
     * Can be canceled.
     *
     * @param cancellable - The event is cancellable.
     * @param location    - The BlockPos of the click.
     * @param level       - The dimension.
     */
    private FullscreenMapEvent(boolean cancellable, BlockPos location, int level)
    {
        super(cancellable, level);
        this.location = location;
    }

    public BlockPos getLocation()
    {
        return location;
    }

    public int getLevel()
    {
        return dimension;
    }


    /**
     * ClickedEvent, handles mouseclicks pre and post, pre is cancelable post is not.
     */
    public static class ClickEvent extends FullscreenMapEvent
    {
        private final int button;
        private final Point2D.Double mousePosition;
        private final Stage stage;

        /**
         * Map clicked event, fired when a user clicks on the map.
         * Can be canceled.
         *
         * @param stage         - The stage
         * @param location      - The BlockPos of the click.
         * @param level         - The dimension.
         * @param mousePosition - The precalculated scaled mouse position.
         * @param button        - The mouse button.
         */
        public ClickEvent(Stage stage, BlockPos location, int level, Point2D.Double mousePosition, int button)
        {
            super(true, location, level);
            this.stage = stage;
            this.mousePosition = mousePosition;
            this.button = button;
        }

        public Stage getStage()
        {
            return stage;
        }

        public double getMouseX()
        {
            return mousePosition.x;
        }

        public double getMouseY()
        {
            return mousePosition.y;
        }

        public Point2D.Double getMousePosition()
        {
            return mousePosition;
        }

        public int getButton()
        {
            return button;
        }

        @Override
        public boolean isCancellable()
        {
            return this.stage == Stage.PRE;
        }
    }

    /**
     * MouseDraggedEvent, handles mouse dragging pre and post, pre is cancelable post is not.
     */
    public static class MouseDraggedEvent extends FullscreenMapEvent
    {
        private final int button;
        private final Point2D.Double mousePosition;
        private final Stage stage;

        /**
         * Map MouseDraggedEvent event, fired when a user drag the mouse on the map.
         * Can be canceled.
         *
         * @param stage         - The stage
         * @param location      - The BlockPos of the click.
         * @param level         - The dimension.
         * @param mousePosition - The precalculated scaled mouse position.
         * @param button        - The mouse button.
         */
        public MouseDraggedEvent(Stage stage, BlockPos location, int level, Point2D.Double mousePosition, int button)
        {
            super(true, location, level);
            this.stage = stage;
            this.mousePosition = mousePosition;
            this.button = button;
        }

        public Stage getStage()
        {
            return stage;
        }

        public double getMouseX()
        {
            return mousePosition.x;
        }

        public double getMouseY()
        {
            return mousePosition.y;
        }

        public Point2D.Double getMousePosition()
        {
            return mousePosition;
        }

        public int getButton()
        {
            return button;
        }

        @Override
        public boolean isCancellable()
        {
            return this.stage == Stage.PRE;
        }
    }

    /**
     * MouseDraggedEvent, handles mouse dragging pre and post, pre is cancelable post is not.
     */
    public static class MouseMoveEvent extends FullscreenMapEvent
    {
        private final Point2D.Double mousePosition;
        private final IBlockInfo info;

        /**
         * Map clicked event, fired when a user clicks on the map.
         * Can be canceled.
         *
         * @param info  - The BlockInfo.
         * @param level - The dimension.
         */
        public MouseMoveEvent(int level, IBlockInfo info, Point2D.Double mousePosition)
        {
            super(false, info.getBlockPos(), level);
            this.mousePosition = mousePosition;
            this.info = info;
        }

        public Point2D.Double getMousePosition()
        {
            return mousePosition;
        }

        public double getMouseX()
        {
            return mousePosition.x;
        }

        public double getMouseY()
        {
            return mousePosition.y;
        }

        public IBlockInfo getInfo()
        {
            return info;
        }
    }

    public enum Stage
    {
        PRE, POST
    }

}
