/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
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

package journeymap.common.api.event.impl;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Parent class for events propagated by the Client API to IClientPlugin implementations.
 */
public abstract class ClientEvent extends JourneyMapEvent
{

    /**
     * World dimension where event occurred.
     */
    public final ResourceKey<Level> dimension;

    /**
     * Whether event has been cancelled.
     */
    private boolean cancelled;

    /**
     * Constructor.
     */
    public ClientEvent(boolean cancellable, ResourceKey<Level> dimension)
    {
        super(cancellable);
        this.dimension = dimension;

    }

    /**
     * Constructor.
     */
    public ClientEvent(boolean cancellable)
    {
        this(cancellable, Level.OVERWORLD);
    }
}
