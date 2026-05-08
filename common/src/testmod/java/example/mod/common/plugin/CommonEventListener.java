/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.common.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.common.event.CommonEventRegistry;
import journeymap.api.v2.common.event.common.DeathWaypointEvent;
import journeymap.api.v2.common.event.common.TeleportEvent;
import journeymap.api.v2.common.event.common.WaypointEvent;
import journeymap.api.v2.common.event.common.WaypointGroupEvent;
import journeymap.api.v2.common.event.common.WaypointGroupTransferEvent;

/**
 * Subscribes to every {@link CommonEventRegistry} event. These events fire on
 * both client and server, so this listener is registered from both the client
 * plugin and the server plugin.
 */
public final class CommonEventListener
{
    private CommonEventListener()
    {
    }

    public static void register()
    {
        CommonEventRegistry.WAYPOINT_EVENT.subscribe(ExampleMod.MODID, CommonEventListener::onWaypoint);
        CommonEventRegistry.WAYPOINT_GROUP_EVENT.subscribe(ExampleMod.MODID, CommonEventListener::onWaypointGroup);
        CommonEventRegistry.WAYPOINT_GROUP_TRANSFER_EVENT.subscribe(ExampleMod.MODID, CommonEventListener::onWaypointGroupTransfer);
        CommonEventRegistry.TELEPORT_EVENT.subscribe(ExampleMod.MODID, CommonEventListener::onTeleport);
        CommonEventRegistry.DEATH_WAYPOINT_EVENT.subscribe(ExampleMod.MODID, CommonEventListener::onDeathWaypoint);
    }

    private static void onWaypoint(WaypointEvent event)
    {
        ExampleMod.LOGGER.debug("WaypointEvent context=%s side=%s name=%s",
                event.getContext(), event.getSide(), event.getWaypoint().getName());
    }

    private static void onWaypointGroup(WaypointGroupEvent event)
    {
        ExampleMod.LOGGER.debug("WaypointGroupEvent context=%s name=%s",
                event.getContext(), event.getGroup().getName());
    }

    private static void onWaypointGroupTransfer(WaypointGroupTransferEvent event)
    {
        ExampleMod.LOGGER.debug("WaypointGroupTransferEvent waypoint=%s",
                event.getWaypoint().getName());
    }

    private static void onTeleport(TeleportEvent event)
    {
        ExampleMod.LOGGER.debug("TeleportEvent fromLevel=%s destLevel=%s pos=%s",
                event.getFromLevel().location(), event.getDestinationLevel().location(), event.getPos());
    }

    private static void onDeathWaypoint(DeathWaypointEvent event)
    {
        ExampleMod.LOGGER.debug("Common DeathWaypointEvent at %s in %s",
                event.getLocation(), event.getDimension().location());
    }
}
