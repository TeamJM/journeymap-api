package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.server.event.TeleportEvent;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SuppressWarnings("removal")
class TeleportEventTest
{
    @BeforeAll
    static void bootstrapMinecraft()
    {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void constructorIsCancellableAndServerSide()
    {
        ServerPlayer player = mock(ServerPlayer.class);
        BlockPos pos = new BlockPos(1, 2, 3);
        var event = new TeleportEvent(player, null, pos, Level.OVERWORLD, Level.OVERWORLD);
        assertTrue(event.isCancellable());
        assertEquals(CommonEvent.Side.Server, event.getSide());
        assertNull(event.getWaypoint());
        assertSame(player, event.getPlayer());
        assertEquals(pos, event.getPos());
        assertEquals(Level.OVERWORLD, event.getFromLevel());
        assertEquals(Level.OVERWORLD, event.getDestinationLevel());
    }
}
