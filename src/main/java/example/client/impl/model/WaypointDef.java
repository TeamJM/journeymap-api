/*
 *
 * JourneyMap API
 * http://journeymap.info
 * http://bitbucket.org/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2015 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 * + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 * + Write your own code that uses, modifies, or extends the example source code in example.* packages
 * + Distribute compiled classes of unmodified API source code in journeymap.* packages
 * + Fork and modify any source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified API source code from journeymap.* packages or compiled classes of modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API source code or example source code in any way not explicitly granted
 *        by this license statement.
 *
 */

package example.client.impl.model;

import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

/**
 * Definition for a waypoint that is offered to a user.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "journeymap.client.api.model.IWaypointDef", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapPoint", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapIcon", modid = "journeymap"),
})
public final class WaypointDef implements journeymap.client.api.model.IWaypointDef
{
    private journeymap.client.api.model.IMapPoint point;
    private journeymap.client.api.model.IMapIcon icon;
    private String waypointId;
    private String waypointGroupName;
    private String waypointName;
    private int[] dimensions;
    private int color;
    private String iconName;

    /**
     * Constructor.
     *
     * @param waypointId        Unique id for waypoint (scoped to your mod)
     * @param waypointGroupName (Optional) Group or category name for the waypoint.
     * @param waypointName      Waypoint name.
     * @param point             Waypoint location.
     * @param icon              (Optional) Icon to display at the point.
     * @param color             rgb color of waypoint label
     * @param dimensions        Dimensions where waypoint should be displayed.
     */
    public WaypointDef(String waypointId, String waypointGroupName, String waypointName, journeymap.client.api.model.IMapPoint point,
                       journeymap.client.api.model.IMapIcon icon, String iconName, int color, int[] dimensions)
    {
        Verify.verifyNotNull(waypointId);
        Verify.verifyNotNull(waypointName);
        Verify.verifyNotNull(point);
        Verify.verifyNotNull(icon);
        Verify.verifyNotNull(iconName);

        this.waypointId = waypointId;
        this.waypointGroupName = waypointGroupName;
        this.waypointName = waypointName;
        this.point = point;
        this.icon = icon;
        this.iconName = iconName;
        this.color = Math.max(0x000000, Math.min(color, 0xffffff));
        this.dimensions = dimensions;
    }

    /**
     * Unique id (scoped to your mod)
     */
    @Override
    public String getWaypointId()
    {
        return waypointId;
    }

    /**
     * (Optional) Group or category name for the waypoint.
     */
    @Override
    public String getWaypointGroupName()
    {
        return waypointGroupName;
    }

    /**
     * Waypoint name.
     */
    @Override
    public String getWaypointName()
    {
        return waypointName;
    }

    /**
     * Waypoint location.
     */
    @Override
    public journeymap.client.api.model.IMapPoint getPoint()
    {
        return point;
    }

    /**
     * Color for waypoint label.
     *
     * @return rgb int
     */
    @Override
    public int getColor()
    {
        return color;
    }

    /**
     * Dimensions where waypoint should be displayed.
     */
    @Override
    public int[] getDimensions()
    {
        return dimensions;
    }

    /**
     * Icon specification for waypoint.
     *
     * @return spec
     */
    @Override
    public journeymap.client.api.model.IMapIcon getIcon()
    {
        return icon;
    }

    /**
     * If the icon is saved to a file by JourneyMap, this determines the filename that will be used.
     *
     * @return icon filename
     */
    @Override
    public String getIconName()
    {
        return iconName;
    }
}
