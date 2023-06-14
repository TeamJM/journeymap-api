package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;

public class WaypointSettings extends CommonSettings
{
    @Since(1)
    protected boolean showDeviation = false;

    public boolean showDeviation()
    {
        return showDeviation;
    }

    public void setShowDeviation(boolean showDeviation)
    {
        this.showDeviation = showDeviation;
    }
}
