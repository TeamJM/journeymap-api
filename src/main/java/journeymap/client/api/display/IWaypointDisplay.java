package journeymap.client.api.display;

import journeymap.client.api.model.MapImage;

/**
 * Values related to displaying a Waypoint.
 */
public interface IWaypointDisplay
{
    Integer getColor();

    Integer getBackgroundColor();

    int[] getDisplayDimensions();

    MapImage getIcon();
}
