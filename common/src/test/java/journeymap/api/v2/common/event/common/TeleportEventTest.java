package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.server.event.TeleportEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.math.BlockPos;
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
        Bootstrap.register();
    }

    @Test
    void constructorIsCancellableAndServerSide()
    {
        EntityPlayerMP player = mock(EntityPlayerMP.class);
        BlockPos pos = new BlockPos(1, 2, 3);
        TeleportEvent event = new TeleportEvent(player, null, pos, 0, 0);
        assertTrue(event.isCancellable());
        assertEquals(CommonEvent.Side.Server, event.getSide());
        assertNull(event.getWaypoint());
        assertSame(player, event.getPlayer());
        assertEquals(pos, event.getPos());
        assertEquals(0, event.getFromLevel());
        assertEquals(0, event.getDestinationLevel());
    }
}
