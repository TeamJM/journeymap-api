package journeymap.common.waypoint;

import com.google.gson.annotations.Since;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Waypoint
{
    private static final Pattern CSS_SAFE_PATTERN = Pattern.compile("[^\\w\\p{L}]+", Pattern.UNICODE_CHARACTER_CLASS);
    /**
     * The constant ICON_NORMAL.
     */
    private static final ResourceLocation DEFAULT_ICON_NORMAL = new ResourceLocation("journeymap", "ui/img/waypoint-icon.png");
    /**
     * The constant ICON_DEATH.
     */
    private static final ResourceLocation DEFAULT_ICON_DEATH = new ResourceLocation("journeymap", "ui/img/waypoint-death-icon.png");

    /**
     * The version.
     */
    @Since(1)
    protected String version = "1";

    /**
     * The Id.
     */
    @Since(1)
    protected String id;

    @Since(1)
    protected String modId;
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
    @Since(1)
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
    protected WaypointType type;

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

    protected transient boolean dirty;

    /**
     * This constructor is for internal use only. Will cause problems when using it.
     */
    @Deprecated
    public Waypoint()
    {
    }

    public Waypoint(Waypoint original)
    {
        this(original.name,
                original.x,
                original.y,
                original.z,
                original.settings.enable,
                original.red,
                original.green,
                original.blue,
                original.type,
                original.origin,
                original.dimensions == null || original.dimensions.isEmpty() ? null : original.dimensions.first(),
                original.dimensions,
                original.settings.showDeviation);
        this.x = original.x;
        this.y = original.y;
        this.z = original.z;
        this.icon = original.icon;
        this.modId = original.modId;
    }

    public Waypoint(String modId, String name, BlockPos pos, Color color, ResourceKey<Level> dimension)
    {
        this(modId, name, pos, color, dimension.location().toString());
    }

    public Waypoint(String modId, String name, BlockPos pos, Color color, String dimension)
    {
        this(name, pos.getX(), pos.getY(), pos.getZ(), true, color.getRed(), color.getGreen(), color.getBlue(), WaypointType.Normal, modId, dimension, Collections.singletonList(dimension), false);
    }

    public Waypoint(String name, BlockPos pos, Color color, WaypointType type, String origin, String currentDimension, boolean showDeviation)
    {
        this(name, pos.getX(), pos.getY(), pos.getZ(), true, color.getRed(), color.getGreen(), color.getBlue(), type, origin, currentDimension, Collections.singletonList(currentDimension), showDeviation);
    }

    public Waypoint(String name, int x, int y, int z, boolean enable, int red, int green, int blue, WaypointType type, String origin, String currentDimension, Collection<String> dimensions, boolean showDeviation)
    {
        if (name == null)
        {
            name = createName(x, z);
        }
        this.dimensions = new TreeSet<>(dimensions);
        if (currentDimension != null)
        {
            this.dimensions.add(currentDimension);
        }
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.type = type;
        this.origin = origin;
        this.persistent = true;

        this.settings = new WaypointSettings();
        settings.setEnable(enable);
        settings.setShowDeviation(showDeviation);

        switch (type)
        {
            case Normal -> this.setIcon(new WaypointIcon(DEFAULT_ICON_NORMAL));
            case Death -> this.setIcon(new WaypointIcon(DEFAULT_ICON_DEATH));
        }
        setLocation(x, y, z, currentDimension);
    }

    public boolean isDirty()
    {
        return this.dirty || this.icon.isDirty() || settings.isDirty();
    }

    public void setDirty(boolean dirty)
    {
        this.dirty = dirty;
        if (this.icon != null)
        {
            this.icon.setDirty(dirty);
        }
        if (this.settings != null)
        {
            this.settings.setDirty(dirty);
        }
    }

    private static String createName(int x, int z)
    {
        return String.format("%s, %s", x, z);
    }

    public void markDirty()
    {
        this.updateId();
        this.setDirty(true);
    }

    public String getDisplayId()
    {
        return this.modId + ":" + this.id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
        this.markDirty();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        this.markDirty();
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
        this.markDirty();
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
        this.markDirty();
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
        this.markDirty();
    }

    public int getZ()
    {
        return z;
    }

    public void setZ(int z)
    {
        this.z = z;
        this.markDirty();
    }

    public int getRed()
    {
        return red;
    }

    public void setRed(int red)
    {
        this.red = red;
        this.markDirty();
    }

    public int getGreen()
    {
        return green;
    }

    public void setGreen(int green)
    {
        this.green = green;
        this.markDirty();
    }

    public int getBlue()
    {
        return blue;
    }

    public void setBlue(int blue)
    {
        this.blue = blue;
        this.markDirty();
    }

    public WaypointType getType()
    {
        return type;
    }

    public void setType(WaypointType type)
    {
        this.type = type;
        this.markDirty();
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
        this.markDirty();
    }

    public WaypointSettings getSettings()
    {
        return settings;
    }

    public void setSettings(WaypointSettings settings)
    {
        this.settings = settings;
        this.markDirty();
    }

    public WaypointIcon getIcon()
    {
        return icon;
    }

    public void setIcon(WaypointIcon icon)
    {
        this.icon = icon;
        this.markDirty();
    }

    public TreeSet<String> getDimensions()
    {
        return dimensions;
    }

    public void setDimensions(Collection<String> dims)
    {
        this.dimensions = new TreeSet<>(dims);
        this.markDirty();
    }

    public boolean isPersistent()
    {
        return persistent;
    }

    public void setPersistent(boolean persistent)
    {
        this.persistent = persistent;
        this.markDirty();
    }

    /**
     * Sets location.
     *
     * @param x                the x
     * @param y                the y
     * @param z                the z
     * @param currentDimension the current dimension
     * @return the location
     */
    public Waypoint setLocation(int x, int y, int z, String currentDimension)
    {
        this.x = ("minecraft:the_nether".equalsIgnoreCase(currentDimension)) ? x * 8 : x;
        this.y = y;
        this.z = ("minecraft:the_nether".equalsIgnoreCase(currentDimension)) ? z * 8 : z;
        this.markDirty();
        return this;
    }

    private void updateId()
    {
        String newId = String.format("%s_%s,%s,%s", this.name, this.x, this.y, this.z);
        this.id = newId.replaceAll(CSS_SAFE_PATTERN.pattern(), "-");
    }

    public int getColor()
    {
        return ((0xFF) << 24) |
                ((this.red & 0xFF) << 16) |
                ((this.green & 0xFF) << 8) |
                ((this.blue & 0xFF));
    }

    public void setColor(int color)
    {
        this.red = (color >> 16) & 0xFF;
        this.green = (color >> 8) & 0xFF;
        this.blue = (color) & 0xFF;
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
