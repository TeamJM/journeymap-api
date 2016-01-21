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
import journeymap.client.api.model.MapImage;
import net.minecraft.util.BlockPos;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Definition for a waypoint that is offered to a player.
 * <p/>
 * Setters use the Builder pattern so they can be chained.
 * <p/>
 * Note that like all Displayables, simply changing this object doesn't guarantee the player will get the changes.
 * You must call {@link journeymap.client.api.IClientAPI#show(Displayable)} in order for the changes to take effect
 * in JourneyMap.
 */
public final class ModWaypoint extends Displayable
{
    private BlockPos point;
    private MapImage icon;
    private String waypointGroupName;
    private String waypointName;
    private int[] dimensions;
    private int color;
    private boolean persistent;
    private boolean editable;

    /**
     * Constructor.
     *
     * @param modId             Your example.mod id
     * @param displayId         Unique id for waypoint (scoped to your example.mod)
     * @param waypointGroupName (Optional) Group or category name for the waypoint.
     * @param waypointName      Waypoint name.
     * @param x                 World x
     * @param y                 World y
     * @param z                 World z
     * @param color             rgb color of waypoint label
     * @param icon              (Optional) Icon to display at the point.
     * @param dimension         Dimension id where waypoint should be displayed.
     */
    public ModWaypoint(String modId, String displayId, String waypointGroupName, String waypointName,
                       int x, int y, int z, @Nullable MapImage icon, int color, boolean persistent, int dimension)
    {
        this(modId, displayId, waypointGroupName, waypointName, new BlockPos(x, y, z), icon, color, persistent, dimension);
    }

    /**
     * Constructor.
     *
     * @param modId             Your example.mod id
     * @param displayId         Unique id for waypoint (scoped to your example.mod)
     * @param waypointGroupName (Optional) Group or category name for the waypoint.
     * @param color             rgb color of waypoint label
     * @param waypointName      Waypoint name.
     * @param point             Waypoint location.
     * @param icon              (Optional) Icon to display at the point.
     * @param dimensions        Dimension ids where waypoint should be displayed.
     */
    public ModWaypoint(String modId, String displayId, String waypointGroupName, String waypointName,
                       BlockPos point, @Nullable MapImage icon, int color, boolean persistent, int... dimensions)
    {
        super(modId, displayId);
        setWaypointGroupName(waypointGroupName);
        setWaypointName(waypointName);
        setPoint(point);
        setIcon(icon);
        setColor(color);
        setDimensions(dimensions);
    }

    /**
     * (Optional) Group or category name for the waypoint.
     */
    public String getWaypointGroupName()
    {
        return waypointGroupName;
    }

    /**
     * Sets the waypoint group name.
     *
     * @param waypointGroupName the name
     * @return this
     */
    public ModWaypoint setWaypointGroupName(String waypointGroupName)
    {
        this.waypointGroupName = waypointGroupName;
        return this;
    }

    /**
     * Waypoint name.
     */
    public String getWaypointName()
    {
        return waypointName;
    }

    /**
     * Sets the waypoint name.
     *
     * @param waypointName the name
     * @return this
     */
    public ModWaypoint setWaypointName(String waypointName)
    {
        this.waypointName = waypointName;
        return this;
    }

    /**
     * Waypoint location.
     */
    public BlockPos getPoint()
    {
        return point;
    }

    /**
     * Sets the waypoint location.
     *
     * @param point the point
     * @return this
     */
    public ModWaypoint setPoint(BlockPos point)
    {
        this.point = point;
        return this;
    }

    /**
     * Color for waypoint label.
     *
     * @return rgb int
     */
    public int getColor()
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
    public ModWaypoint setColor(int color)
    {
        this.color = clampRGB(color);
        return this;
    }

    /**
     * Dimensions where waypoint should be displayed.
     */
    public int[] getDimensions()
    {
        return dimensions;
    }

    /**
     * Sets the dimensions in which the waypoint should appear.
     *
     * @param dimensions the dimensions
     * @return this
     */
    public ModWaypoint setDimensions(int... dimensions)
    {
        this.dimensions = dimensions;
        return this;
    }

    /**
     * Whether the waypoint is shown in the dimension.
     *
     * @param dimension dim id
     * @return true if dim id is in getDimensions()
     */
    public boolean isInDimension(int dimension)
    {
        return Arrays.binarySearch(dimensions, dimension) > -1;
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
    public ModWaypoint setIcon(MapImage icon)
    {
        this.icon = icon;
        return this;
    }

    /**
     * If the icon is saved to a file by JourneyMap, this determines the filename that will be used.
     *
     * @return icon filename
     */
    public String getIconName()
    {
        return getIcon().getImageLocation().getResourcePath();
    }

    /**
     * Your example.mod id.
     *
     * @return modId example.mod id
     */
    public String getModId()
    {
        return modId;
    }

    /**
     * Whether or not the waypoint should be persisted (saved to file)
     * after the player disconnects from the world or changes dimensions.
     *
     * @return true if persistent
     */
    public boolean isPersistent()
    {
        return this.persistent;
    }

    /**
     * Sets whether or not the waypoint should be persisted (saved to file)
     * after the player disconnects from the world or changes dimensions.
     *
     * @param persistent true if save to file
     * @return this
     */
    public ModWaypoint setPersistent(boolean persistent)
    {
        this.persistent = persistent;
        return this;
    }

    /**
     * Whether the player can edit the waypoint.
     * @return true if editable
     */
    public boolean isEditable()
    {
        return editable;
    }

    /**
     * Sets whether the player can edit the waypoint.
     * @param editable is editable
     * @return this
     */
    public ModWaypoint setEditable(boolean editable)
    {
        this.editable = editable;
        return this;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("modId", modId)
                .add("displayId", displayId)
                .add("waypointName", waypointName)
                .add("waypointGroupName", waypointGroupName)
                .add("editable", editable)
                .add("color", color)
                .add("dimensions", dimensions)
                .add("icon", icon)
                .add("iconName", getIconName())
                .add("point", point)
                .toString();
    }
}
