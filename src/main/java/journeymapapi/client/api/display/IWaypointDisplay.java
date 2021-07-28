package journeymapapi.client.api.display;

import journeymapapi.client.api.model.MapImage;

/**
 * Values related to displaying a Waypoint.
 */
public interface IWaypointDisplay
{
    Integer getColor();

    Integer getBackgroundColor();

    String[] getDisplayDimensions();

    MapImage getIcon();
}
