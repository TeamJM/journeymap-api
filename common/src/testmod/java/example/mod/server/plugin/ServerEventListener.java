/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.server.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.server.IServerAPI;
import journeymap.api.v2.server.event.ServerEventRegistry;
import journeymap.api.v2.server.event.server.GlobalWaypointEvent;
import journeymap.api.v2.server.event.server.GlobalWaypointGroupEvent;
import journeymap.api.v2.server.event.server.WaypointPendingActionEvent;
import journeymap.api.v2.server.event.server.WaypointPendingReceivedEvent;
import journeymap.api.v2.server.event.server.WaypointShareSubmitEvent;

/**
 * Subscribes to every {@link ServerEventRegistry} event, demonstrating each
 * server-side hook the API exposes.
 */
public final class ServerEventListener
{
    private final IServerAPI jmServerApi;

    public ServerEventListener(IServerAPI jmServerApi)
    {
        this.jmServerApi = jmServerApi;
        ServerEventRegistry.WAYPOINT_SHARE_SUBMIT_EVENT.subscribe(ExampleMod.MODID, this::onWaypointShareSubmit);
        ServerEventRegistry.WAYPOINT_PENDING_RECEIVED_EVENT.subscribe(ExampleMod.MODID, this::onWaypointPendingReceived);
        ServerEventRegistry.WAYPOINT_PENDING_ACTION_EVENT.subscribe(ExampleMod.MODID, this::onWaypointPendingAction);
        ServerEventRegistry.GLOBAL_WAYPOINT_EVENT.subscribe(ExampleMod.MODID, this::onGlobalWaypoint);
        ServerEventRegistry.GLOBAL_WAYPOINT_GROUP_EVENT.subscribe(ExampleMod.MODID, this::onGlobalWaypointGroup);
    }

    private void onWaypointShareSubmit(WaypointShareSubmitEvent event)
    {
        ExampleMod.LOGGER.info("Server: %s shared waypoint '%s' with %d targets",
                event.senderName, event.waypoint.getName(), event.targetIds.size());
    }

    private void onWaypointPendingReceived(WaypointPendingReceivedEvent event)
    {
        ExampleMod.LOGGER.debug("Server: pending waypoint '%s' for %s from %s",
                event.waypoint.getName(), event.recipientUUID, event.senderUUID);
    }

    private void onWaypointPendingAction(WaypointPendingActionEvent event)
    {
        ExampleMod.LOGGER.debug("Server: %s %s pending waypoint '%s'",
                event.playerUUID, event.action, event.waypoint.getName());
    }

    private void onGlobalWaypoint(GlobalWaypointEvent event)
    {
        ExampleMod.LOGGER.debug("Server: GlobalWaypoint %s '%s'",
                event.context, event.waypoint.getName());
    }

    private void onGlobalWaypointGroup(GlobalWaypointGroupEvent event)
    {
        ExampleMod.LOGGER.debug("Server: GlobalWaypointGroup %s '%s'",
                event.context, event.group.getName());
    }
}
