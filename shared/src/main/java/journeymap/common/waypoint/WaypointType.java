package journeymap.common.waypoint;

import java.util.HashMap;
import java.util.Map;

public enum WaypointType
{
    /**
     * Normal type.
     */
    Normal("Normal"),
    /**
     * Death type.
     */
    Death("Death");

    final String value;
    private static final Map<String, WaypointType> values;

    static
    {
        values = new HashMap<>();
        for (WaypointType o : values())
        {
            values.put(o.name(), o);
        }
    }

    WaypointType(String value)
    {
        this.value = value;
    }

    public static WaypointType from(String string)
    {
        return values.get(string);
    }

    public String getValue()
    {
        return value;
    }
}
