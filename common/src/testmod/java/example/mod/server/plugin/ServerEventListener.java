/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.server.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.common.event.ServerEventRegistry;
import journeymap.api.v2.server.IServerAPI;
import journeymap.api.v2.server.event.GlobalWaypointEvent;
import journeymap.api.v2.server.event.GlobalWaypointGroupEvent;
import journeymap.api.v2.server.event.ServerOptionsRegistryEvent;
import journeymap.api.v2.server.event.TeleportEvent;
import journeymap.api.v2.server.event.WaypointPendingActionEvent;
import journeymap.api.v2.server.event.WaypointPendingReceivedEvent;
import journeymap.api.v2.server.event.WaypointShareSubmitEvent;
import journeymap.api.v2.server.overlay.IServerOverlayAPI;
import journeymap.api.v2.server.overlay.ServerPolygon;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

/**
 * Subscribes to every {@link ServerEventRegistry} event, demonstrating each
 * server-side hook the API exposes.
 */
public final class ServerEventListener
{
    private final IServerAPI jmServerApi;
    private ServerProperties serverProperties;

    public ServerEventListener(IServerAPI jmServerApi)
    {
        this.jmServerApi = jmServerApi;
        ServerEventRegistry.WAYPOINT_SHARE_SUBMIT_EVENT.subscribe(ExampleMod.MODID, this::onWaypointShareSubmit);
        ServerEventRegistry.WAYPOINT_PENDING_RECEIVED_EVENT.subscribe(ExampleMod.MODID, this::onWaypointPendingReceived);
        ServerEventRegistry.WAYPOINT_PENDING_ACTION_EVENT.subscribe(ExampleMod.MODID, this::onWaypointPendingAction);
        ServerEventRegistry.GLOBAL_WAYPOINT_EVENT.subscribe(ExampleMod.MODID, this::onGlobalWaypoint);
        ServerEventRegistry.GLOBAL_WAYPOINT_GROUP_EVENT.subscribe(ExampleMod.MODID, this::onGlobalWaypointGroup);
        ServerEventRegistry.TELEPORT_EVENT.subscribe(ExampleMod.MODID, this::onTeleport);
        ServerEventRegistry.OPTIONS_REGISTRY_EVENT.subscribe(ExampleMod.MODID, this::onOptionsRegistry);

    }

    public ServerProperties getServerProperties()
    {
        return serverProperties;
    }

    private void onOptionsRegistry(ServerOptionsRegistryEvent event)
    {
        this.serverProperties = new ServerProperties();
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

    private void onTeleport(TeleportEvent event)
    {
        ExampleMod.LOGGER.debug("Common TeleportEvent fromLevel=%s destLevel=%s pos=%s",
                event.getFromLevel().identifier(), event.getDestinationLevel().identifier(), event.getPos());

        // Demonstrate the server overlay API: push a sample claim around the
        // teleport destination so the player sees an overlay the moment they
        // land. Re-teleporting to the same overlayId atomically replaces the
        // prior version on the client.
        pushSampleClaim(event.getPlayer(), event.getPos());
    }

    // ---- Server overlay API demo ----
    //
    // The server overlay API is reached via IServerAPI#getOverlayApi(). The
    // calls below show the full surface: push, replace, remove, clear. The
    // example mod does not auto-wire these to a player-join event because the
    // available loader-agnostic events do not carry the joining ServerPlayer;
    // wire them from your loader entry point or a relevant event of your own.

    /**
     * Demo: push a sample square overlay around the given block to a single player.
     * Calling again with the same {@code overlayId} (used as both the visible label
     * and the wire-level handle) replaces the prior version on the client.
     *
     * @param player  the recipient
     * @param centre  the block to centre the square on
     */
    public void pushSampleClaim(ServerPlayer player, BlockPos centre)
    {
        IServerOverlayAPI overlayApi = jmServerApi.getOverlayApi();
        ServerPolygon polygon = SampleServerPolygonOverlayFactory.createSampleSquare(
                "sample-claim", player.level().dimension(), centre, 32);
        overlayApi.show(player, ExampleMod.MODID, polygon);
        ExampleMod.LOGGER.info("Pushed sample claim to %s at %s", player.getName().getString(), centre);
    }

    /**
     * Demo: remove the sample square overlay (by its addon-stable id).
     */
    public void clearSampleClaim(ServerPlayer player)
    {
        jmServerApi.getOverlayApi().remove(player, ExampleMod.MODID, "sample-claim");
    }

    /**
     * Demo: clear every overlay this addon has shown to the player.
     * Useful on logout or a "reset everything" path.
     */
    public void clearAllOverlaysForPlayer(ServerPlayer player)
    {
        jmServerApi.getOverlayApi().clearAll(player, ExampleMod.MODID);
    }
}
