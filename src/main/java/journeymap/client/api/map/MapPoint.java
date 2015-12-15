/*
 *
 * JourneyMap API
 * http://journeymap.info
 * http://bitbucket.org/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2015 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *   + Write your own code that uses this API source code as a dependency.
 *   + Distribute compiled classes of unmodified API source code which your code depends upon.
 *   + Fork and modify API source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified versions of the API source code or compiled artifacts of  modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API code in any way not explicitly granted by this license statement.
 *
 */

package journeymap.client.api.map;

import com.google.common.base.Objects;

/**
 * A MapPoint is a triple of world coordinates.
 */
public class MapPoint
{
    private int x;
    private int y;
    private int z;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MapPoint))
        {
            return false;
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
