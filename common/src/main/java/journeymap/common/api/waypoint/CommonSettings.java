package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;

public class CommonSettings
{

    public CommonSettings(boolean enable)
    {
        this.enable = enable;
    }

    /**
     * The Enable.
     */
    @Since(1)
    protected boolean enable = true;

    protected transient boolean dirty;

    public boolean isDirty()
    {
        return this.dirty;
    }

    public CommonSettings setDirty(boolean dirty)
    {
        this.dirty = dirty;
        return this;
    }

    public boolean isEnable()
    {
        return enable;
    }

    public CommonSettings setEnable(boolean enable)
    {
        this.enable = enable;
        this.setDirty(true);
        return this;
    }
}
