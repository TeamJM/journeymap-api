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
import net.minecraft.util.BlockPos;

import javax.annotation.Nullable;

/**
 * Event propagated by the Client API to IClientPlugin implementations.
 */
public class ClientEvent<V>
{
    public final Type type;
    public final int dimension;
    public final V value;

    private boolean cancelled;

    /**
     * Private constructor.
     */
    private ClientEvent(Type type, int dimension, @Nullable V value)
    {
        this.type = type;
        this.dimension = dimension;
        this.value = value;
    }

    /**
     * Factory method to generate a Display Started event.
     *
     * @param dimension the dimension
     * @return new event
     */
    public static ClientEvent<Void> newDisplayStarted(int dimension)
    {
        return new ClientEvent<Void>(Type.DISPLAY_STARTED, dimension, null);
    }

    /**
     * Factory method to generate a Death Waypoint event.
     *
     * @return new event
     */
    public static ClientEvent<BlockPos> newDeathWaypoint(int dimension, BlockPos location)
    {
        return new ClientEvent<BlockPos>(Type.DEATH_WAYPOINT, dimension, location);
    }

    /**
     * Whether the event has been cancelled.
     * @return true if cancelled
     */
    public boolean isCancelled()
    {
        return cancelled;
    }

    /**
     * Cancels the event only if the Type is cancellable.
     */
    public void cancel()
    {
        if(type.cancellable)
        {
            this.cancelled = true;
        }
    }

    /**
     * Event type
     */
    public enum Type
    {
        /**
         * Signal for ClientPlugins to {@link journeymap.client.api.IClientAPI#show(Displayable)} its
         * Displayables for the {@link #dimension} indicated. (ModWaypoints with persisted==true will already be shown.)
         * The {@link #value} field is always null.  Cannot be cancelled.
         */
        DISPLAY_STARTED(false, Void.TYPE),

        /**
         * Signal for ClientPlugins that JourneyMap is going to create a Death Waypoint for the player.
         * The {@link #value} field will be a {@link net.minecraft.util.BlockPos} with the
         * appropriate coordinates set.  Event can be cancelled, preventing the Death Waypoint from being created.
         */
        DEATH_WAYPOINT(true, BlockPos.class);

        /**
         * Whether the type of event can be cancelled.
         */
        public final boolean cancellable;

        /**
         * The class of the expected value object. If the value is always null, this will be Class<Void>.
         */
        public final Class valueClass;

        /**
         * Constructor
         *
         * @param cancellable true if the event type can be cancelled.
         */
        Type(boolean cancellable, Class valueClass)
        {
            this.cancellable = cancellable;
            this.valueClass = valueClass;
        }
    }
}
