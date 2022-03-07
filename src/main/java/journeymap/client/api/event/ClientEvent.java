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

package journeymap.client.api.event;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Parent class for events propagated by the Client API to IClientPlugin implementations.
 */
public class ClientEvent
{
    /**
     * Event type.
     */
    public final Type type;

    /**
     * World dimension where event occurred.
     */
    public final ResourceKey<Level> dimension;

    /**
     * System millis when event was created.
     */
    public final long timestamp;

    /**
     * Whether event has been cancelled.
     */
    private boolean cancelled;

    /**
     * Constructor.
     */
    public ClientEvent(Type type, ResourceKey<Level> dimension)
    {
        this.type = type;
        this.dimension = dimension;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Constructor.
     */
    public ClientEvent(Type type)
    {
        this(type, Level.OVERWORLD);
    }

    /**
     * Whether the event has been cancelled.
     *
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
        if (isCancellable())
        {
            this.cancelled = true;
        }
    }

    /**
     * Whether the event is cancellable.
     *
     * @return true if cancellable.
     */
    public boolean isCancellable()
    {
        return type.cancellable;
    }

    /**
     * Event type enumeration.
     */
    public enum Type
    {
        /**
         * Indicates a change in the display characteristics of the specified UI.
         * Event will be a {@link DisplayUpdateEvent}, which can not be cancelled.
         */
        DISPLAY_UPDATE(false),

        /**
         * Indicates a Death Waypoint is about to be created for the player.
         * Event will be a {@link DeathWaypointEvent}, which can be cancelled.
         */
        DEATH_WAYPOINT(true),

        /**
         * Indicates JourneyMap has started mapping chunks in the dimension.
         * Event will be a simple {@link ClientEvent}.
         */
        MAPPING_STARTED(false),

        /**
         * Indicates JourneyMap has stopped mapping chunks.  Usually due
         * to player death or disconnect from world.  Event will be a
         * simple {@link ClientEvent}.
         */
        MAPPING_STOPPED(false),

        /**
         * Indicates that the fullscreen map is going to have a mouse click
         * {@link FullscreenMapEvent.ClickEvent.Pre}, which can be cancelled.
         * {@link FullscreenMapEvent.ClickEvent.Post}, which can not be cancelled.
         */
        MAP_CLICKED(true),

        /**
         * Indicates the start of the mouse dragging.
         * {@link FullscreenMapEvent.MouseDraggedEvent.Pre}, which can be cancelled.
         * {@link FullscreenMapEvent.MouseDraggedEvent.Post}, which can not be cancelled.
         */
        MAP_DRAGGED(true),

        /**
         * Indicates moving of the mouse, gets block info where the cursor is pointing.
         * {@link FullscreenMapEvent.MouseMoveEvent}, which can not be cancelled.
         */
        MAP_MOUSE_MOVED(false),

        /**
         * Indicates registry events.
         * {@link RegistryEvent}
         */
        REGISTRY(false),

        /**
         * Waypoint events, includes save, add, update, delete.
         */
        WAYPOINT(false);


        /**
         * Whether the type of event can be cancelled.
         */
        public final boolean cancellable;

        /**
         * Constructor
         *
         * @param cancellable true if the event type can be cancelled.
         */
        Type(boolean cancellable)
        {
            this.cancellable = cancellable;
        }
    }
}
