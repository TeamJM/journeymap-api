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

package journeymap.client.api.overlay;

import journeymap.client.api.model.IMapIcon;
import journeymap.client.api.model.IMapPoint;

/**
 * An image overlay scales an image on the map between the given coordinates.
 */
public interface IImageOverlay extends IOverlay
{
    /**
     * Top-left location of the image overlay.
     */
    IMapPoint getNorthWestPoint();

    /**
     * Bottom-right location of the image overlay.
     */
    IMapPoint getSouthEastPoint();

    /**
     * Image to display as the overlay.
     *
     * @return icon
     */
    IMapIcon getImage();

    /**
     * Common overlay characteristics.
     *
     * @return properties
     */
    IOverlayProperties getOverlayProperties();
}
