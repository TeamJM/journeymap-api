package journeymap.client.api.display;

public interface IThemeToolBar
{
    /**
     * Horizontal layout.
     *
     * @param startX      - The start x coord.
     * @param y           - The top Y coord
     * @param buttonGap   - The gap between buttons.
     * @param leftToRight - Button rendering order, true for left to right, false for right to left.
     */
    void setLayoutHorizontal(int startX, final int y, int buttonGap, boolean leftToRight);

    /**
     * Centered Horizontal layout
     *
     * @param centerX     - The x coord that is the toolbar center.
     * @param y           - The top Y coord
     * @param buttonGap   - The gap between buttons.
     * @param leftToRight - Button rendering order, true for left to right, false for right to left.
     */
    void setLayoutCenteredHorizontal(final int centerX, final int y, final int buttonGap, final boolean leftToRight);

    /**
     * Distributes the buttons horizontal evenly based on the leftX and rightX values.
     *
     * @param leftX       - The leftX
     * @param y           - The top Y coord
     * @param rightX      - The right X coord.
     * @param leftToRight - Button rendering order, true for left to right, false for right to left.
     */
    void setLayoutDistributedHorizontal(final int leftX, final int y, final int rightX, final boolean leftToRight);

    /**
     * Vertical layout
     *
     * @param startX      - The start x coord.
     * @param startY      - The start y coord.
     * @param buttonGap   - The gap between buttons.
     * @param leftToRight - Button rendering order, true for left to right, false for right to left.
     */
    void setLayoutVertical(final int startX, int startY, int buttonGap, boolean leftToRight);

    /**
     * Centered Vertical layout
     *
     * @param startX      - The start x coord.
     * @param centerY     - The center y coord.
     * @param buttonGap   - The gap between buttons.
     * @param leftToRight - Button rendering order, true for left to right, false for right to left.
     */
    void setLayoutCenteredVertical(final int startX, final int centerY, final int buttonGap, final boolean leftToRight);

    /**
     * reverses the button order.
     */
    void setReverse();

    /**
     * Gets the toolbar height.
     *
     * @return the height
     */
    int getHeight();

    /**
     * Gets the toolbar width
     *
     * @return - The width.
     */
    int getWidth();

    /**
     * Gets the x position.
     *
     * @return - the x position.
     */
    int getX();

    /**
     * Gets the y position.
     *
     * @return - the y position.
     */
    int getY();

    /**
     * Gets the center x of the toolbar.
     *
     * @return - the center x
     */
    int getCenterX();

    /**
     * Gets the center/middle y of the toolbar.
     *
     * @return the middle y.
     */
    int getMiddleY();

    /**
     * Gets the bottom y of the toolbar.
     *
     * @return the bottom y.
     */
    int getBottomY();

    /**
     * Gets the right most X position of the toolbar
     *
     * @return - the right x.
     */
    int getRightX();

    /**
     * Sets the x, y position of the toolbar.
     *
     * @param x - the x
     * @param y - the y
     */
    void setPosition(int x, int y);

    /**
     * Is the mouse over the toolbar.
     *
     * @return - is mouse over.
     */
    boolean isMouseOver();
}
