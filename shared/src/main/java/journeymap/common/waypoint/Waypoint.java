package journeymap.common.waypoint;

import com.google.gson.annotations.Since;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.TreeSet;

public class Waypoint
{
    /**
     * The Id.
     */
    @Since(1)
    protected String id;

    /**
     * The Name.
     */
    @Since(1)
    protected String name;

    /**
     * Used for the webmap
     */
    @Since(1)
    protected String colorizedIcon;

    /**
     * The Persistent.
     */
    @Since(2)
    protected boolean persistent;
    /**
     * The X.
     */
    @Since(1)
    protected int x;

    /**
     * The Y.
     */
    @Since(1)
    protected int y;

    /**
     * The Z.
     */
    @Since(1)
    protected int z;

    /**
     * The red.
     */
    @Since(1)
    protected int red;

    /**
     * The green.
     */
    @Since(1)
    protected int green;

    /**
     * The blue.
     */
    @Since(1)
    protected int blue;

    /**
     * The Type.
     */
    @Since(1)
    protected String type;

    /**
     * The Origin.
     */
    @Since(1)
    protected String origin;

    /**
     * The Settings
     */
    @Since(1)
    protected WaypointSettings settings;

    /**
     * The icon
     */
    @Since(1)
    protected WaypointIcon icon;

    /**
     * The Dimensions.
     */
    @Since(1)
    protected TreeSet<String> dimensions;

    public Waypoint(String modid, String id, String name, ResourceKey<Level> dimension, BlockPos blockPos)
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getColorizedIcon()
    {
        return colorizedIcon;
    }

    /**
     * Used internally, this is for webmap waypoint icons.
     *
     * @param colorizedIcon - the recolored icon
     */
    public void setColorizedIcon(String colorizedIcon)
    {
        this.colorizedIcon = colorizedIcon;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getZ()
    {
        return z;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public int getRed()
    {
        return red;
    }

    public void setRed(int red)
    {
        this.red = red;
    }

    public int getGreen()
    {
        return green;
    }

    public void setGreen(int green)
    {
        this.green = green;
    }

    public int getBlue()
    {
        return blue;
    }

    public void setBlue(int blue)
    {
        this.blue = blue;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public WaypointSettings getSettings()
    {
        return settings;
    }

    public void setSettings(WaypointSettings settings)
    {
        this.settings = settings;
    }

    public WaypointIcon getIcon()
    {
        return icon;
    }

    public void setIcon(WaypointIcon icon)
    {
        this.icon = icon;
    }

    public TreeSet<String> getDimensions()
    {
        return dimensions;
    }

    public void setDimensions(TreeSet<String> dimensions)
    {
        this.dimensions = dimensions;
    }

    public boolean isPersistent()
    {
        return persistent;
    }

    public void setPersistent(boolean persistent)
    {
        this.persistent = persistent;
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

        Waypoint waypoint = (Waypoint) o;


        if (getSettings().enable != waypoint.getSettings().enable)
        {
            return false;
        }
        if (green != waypoint.green)
        {
            return false;
        }
        if (blue != waypoint.blue)
        {
            return false;
        }
        if (red != waypoint.red)
        {
            return false;
        }
        if (x != waypoint.x)
        {
            return false;
        }
        if (y != waypoint.y)
        {
            return false;
        }
        if (z != waypoint.z)
        {
            return false;
        }
        if (!dimensions.equals(waypoint.dimensions))
        {
            return false;
        }
        if (!icon.equals(waypoint.icon))
        {
            return false;
        }
        if (!id.equals(waypoint.id))
        {
            return false;
        }
        if (!name.equals(waypoint.name))
        {
            return false;
        }
        if (origin.equals(waypoint.origin))
        {
            return false;
        }
        if (!Objects.equals(type, waypoint.type))
        {
            return false;
        }
        if (getSettings().showDeviation != waypoint.getSettings().showDeviation)
        {
            return false;
        }
        if (getIcon().getColor() != waypoint.getIcon().getColor())
        {
            return false;
        }
        return true;
    }
}
