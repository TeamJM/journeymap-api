/*
 * JourneyMap API (http://journeymap.info)
 * http://bitbucket.org/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package journeymap.client.api.model;

import com.google.common.base.Objects;

/**
 * A MapPoint is a triple of world coordinates.  It is immutable.
 */
public final class MapPoint
{
    private final int x;
    private final int y;
    private final int z;

    /**
     * Constructor.
     *
     * @param x world x
     * @param y world y
     * @param z world z
     */
    public MapPoint(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public MapPoint offset(int x, int z)
    {
        return new MapPoint(x, 0, z);
    }

    public MapPoint offset(int x, int y, int z)
    {
        return new MapPoint(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        MapPoint mapPoint = (MapPoint) o;
        return Objects.equal(x, mapPoint.x) &&
                Objects.equal(y, mapPoint.y) &&
                Objects.equal(z, mapPoint.z);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(x, y, z);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .add("z", z)
                .toString();
    }


}
