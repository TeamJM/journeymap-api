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
