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
 *   + Write your own code that uses this API source code as a dependency.
 *   + Distribute compiled classes of unmodified API source code which your code depends upon.
 *   + Fork and modify API source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified versions of the API source code or compiled artifacts of  modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API code in any way not explicitly granted by this license statement.
 *
 */

package journeymap.client.api.map;

import com.google.common.base.Objects;
import com.google.common.base.Verify;

/**
 * A Marker overlat shows a geographical point on the map with an icon and a label.  For example: The spawn point.
 */
public final class MarkerOverlay extends OverlayBase
{
    private String markerId;
    private MapPoint point;
    private MapIcon icon;

    /**
     * Constructor.
     *
     * @param markerId A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param point    Location of the marker.
     * @param icon     The icon to display as the marker.
     */
    public MarkerOverlay(String markerId, MapPoint point, MapIcon icon)
    {
        this(markerId, null, point, null, null, icon);
    }

    /**
     * Constructor.
     *
     * @param markerId         A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param overlayGroupName A suggested group or category name used to organize map overlays.
     * @param point            Location of the marker.
     * @param title            (Optional) Rollover text to be displayed when the mouse is over the overlay.
     * @param label            (Optional) Label text to be displayed on the overlay.
     * @param icon             The icon to display as the marker.
     */
    public MarkerOverlay(String markerId, String overlayGroupName, MapPoint point, String title, String label, MapIcon icon)
    {
        Verify.verifyNotNull(markerId);
        Verify.verifyNotNull(icon);
        Verify.verifyNotNull(point);
        this.markerId = markerId;
        this.icon = icon;
        this.point = point;
        super.setOverlayGroupName(overlayGroupName);
        super.setTitle(title);
        super.setLabel(label);
    }

    /**
     * A unique id for the marker which can be used to remove/update it.
     */
    public String getMarkerId()
    {
        return markerId;
    }

    /**
     * Location of the marker.
     */
    public MapPoint getPoint()
    {
        return point;
    }

    /**
     * Icon to display in the marker.
     *
     * @return icon
     */
    public MapIcon getIcon()
    {
        return icon;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("markerId", markerId)
                .add("point", point)
                .add("icon", icon)
                .add("label", label)
                .add("title", title)
                .add("overlayGroupName", overlayGroupName)
                .add("color", color)
                .add("inFullscreen", inFullscreen)
                .add("inMinimap", inMinimap)
                .add("inWebmap", inWebmap)
                .add("maxZoom", maxZoom)
                .add("minZoom", minZoom)
                .add("zIndex", zIndex)
                .toString();
    }
}
