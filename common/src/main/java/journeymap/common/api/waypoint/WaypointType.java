package journeymap.common.api.waypoint;

import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;

public enum WaypointType implements StringRepresentable
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
    public static final Map<String, WaypointType> types;

    static
    {
        types = new HashMap<>();
        for (WaypointType o : values())
        {
            types.put(o.name(), o);
        }
    }

    WaypointType(String value)
    {
        this.value = value;
    }

    public static WaypointType from(String string)
    {
        return types.get(string);
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String getSerializedName()
    {
        return this.value;
    }
}
