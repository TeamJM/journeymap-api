package journeymap.client.api.model;

import journeymap.client.api.display.Context;
import journeymap.client.api.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface IFullscreen
{
    void updateMapType(Context.MapType mapType, Integer vSlice, ResourceKey<Level> dimension);

    void toggleMapType();

    void zoomIn();

    void zoomOut();

    void centerOn(double x, double z);

    void close();

    UIState getUiState();

    Minecraft getMinecraft();
}
