package journeymap.api.v2.client.fullscreen;

import journeymap.api.v2.common.Context;
import journeymap.api.v2.client.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.awt.geom.Point2D;

public interface IFullscreen
{
    /**
     * Updates to a maptype.
     *
     * @param mapType   - The maptype.
     * @param vSlice    - The slice.
     * @param dimension - The dimension
     */
    void updateMapType(Context.MapType mapType, Integer vSlice, ResourceKey<Level> dimension);

    /**
     * Moves to the next maptype
     */
    void toggleMapType();

    /**
     * Zooms in
     */
    void zoomIn();

    /**
     * Zoomes out.
     */
    void zoomOut();

    /**
     * Centers the map on x, z coord
     *
     * @param x - The X coord.
     * @param z - The Z coord.
     */
    void centerOn(double x, double z);

    /**
     * Closes the map.
     */
    void close();

    /**
     * Gets the current UIState
     *
     * @return - The uiState.
     */
    UIState getUiState();

    /**
     * Gets minecraft
     *
     * @return Minecraft
     */
    Minecraft getMinecraft();

    /**
     * Gets the screen.
     *
     * @return the Screen.
     */
    Screen getScreen();

    /**
     * Gets the fullscreen mouseDrag position.
     *
     * @return Point2D.Double of the mouse drag.
     */
    Point2D.Double getMouseDrag();

    /**
     * Center Block X
     *
     * @param withDragOffset - with the calculated drag offset.
     * @return position of the center X block
     */
    double getCenterBlockX(boolean withDragOffset);

    /**
     * Center Block Z
     *
     * @param withDragOffset- with the calculated drag offset.
     * @return position of the center Z block
     */
    double getCenterBlockZ(boolean withDragOffset);
}
