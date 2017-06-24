/*
 * JourneyMap API (http://journeymap.info)
 * http://bitbucket.org/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package journeymap.client.api.display;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import journeymap.client.api.model.MapImage;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Definition for a waypoint that is offered to a player.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 * <p>
 * Note that like all Displayables, simply changing this object doesn't guarantee the player will get the changes.
 * You must call {@link journeymap.client.api.IClientAPI#show(Displayable)} in order for the changes to take effect
 * in JourneyMap.
 */
public class ModWaypoint extends Displayable
{
    protected transient boolean dirty;
    private int dim;
    private BlockPos pos;
    private MapImage icon;
    private String groupName;
    private String name;
    private int[] displayDims;
    private int color;
    private int bgColor;
    private boolean persistent;
    private boolean editable;

    /**
     * Constructor.
     *
     * @param modId             Your mod id
     * @param waypointId        Unique id for waypoint (scoped to your mod)
     * @param waypointGroupName (Optional) Group or category name for the waypoint.
     * @param waypointName      Waypoint name.
     * @param dimension         Dimension id where waypoint should be displayed.
     * @param x                 World x
     * @param y                 World y
     * @param z                 World z
     * @param color             rgb color of waypoint label
     * @param icon              (Optional) Icon to display at the point.

     */
    public ModWaypoint(String modId, String waypointId, String waypointGroupName, String waypointName, int dimension,
                       int x, int y, int z, @Nullable MapImage icon, int color, boolean persistent)
    {
        this(modId, waypointId, waypointGroupName, waypointName, dimension, new BlockPos(x, y, z), icon, color, persistent);
    }

    public ModWaypoint(String modId, String waypointId, @Nullable String waypointGroupName, String waypointName,
                       int dimension, BlockPos position, @Nullable MapImage icon, int color, boolean persistent)
    {
        this(modId, waypointId, waypointGroupName, waypointName, dimension, position, icon, color, persistent, new int[]{dimension});
    }

    /**
     * Constructor.
     *
     * @param modId             Your mod id
     * @param waypointId         Unique id for waypoint (scoped to your mod)
     * @param waypointGroupName (Optional) Group or category name for the waypoint.
     * @param color             rgb color of waypoint label
     * @param waypointName      Waypoint name.
     * @param position          Waypoint location.
     * @param icon              (Optional) Icon to display at the point.
     * @param displayDimensions Dimension ids where waypoint should be displayed.
     */
    public ModWaypoint(String modId, String waypointId, @Nullable String waypointGroupName, String waypointName,
                       int dimension, BlockPos position, @Nullable MapImage icon, int color, boolean persistent, int[] displayDimensions)
    {
        super(modId, waypointId, DisplayType.Waypoint);
        setPersistent(persistent);
        if (!Strings.isNullOrEmpty(waypointGroupName))
        {
            setGroupName(waypointGroupName);
        }
        setName(waypointName);
        setPosition(dimension, position);
        if (icon != null)
        {
            setIcon(icon);
        }
        setColor(color);
        setBackgroundColor(0x000000);
        setDisplayDimensions(displayDimensions);
    }

    /**
     * (Optional) Group or category name for the waypoint.
     */
    public final String getGroupName()
    {
        return groupName;
    }

    /**
     * Sets the waypoint group name.
     *
     * @param groupName the name
     * @return this
     */
    public ModWaypoint setGroupName(String groupName)
    {
        this.groupName = groupName;
        return setDirty();
    }

    /**
     * Waypoint name.
     */
    public final String getName()
    {
        return name;
    }

    /**
     * Sets the waypoint name.
     *
     * @param waypointName the name
     * @return this
     */
    public final ModWaypoint setName(String waypointName)
    {
        this.name = waypointName;
        return setDirty();
    }

    public final int getDimension()
    {
        return dim;
    }

    /**
     * Waypoint location.
     */
    public final BlockPos getPosition()
    {
        return pos;
    }

    /**
     * Sets the waypoint location.
     *
     * @param position the BlockPos
     * @return this
     */
    public ModWaypoint setPosition(int dimension, BlockPos position)
    {
        this.dim = dimension;
        this.pos = position;
        return setDirty();
    }

    /**
     * Color for waypoint label.
     *
     * @return rgb int
     */
    public final int getColor()
    {
        return color;
    }

    /**
     * Sets the rgb color (between 0x000000 - 0xffffff) of the
     * waypoint name.
     *
     * @param color the color
     * @return this
     */
    public final ModWaypoint setColor(int color)
    {
        this.color = clampRGB(color);
        return setDirty();
    }

    /**
     * Background color for waypoint label.
     *
     * @return rgb int
     */
    public final int getBackgroundColor()
    {
        return bgColor;
    }


