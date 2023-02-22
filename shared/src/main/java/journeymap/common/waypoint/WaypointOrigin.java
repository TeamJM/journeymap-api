package journeymap.common.waypoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum WaypointOrigin
{
    SERVER("server"), // for server sided waypoints //TODO: create in the future
    COMMAND("command"), // for waypoints created by /waypoint command
    EXTERNAL("external"), // for waypoints created by external servers.
    EXTERNAL_FORCE("external-force"), // ignores all disabling rules for waypoints. For special servers that want to add temporary waypoints users cannot disable.
    TEMP("temp"); // vanishes as you approach.

    final String value;
    private static final Map<String, WaypointOrigin> values;

    static
    {
        values = new HashMap<>();
        for (WaypointOrigin o : values())
        {
            values.put(o.name(), o);
        }
    }

    WaypointOrigin(String value)
    {
        this.value = value;
    }

    public static WaypointOrigin from(String string)
    {
        return values.get(string);
    }

    public String getValue()
    {
        return value;
    }

    public static Set<String> getValues()
    {
        return values.keySet();
    }
}
