package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;

import java.awt.Color;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.TreeSet;
import java.util.UUID;
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

    @Since(1)
    protected final String guid;

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
    protected WaypointPos pos;

    /**
     * The red.
     */
    @Since(1)
    protected int color;

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
    @ApiStatus.Internal
    protected Waypoint()
    {
        this.guid = UUID.randomUUID().toString();
    }

    /**
     * This constructor is for internal use only. Will cause problems when using it.
     */
    @ApiStatus.Internal
    protected Waypoint(String guid)
    {
        this.guid = guid;
    }

    @ApiStatus.Internal
    protected Waypoint(Waypoint original)
    {
        this(original.name,
                original.pos,
                original.settings.enable,
                original.color,
                original.type,
                original.origin,
                original.dimensions == null || original.dimensions.isEmpty() ? null : original.dimensions.first(),
                original.dimensions,
                original.settings.showDeviation,
                original.icon != null ? new WaypointIcon(original.icon) : null);
        this.pos = original.pos;
        this.modId = original.modId;
    }

    public Waypoint(Builder builder)
    {
        this(
                builder.name,
                builder.pos,
                builder.enabled,
                builder.color,
                builder.type,
                builder.origin,
                builder.currentDimension,
                builder.dimensions,
                builder.showDeviation,
                builder.icon
        );
        this.modId = builder.modId;
        this.displayId = builder.displayId == null ? (this.modId + ":" + this.id) : builder.displayId;
    }


    private Waypoint(String name,
                     WaypointPos pos,
                     boolean enable,
                     int color,
                     WaypointType type,
                     String origin,
                     String currentDimension,
                     Collection<String> dimensions,
                     boolean showDeviation,
                     WaypointIcon icon)
    {
        this.guid = UUID.randomUUID().toString();
        if (name == null)
        {
            name = createName(pos);
        }

        if (dimensions == null)
        {
            this.dimensions = new TreeSet<>();
        }
        else
        {
            this.dimensions = new TreeSet<>(dimensions);
        }

        if (currentDimension != null)
        {
            this.dimensions.add(currentDimension);
        }
        this.name = name;
        this.color = color;
        this.type = type;
        this.origin = origin;
        this.persistent = true;

        if (settings == null)
        {
            this.settings = new WaypointSettings(true, false);
        }

        settings.setEnable(enable);
        settings.setShowDeviation(showDeviation);

        this.icon = icon;

        if (icon == null)
        {
            switch (type)
            {
                case Normal -> this.setIcon(new WaypointIcon(DEFAULT_ICON_NORMAL));
                case Death -> this.setIcon(new WaypointIcon(DEFAULT_ICON_DEATH));
            }
        }
        this.pos = pos;
        setLocation(pos.x, pos.y, pos.z, currentDimension);
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

    private static String createName(WaypointPos pos)
    {
        return String.format("%s, %s", pos.x, pos.y);
    }

    public void markDirty()
    {
        this.updateId();
        this.setDirty(true);
    }

    public String getDisplayId()
    {
        return this.displayId;
    }

    public String getId()
    {
        return id;
    }

    public String getVersion()
    {
        return version;
    }

    public String getModId()
    {
        return modId;
    }

    /**
     * This is a static ID used for group tracking, primary key.
     *
     * @return - the guid
     */
    public String getGuid()
    {
        return guid;
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

    public WaypointPos getPos()
    {
        return pos;
    }

    public void setPos(WaypointPos pos)
    {
        this.pos = pos;
    }

    public int getX()
    {
        return this.pos.x;
    }

    public void setX(int x)
    {
        this.pos.setX(x);
        this.markDirty();
    }

    public int getY()
    {
        return this.pos.y;
    }

    public void setY(int y)
    {
        this.pos.setY(y);
        this.markDirty();
    }

    public int getZ()
    {
        return this.pos.z;
    }

    public void setZ(int z)
    {
        this.pos.setZ(z);
        this.markDirty();
    }

    public int getRed()
    {
        return (this.color >> 16) & 0xFF;
    }

    public void setRed(int red)
    {
        this.updateColors(red, getGreen(), getBlue());
    }

    public int getGreen()
    {
        return (this.color >> 8) & 0xFF;
    }

    public void setGreen(int green)
    {
        this.updateColors(getRed(), green, getBlue());
    }

    public int getBlue()
    {
        return (this.color) & 0xFF;
    }

    public void setBlue(int blue)
    {
        this.updateColors(getRed(), getGreen(), blue);
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
        this.pos.setX(("minecraft:the_nether".equalsIgnoreCase(currentDimension)) ? x * 8 : x);
        this.pos.setY(y);
        this.pos.setZ(("minecraft:the_nether".equalsIgnoreCase(currentDimension)) ? z * 8 : z);
        this.markDirty();
        return this;
    }

    private void updateId()
    {
        String newId = String.format("%s_%s,%s,%s", this.name, this.pos.x, this.pos.y, this.pos.z);
        this.id = newId.replaceAll(CSS_SAFE_PATTERN.pattern(), "-");
    }

    public int getColor()
    {
        return this.color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    private void updateColors(int red, int green, int blue)
    {
        this.color = ((0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8) |
                ((blue & 0xFF));
        this.markDirty();
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
        if (color != waypoint.color)
        {
            return false;
        }
        if (pos.x != waypoint.pos.x)
        {
            return false;
        }
        if (pos.y != waypoint.pos.y)
        {
            return false;
        }
        if (pos.z != waypoint.pos.z)
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
        private WaypointPos pos;
        private Integer color;
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
            this.pos = new WaypointPos(x, y, z);
            return this;
        }

        public Builder withBlockPos(BlockPos blockPos)
        {
            return withPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public Builder withColor(int color)
        {
            this.color = color;
            return this;
        }

        public Builder withColor(Color color)
        {
            return withRgb(color.getRed(), color.getGreen(), color.getBlue());
        }

        public Builder withRgb(int red, int green, int blue)
        {
            this.color = ((0xFF) << 24) |
                    ((red & 0xFF) << 16) |
                    ((green & 0xFF) << 8) |
                    ((blue & 0xFF));

            return this;
        }

        public Waypoint build()
        {
            this.validate();
            return new Waypoint(this);
        }

        private void validate()
        {
            if (this.pos == null)
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
            if (color == null)
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
