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

import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

/**
 * Provides IDs and key information for map overlays in JourneyMap.
 */
@Optional.Interface(iface = "journeymap.client.api.overlay.IOverlay", modid = "journeymap")
public class Overlay implements journeymap.client.api.overlay.IOverlay
{
    private final String modId;
    private final String displayId;

    /**
     * Constructor.
     *
     * @param modId     Your mod id.
     * @param displayId A unique id for the displayable item.
     */
    public Overlay(String modId, String displayId)
    {
        Verify.verifyNotNull(modId);
        Verify.verifyNotNull(displayId);
        this.modId = modId;
        this.displayId = displayId;
    }

    /**
     * Your mod id.
     *
     * @return modId
     */
    @Override
    public String getModId()
    {
        return modId;
    }

    /**
     * A unique id for the displayable item. Uniqueness is only needed among items in your mod of the same type;
     * each ImageOverlay should have a unique ID within your mod,
     * each MarkerOverlay should have a unique ID within your mod,
     * etc.
     *
     * @return displayId
     */
    @Override
    public String getDisplayId()
    {
        return displayId;
    }
}
