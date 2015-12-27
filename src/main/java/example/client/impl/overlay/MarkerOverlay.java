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

package example.client.impl.overlay;

import com.google.common.base.Objects;
import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

/**
 * A Marker overlat shows a geographical point on the map with an icon and a label.  For example: The spawn point.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "journeymap.client.api.overlay.IMarkerOverlay", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.overlay.IOverlayProperties", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapPoint", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapIcon", modid = "journeymap")
})
public final class MarkerOverlay extends Overlay implements journeymap.client.api.overlay.IMarkerOverlay
{
    private final journeymap.client.api.model.IMapPoint point;
    private final journeymap.client.api.model.IMapIcon icon;
    private final journeymap.client.api.overlay.IOverlayProperties overlayProperties;

    /**
     * Constructor.
     * @param modId    Your mod id.
     * @param markerId A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param point    Location of the marker.
     * @param icon     The icon to display as the marker.
     * @param overlayProperties Common overlay characteristics.
     */
    public MarkerOverlay(String modId, String markerId, journeymap.client.api.model.IMapPoint point,
                         journeymap.client.api.model.IMapIcon icon, journeymap.client.api.overlay.IOverlayProperties overlayProperties)
    {
        super(modId, markerId);
        Verify.verifyNotNull(markerId);
        Verify.verifyNotNull(icon);
        Verify.verifyNotNull(point);
        Verify.verifyNotNull(overlayProperties);
        this.icon = icon;
        this.point = point;
        this.overlayProperties = overlayProperties;
    }

    /**
     * Location of the marker.
     */
    @Override
    public journeymap.client.api.model.IMapPoint getPoint()
    {
        return point;
    }

    /**
     * Icon to display in the marker.
     *
     * @return icon
     */
    @Override
    public journeymap.client.api.model.IMapIcon getIcon()
    {
        return icon;
    }

    /**
     * The overlay properties.
     *
     * @return properties
     */
    @Override
    public journeymap.client.api.overlay.IOverlayProperties getOverlayProperties()
    {
        return overlayProperties;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("markerId", getDisplayId())
                .add("point", point)
                .add("icon", icon)
                .add("overlayProperties", overlayProperties)
                .toString();
    }
}
