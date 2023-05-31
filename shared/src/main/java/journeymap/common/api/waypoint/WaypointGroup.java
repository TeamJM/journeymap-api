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
    protected boolean enable = true;
    @Since(1)
    protected WaypointSettings settings;

    @Since(1)
    protected List<String> waypoints;

    public WaypointGroup()
    {
        guid = UUID.randomUUID().toString();
        waypoints = new ArrayList<>();
    }

    public void addWaypoint(Waypoint waypoint)
    {
        waypoints.add(waypoint.getGuid());
    }

    public String getGuid()
    {
        return guid;
    }

    public boolean isEnabled()
    {
        return enable;
    }

    public void setEnabled(boolean enable)
    {
        this.enable = enable;
    }

    public WaypointSettings getSettings()
    {
        return settings;
    }

    public List<String> getWaypoints()
    {
        return waypoints;
    }
}
