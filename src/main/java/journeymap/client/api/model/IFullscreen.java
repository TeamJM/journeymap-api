package journeymap.client.api.model;

import journeymap.client.api.display.Context;
import journeymap.client.api.util.UIState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;


public interface IFullscreen
{
    void updateMapType(Context.MapType mapType, Integer vSlice, RegistryKey<World> dimension);

    void toggleMapType();

    void zoomIn();

    void zoomOut();

    void centerOn(double x, double z);

    void close();

    UIState getUiState();

    Minecraft getMinecraft();
}
