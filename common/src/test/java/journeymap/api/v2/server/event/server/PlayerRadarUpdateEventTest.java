package journeymap.api.v2.server.event.server;

import journeymap.api.v2.server.event.PlayerRadarUpdateEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerRadarUpdateEventTest
{
    @Test
    void updateIsCancellableAndVisibleIsMutable()
    {
        UUID remoteId = UUID.randomUUID();
        PlayerRadarUpdateEvent event = new PlayerRadarUpdateEvent(null, remoteId, null,
                PlayerRadarUpdateEvent.Action.UPDATE, true, false);

        assertTrue(event.isCancellable());
        assertEquals(PlayerRadarUpdateEvent.Action.UPDATE, event.getAction());
        assertTrue(event.isVisible());

        event.setVisible(false);
        assertFalse(event.isVisible());
    }

    @Test
    void removeIsCancellable()
    {
        UUID remoteId = UUID.randomUUID();
        PlayerRadarUpdateEvent event = new PlayerRadarUpdateEvent(null, remoteId, null,
                PlayerRadarUpdateEvent.Action.REMOVE, false, false);

        assertTrue(event.isCancellable());
        assertEquals(PlayerRadarUpdateEvent.Action.REMOVE, event.getAction());
        assertNull(event.getRemote());
        assertEquals(remoteId, event.getRemoteId());
    }
}
