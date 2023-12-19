package journeymap.common.api.waypoint;

import com.google.gson.annotations.Since;

public class WaypointPos
{
    /**
     * The X.
     */
    @Since(1)
    protected int x;

    /**
     * The Y.
     */
    @Since(1)
    protected int y;

    /**
     * The Z.
     */
    @Since(1)
    protected int z;

    public WaypointPos(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getZ()
    {
        return z;
    }

    public void setZ(int z)
    {
        this.z = z;
    }
}
