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

package journeymap.client.api.event;

import journeymap.client.api.display.Displayable;

/**
 * Event propagated by the Client API to IClientPlugin implementations.
 */
public class ClientEvent
{
    public final Type type;
    public int dimension;
    public Object value;

    public ClientEvent(Type type, int dimension)
    {
        this.type = type;
        this.dimension = dimension;
    }

    public ClientEvent(Type type, int dimension, Object value)
    {
        this.type = type;
        this.dimension = dimension;
    }

    public enum Type
    {
        /**
         * Signal for ClientPlugins to {@link journeymap.client.api.IClientAPI#show(Displayable)} its
         * Displayables for the {@link #dimension} indicated. (ModWaypoints with persisted==true will already be shown.)
         * The {@link #value} field is always null.
         */
        DISPLAY_STARTED
    }
}
