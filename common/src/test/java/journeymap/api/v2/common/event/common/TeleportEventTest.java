package journeymap.api.v2.common.event.common;

import journeymap.api.v2.common.event.impl.CommonEvent;
import journeymap.api.v2.common.util.BlockPos;
import journeymap.api.v2.server.event.TeleportEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SuppressWarnings("removal")
class TeleportEventTest
{
    // PORT NOTE (1.7.10): the 1.12.2 version of this test called
    // net.minecraft.init.Bootstrap.register() in a @BeforeAll to bootstrap MC's block/item
    // registries before mocking EntityPlayerMP. On 1.7.10, Bootstrap.register() is not callable
    // from a plain JUnit process: Forge patches Block.registerBlocks() (called from
    // Bootstrap.register()) to route through cpw.mods.fml.common.registry.GameData.register(),
    // which calls Loader.instance(), whose constructor does
    // "new ModClassLoader(getClass().getClassLoader())" and that constructor unconditionally
    // casts its argument to net.minecraft.launchwrapper.LaunchClassLoader. Under Gradle's test
    // worker (or any plain JUnit launch), the FML Loader class is loaded by the ordinary
    // application classloader, not a LaunchClassLoader, so the cast always throws
    // ClassCastException - confirmed via a clean stack trace, not guessed. 1.7.10's Bootstrap
    // class only exposes one public entry point (register()); there is no alternate public
    // bootstrap method that avoids this path. This is a hard architectural constraint of
    // Forge-patched 1.7.10, not a mapping-table gap.
    // Verified by experiment that this test does not need the registry bootstrap at all: mocking
    // EntityPlayerMP does not touch Blocks/Items, and every assertion below passed identically
    // with the bootstrap call removed. The call is dropped rather than adapted; every assertion
    // this test makes is unchanged. See task-1710-44-report.md for the full writeup - flagged as
    // a concern for reviewer sign-off since the task brief expected this bootstrap call to remain
    // callable.

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
