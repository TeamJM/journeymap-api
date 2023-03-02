package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.awt.Color;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
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

    /**
     * The Id.
     */
    @Since(1)
    protected String displayId;

    @Since(1)
    protected String modId;
    /**
     * The Name.
     */
    @Since(1)
    protected String name;

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
    protected Waypoint()
    {
    }

    protected Waypoint(Waypoint original)
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
                original.settings.showDeviation,
                new WaypointIcon(original.icon));
        this.x = original.x;
        this.y = original.y;
        this.z = original.z;
        this.modId = original.modId;
    }

    public Waypoint(Builder builder)
    {
        this(
                builder.name,
                builder.x,
                builder.y,
                builder.z,
                builder.enabled,
                builder.red,
                builder.green,
                builder.blue,
                builder.type,
                builder.origin,
                builder.currentDimension,
                builder.dimensions,
                builder.showDeviation,
                builder.icon
        );
        this.modId = builder.modId;
        this.displayId = builder.displayId;
    }

    private Waypoint(String name,
                     int x,
                     int y,
                     int z,
                     boolean enable,
                     int red,
                     int green,
                     int blue,
                     WaypointType type,
                     String origin,
                     String currentDimension,
                     Collection<String> dimensions,
                     boolean showDeviation,
                     WaypointIcon icon)
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

        if (settings == null)
        {
            this.settings = new WaypointSettings();
        }

        settings.setEnable(enable);
        settings.setShowDeviation(showDeviation);

        if (icon == null)
        {
            switch (type)
            {
                case Normal -> this.setIcon(new WaypointIcon(DEFAULT_ICON_NORMAL));
                case Death -> this.setIcon(new WaypointIcon(DEFAULT_ICON_DEATH));
            }
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
        return this.displayId == null ? (this.modId + ":" + this.id) : this.displayId;
    }

    public String getId()
    {
        return id;
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

    public void setPos(BlockPos pos)
    {
        this.setX(pos.getX());
        this.setZ(pos.getZ());
        this.setY(pos.getY());
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

    public static class Builder
    {
        private String displayId;
        private final String modId;
        private String name;
        private boolean persistent = true;
        private Integer x;
        private Integer y;
        private Integer z;
        private Integer red;
        private Integer green;
        private Integer blue;
        private WaypointType type;
        private String origin;
        private WaypointSettings settings;
        private WaypointIcon icon;
        private Collection<String> dimensions;

        private String currentDimension;

        private boolean enabled = true;
        private boolean showDeviation = false;

        public Builder(String modId)
        {
            this.modId = modId;
        }

        public Builder isEnabled(boolean enabled)
        {
            this.enabled = enabled;
            return this;
        }

        public Builder showDeviation(boolean showDeviation)
        {
            this.showDeviation = showDeviation;
            return this;
        }

        public Builder withDimension(String dimension)
        {
            this.currentDimension = dimension;
            return this;
        }

        public Builder withDimension(ResourceKey<Level> dimension)
        {
            return withDimension(dimension.location().toString());
        }

        public Builder withDimensions(Collection<String> dimensions)
        {
            this.dimensions = dimensions;
            return this;
        }

        public Builder withIcon(WaypointIcon icon)
        {
            this.icon = icon;
            return this;
        }

        public Builder withOrigin(String origin)
        {
            this.origin = origin;
            return this;
        }

        public Builder withType(WaypointType type)
        {
            this.type = type;
            return this;
        }

        public Builder isPersistent(boolean persistent)
        {
            this.persistent = persistent;
            return this;
        }

        public Builder withName(String name)
        {

            this.name = name;
            return this;
        }

        public Builder withDisplayId(String displayId)
        {

            this.displayId = displayId;
            return this;
        }

        public Builder withPos(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public Builder withBlockPos(BlockPos blockPos)
        {
            return withPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public Builder withColorInt(int color)
        {
            return withRgb((color >> 16) & 0xFF, (color >> 8) & 0xFF, (color) & 0xFF);
        }

        public Builder withColor(Color color)
        {
            return withRgb(color.getRed(), color.getGreen(), color.getBlue());
        }

        public Builder withRgb(int red, int green, int blue)
        {
            this.red = red;
            this.blue = blue;
            this.green = green;
            return this;
        }

        public Waypoint build()
        {
            this.validate();
            return new Waypoint(this);
        }

        private void validate()
        {
            if (x == null || y == null || z == null)
            {
                throw new RuntimeException("Must provide waypoint position.");
            }

            if (this.type == null)
            {
                this.type = WaypointType.Normal;
            }

            if (this.icon == null)
            {
                this.icon = new WaypointIcon(WaypointType.Normal.equals(this.type) ? DEFAULT_ICON_NORMAL : DEFAULT_ICON_DEATH);
            }

            this.origin = this.origin == null ? this.modId : this.origin;

            // if no color provided, set to random.
            if (red == null || green == null || blue == null)
            {
                Random random = new Random();
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);

                int min = 100;
                int max = Math.max(r, Math.max(g, b));
                if (max < min)
                {
                    if (r == max)
                    {
                        r = min;
                    }
                    else if (g == max)
                    {
                        g = min;
                    }
                    else
                    {
                        b = min;
                    }
                }
                withRgb(r, g, b);
            }
        }
    }
}
