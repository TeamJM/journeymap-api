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

package journeymap.client.api.overlay;

import journeymap.client.api.model.IMapIcon;
import journeymap.client.api.model.IMapPoint;

/**
 * A Marker overlay shows a geographical point on the map with an icon and a label.  For example: The spawn point.
 */
public interface IMarkerOverlay extends IOverlay
{
    /**
     * Location of the marker.
     */
    IMapPoint getPoint();

    /**
     * Icon to display in the marker.
     *
     * @return icon
     */
    IMapIcon getIcon();

    /**
     * The overlay properties.
     *
     * @return properties
     */
    IOverlayProperties getOverlayProperties();
}
