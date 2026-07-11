package journeymap.api.v2.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Guards the packed-long wire format that {@code journeymap.api.v2.server.overlay.OverlayPoints}
 * documents and that the JourneyMap mod uses on both ends (the server packs the points, the client
 * unpacks them). The layout is x in the upper 26 bits, y in the middle 12 bits, z in the lower 26
 * bits, matching {@code net.minecraft.util.math.BlockPos#toLong()} on Minecraft 1.8 through 1.13.
 * Minecraft 1.14 changed its own layout to x, z, y; this API deliberately does not follow that,
 * because the format is a published contract shared with server addons.
 */
class BlockPosTest
{
    @Test
    void asLongUsesTheDocumentedBitLayout()
    {
        // x = 1 at shift 38, y = 2 at shift 26, z = 3 at shift 0. Golden value: changing this
        // constant changes the wire format and silently misplaces every server overlay.
        assertEquals(0x0000004008000003L, BlockPos.asLong(1, 2, 3));
        assertEquals(0L, BlockPos.asLong(0, 0, 0));
        assertEquals(-1L, BlockPos.asLong(-1, -1, -1));
    }

    @Test
    void toLongMatchesAsLong()
    {
        assertEquals(BlockPos.asLong(123456, 70, -654321), new BlockPos(123456, 70, -654321).toLong());
    }

    @Test
    void fromLongRoundTripsPositiveCoordinates()
    {
        assertRoundTrip(0, 0, 0);
        assertRoundTrip(1, 2, 3);
        assertRoundTrip(123456, 70, 654321);
    }

    @Test
    void fromLongRoundTripsNegativeCoordinates()
    {
        assertRoundTrip(-1, -1, -1);
        assertRoundTrip(-123456, 70, -654321);
        assertRoundTrip(-1, 255, -1);
    }

    @Test
    void fromLongRoundTripsTheWorldBorder()
    {
        assertRoundTrip(30000000, 255, -30000000);
        assertRoundTrip(-30000000, 0, 30000000);
    }

    @Test
    void fromLongRoundTripsTheFieldExtremes()
    {
        // 26-bit signed x/z and 12-bit signed y, the full domain the format can represent.
        assertRoundTrip(33554431, 2047, -33554432);
        assertRoundTrip(-33554432, -2048, 33554431);
    }

    private static void assertRoundTrip(int x, int y, int z)
    {
        BlockPos unpacked = BlockPos.fromLong(BlockPos.asLong(x, y, z));
        assertEquals(new BlockPos(x, y, z), unpacked);
        assertEquals(x, unpacked.getX());
        assertEquals(y, unpacked.getY());
        assertEquals(z, unpacked.getZ());
    }
}
