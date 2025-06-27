package journeymap.api.v2.common;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface IJourneyMapPlugin
{
    /**
     * Used by JourneyMap to associate your mod id with your plugin instance.
     */
    String getModId();

}
