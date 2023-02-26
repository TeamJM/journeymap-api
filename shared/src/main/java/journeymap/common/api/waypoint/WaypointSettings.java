package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;

public class WaypointSettings
{
    /**
     * The Enable.
     */
    @Since(1)
    protected boolean enable = true;

    @Since(1)
    protected boolean showDeviation = false;

    protected transient boolean dirty;

    public boolean isDirty()
    {
        return this.dirty;
    }

    public void setDirty(boolean dirty)
    {
        this.dirty = dirty;
    }

    public boolean isEnable()
    {
        return enable;
    }

    public void setEnable(boolean enable)
    {
        this.enable = enable;
    }

    public boolean showDeviation()
    {
        return showDeviation;
    }

    public void setShowDeviation(boolean showDeviation)
    {
        this.showDeviation = showDeviation;
    }
}
