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

/**
 * Provides IDs needed to display map overlays in JourneyMap.
 */
public interface IOverlay
{
    /**
     * Your mod id.
     *
     * @return modId
     */
    String getModId();

    /**
     * A unique id for the displayable item. Uniqueness is only needed among items in your mod of the same type;
     * each ImageOverlay should have a unique ID within your mod,
     * each MarkerOverlay should have a unique ID within your mod,
     * etc.
     *
     * @return displayId
     */
    String getDisplayId();
}
