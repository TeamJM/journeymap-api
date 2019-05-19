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

import journeymap.client.api.model.MapImage;
import net.minecraft.util.math.BlockPos;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A Marker overlay shows a geographical point on the map with an icon and a label.
 * For example: A pushpin shown at the world spawn point.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 * <p>
 * Note that like all Displayables, simply changing this object doesn't guarantee the player will get the changes.
 * You must call {@link journeymap.client.api.IClientAPI#show(Displayable)} in order for the changes to take effect
 * in JourneyMap.
 */
@ParametersAreNonnullByDefault
public final class MarkerOverlay extends Overlay
{
    private BlockPos point;
    private MapImage icon;

    /**
     * Constructor.
     *
     * @param modId    Your mod id.
     * @param markerId A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param point    Location of the marker.
     * @param icon     The icon to display as the marker.
     */
    public MarkerOverlay(String modId, String markerId, BlockPos point, MapImage icon)
    {
        super(modId, markerId);
        setPoint(point);
        setIcon(icon);
    }

    /**
     * Location of the marker.
     */
    public BlockPos getPoint()
    {
        return point;
    }

    /**
     * Sets the location of the marker.
     *
     * @param point location
     * @return this
     */
    public MarkerOverlay setPoint(BlockPos point)
    {
        this.point = point;
        return this;
    }

    /**
     * Icon to display in the marker.
     *
     * @return icon
     */
    public MapImage getIcon()
    {
        return icon;
    }

    /**
     * Sets the icon to display in the marker.
     *
     * @param icon marker image
     * @return this
     */
    public MarkerOverlay setIcon(MapImage icon)
    {
        this.icon = icon;
        return this;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("icon", icon)
                .add("point", point)
                .toString();
    }
}
