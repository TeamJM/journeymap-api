package journeymap.api.v2.server.event;

import journeymap.api.v2.common.event.impl.CommonEvent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Fired on the server for player radar visibility decisions.
 * <p>
 * UPDATE fires once per (receiver, remote-player) pair on each radar broadcast tick,
 * before the {@code PlayerLoc} is constructed. Listeners may mutate {@link #setVisible}.
 * Cancel = treat as {@code visible=false}.
 * <p>
 * REMOVE fires once per online receiver when a player disconnects or unloads, before
 * the {@code RemovePlayerPacket} is sent. Cancel = skip sending to this receiver.
 */
public class PlayerRadarUpdateEvent extends CommonEvent
{
    private final ServerPlayer receiver;
    private final UUID remoteId;
    @Nullable
    private final ServerPlayer remote;
    private final Action action;
    private boolean visible;
    private final boolean receiverOp;

    public PlayerRadarUpdateEvent(ServerPlayer receiver,
                                  UUID remoteId,
                                  @Nullable ServerPlayer remote,
                                  Action action,
                                  boolean visible,
                                  boolean receiverOp)
    {
        super(true, Side.Server);
        this.receiver = receiver;
        this.remoteId = remoteId;
        this.remote = remote;
        this.action = action;
        this.visible = visible;
        this.receiverOp = receiverOp;
    }

    public ServerPlayer getReceiver()
    {
        return receiver;
    }

    public UUID getRemoteId()
    {
        return remoteId;
    }

    @Nullable
    public ServerPlayer getRemote()
    {
        return remote;
    }

    public Action getAction()
    {
        return action;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public boolean isReceiverOp()
    {
        return receiverOp;
    }

    public enum Action
    {
        UPDATE,
        REMOVE
    }
}
