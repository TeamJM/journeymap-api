package journeymap.client.api.model;

import journeymap.client.api.display.Context;
import journeymap.client.api.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

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
}
