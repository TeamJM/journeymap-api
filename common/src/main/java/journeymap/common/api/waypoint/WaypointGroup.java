package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WaypointGroup
{
    @Since(1)
    protected String version = "1";
    @Since(1)
    protected final String guid;
    @Since(1)
    protected GroupSettings settings;

    @Since(1)
    protected List<String> waypoints;

    @Since(1)
    protected String name;
    @Since(1)
    protected final String modId;
    protected transient boolean dirty;

    public WaypointGroup(String modId, String name)
    {
        this.guid = UUID.randomUUID().toString();
        this.waypoints = new ArrayList<>();
        this.modId = modId;
        this.name = name;
        this.settings = (GroupSettings) new GroupSettings().setEnable(true).setDirty(true);
    }

    public WaypointGroup addWaypoint(Waypoint waypoint)
    {
        waypoints.add(waypoint.getGuid());
        this.setDirty()
        ;return this;
    }

    public String getGuid()
    {
        return guid;
    }

    public GroupSettings getSettings()
    {
        return settings;
    }

    public List<String> getWaypoints()
    {
        return waypoints;
    }

    public boolean isEnabled()
    {
        return this.settings.isEnable();
    }

    public WaypointGroup setEnabled(boolean enabled)
    {
        this.settings.setEnable(enabled);
        this.setDirty();
        return this;
    }

    public String getVersion()
    {
        return version;
    }

    public String getName()
    {
        return name;
    }

    public String getKey()
    {
        return String.format("%s:%s", modId, name);
    }

    public String getModId()
    {
        return modId;
    }

    public boolean isDirty()
    {
        return dirty || this.settings.isDirty();
    }

    public WaypointGroup setDirty()
    {
        return setDirty(true);
    }

    public WaypointGroup setDirty(boolean dirty)
    {
        this.dirty = dirty;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        WaypointGroup group = (WaypointGroup) o;

        return guid.equals(group.guid);

    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + guid.hashCode();
        return result;
    }
}