    /**
     * Sets the rgb color (between 0x000000 - 0xffffff) of the
     * waypoint name's background.
     *
     * @param bgColor the color
     * @return this
     */
    public final ModWaypoint setBackgroundColor(int bgColor)
    {
        this.bgColor = clampRGB(bgColor);
        return setDirty();
    }

    /**
     * Dimensions where waypoint should be displayed.
     */
    public final int[] getDisplayDimensions()
    {
        return displayDims;
    }

    /**
     * Sets the displayDims in which the waypoint should appear.
     *
     * @param dimensions the displayDims
     * @return this
     */
    public final ModWaypoint setDisplayDimensions(int... dimensions)
    {
        this.displayDims = dimensions;
        return setDirty();
    }

    /**
     * Sets whether to display in a given dimension.
     *
     * @param dimension dim id
     * @param displayed true to display
     */
    public void setDisplayed(int dimension, boolean displayed)
    {
        if (displayed && !isDisplayed(dimension))
        {
            setDisplayDimensions(ArrayUtils.add(getDisplayDimensions(), dimension));
        }
        else if (!displayed && isDisplayed(dimension))
        {
            setDisplayDimensions(ArrayUtils.removeElement(getDisplayDimensions(), dimension));
        }
    }

    /**
     * Whether the waypoint is shown in the dimension.
     *
     * @param dimension dim id
     * @return true if dim id is in getDisplayDimensions()
     */
    public final boolean isDisplayed(int dimension)
    {
        return Arrays.binarySearch(displayDims, dimension) > -1;
    }

    /**
     * Icon specification for waypoint.
     *
     * @return spec
     */
    public MapImage getIcon()
    {
        return icon;
    }

    /**
     * Sets the icon.
     *
     * @param icon the icon
     * @return this
     */
    public final ModWaypoint setIcon(MapImage icon)
    {
        this.icon = icon;
        return setDirty();
    }

    /**
     * If the icon is saved to a file by JourneyMap, this determines the filename that will be used.
     *
     * @return icon filename
     */
    public final String getIconName()
    {
        if (icon != null && icon.getImageLocation() != null)
        {
            return getIcon().getImageLocation().getResourcePath();
        }
        else
        {
            return null;
        }
    }

    /**
     * Whether or not the waypoint should be persisted (saved to file)
     * after the player disconnects from the world or changes displayDims.
     *
     * @return true if persistent
     */
    public final boolean isPersistent()
    {
        return this.persistent;
    }

    /**
     * Sets whether or not the waypoint should be persisted (saved to file)
     * after the player disconnects from the world or changes displayDims.
     *
     * @param persistent true if save to file
     * @return this
     */
    public final ModWaypoint setPersistent(boolean persistent)
    {
        this.persistent = persistent;
        if (!persistent)
        {
            dirty = false;
        }
        return setDirty();
    }

    /**
     * Whether the player can edit the waypoint.
     *
     * @return true if editable
     */
    public final boolean isEditable()
    {
        return editable;
    }

    /**
     * Sets whether the player can edit the waypoint.
     *
     * @param editable is editable
     * @return this
     */
    public final ModWaypoint setEditable(boolean editable)
    {
        this.editable = editable;
        return setDirty();
    }

    /**
     * Sets dirty.
     *
     * @return the dirty
     */
    public ModWaypoint setDirty()
    {
        return setDirty(true);
    }

    public boolean isDirty()
    {
        return persistent && dirty;
    }

    /**
     * Sets dirty.
     *
     * @param dirty the dirty
     * @return the dirty
     */
    public ModWaypoint setDirty(boolean dirty)
    {
        if (persistent)
        {
            this.dirty = dirty;
        }
        return this;
    }

    /**
     * Not specified by ModWaypoint; always returns 0.
     */
    @Override
    public int getDisplayOrder()
    {
        return 0;
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
        if (!super.equals(o))
        {
            return false;
        }
        ModWaypoint that = (ModWaypoint) o;
        return dim == that.dim &&
                getColor() == that.getColor() &&
                bgColor == that.bgColor &&
                isPersistent() == that.isPersistent() &&
                isEditable() == that.isEditable() &&
                isDirty() == that.isDirty() &&
                Objects.equal(pos, that.pos) &&
                Objects.equal(getIcon(), that.getIcon()) &&
                Objects.equal(getGroupName(), that.getGroupName()) &&
                Objects.equal(getName(), that.getName()) &&
                Objects.equal(displayDims, that.displayDims);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), dim, pos, getIcon(), getGroupName(), getName(), displayDims, getColor(), bgColor, isPersistent(), isEditable(), isDirty());
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("bgColor", bgColor)
                .add("color", color)
                .add("dim", dim)
                .add("dirty", dirty)
                .add("displayDims", displayDims)
                .add("editable", editable)
                .add("groupName", groupName)
                .add("icon", icon)
                .add("name", name)
                .add("persistent", persistent)
                .add("pos", pos)
                .toString();
    }


}
