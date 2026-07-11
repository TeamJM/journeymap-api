package journeymap.api.v2.common.util;

/**
 * Immutable integer block-coordinate value type.
 * <p>
 * 1.7.10 has no {@code net.minecraft.util.math.BlockPos} class (that type was introduced in a
 * later Minecraft version). This class replaces it so the JourneyMap API surface - and downstream
 * addon source - stays the same shape across Minecraft versions.
 */
public final class BlockPos
{
    private final int x;
    private final int y;
    private final int z;

    /**
     * Constructor.
     *
     * @param x block x coordinate
     * @param y block y coordinate
     * @param z block z coordinate
     */
    public BlockPos(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor. Each component is floored to the containing block coordinate (MC parity).
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public BlockPos(double x, double y, double z)
    {
        this((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    /**
     * Packs a block coordinate into a single {@code long}, matching the format used by
     * {@code net.minecraft.util.math.BlockPos#toLong()} on the Minecraft versions that have it:
     * x in the upper 26 bits, y in the middle 12 bits, z in the lower 26 bits.
     * <p>
     * This is the wire format documented by
     * {@code journeymap.api.v2.server.overlay.OverlayPoints}, so the packed value is identical
     * regardless of which Minecraft version an addon is built against.
     *
     * @param x block x coordinate
     * @param y block y coordinate
     * @param z block z coordinate
     * @return the packed value
     */
    public static long asLong(int x, int y, int z)
    {
        return ((long) x & 0x3FFFFFFL) << 38 | ((long) y & 0xFFFL) << 26 | (long) z & 0x3FFFFFFL;
    }

    /**
     * Packs this position via {@link #asLong(int, int, int)}.
     *
     * @return the packed value
     */
    public long toLong()
    {
        return asLong(x, y, z);
    }

    /**
     * Unpacks a value produced by {@link #asLong(int, int, int)}. Each component is sign-extended
     * from its field width, so negative coordinates round-trip.
     *
     * @param packed a value produced by {@link #asLong(int, int, int)}
     * @return the unpacked position
     */
    public static BlockPos fromLong(long packed)
    {
        int x = (int) (packed << 0 >> 38);
        int y = (int) (packed << 26 >> 52);
        int z = (int) (packed << 38 >> 38);
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof BlockPos))
        {
            return false;
        }
        BlockPos other = (BlockPos) o;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode()
    {
        // MC parity: net.minecraft.util.math.BlockPos#hashCode() on MC 1.8+ is (y + z * 31) * 31 + x.
        return (y + z * 31) * 31 + x;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
