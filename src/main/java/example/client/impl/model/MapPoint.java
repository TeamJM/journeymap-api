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
 * + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 * + Write your own code that uses, modifies, or extends the example source code in example.* packages
 * + Distribute compiled classes of unmodified API source code in journeymap.* packages
 * + Fork and modify any source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified API source code from journeymap.* packages or compiled classes of modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API source code or example source code in any way not explicitly granted
 *        by this license statement.
 *
 */

package example.client.impl.model;

import com.google.common.base.Objects;
import cpw.mods.fml.common.Optional;

/**
 * A MapPoint is a triple of world coordinates.
 */
@Optional.Interface(iface = "journeymap.client.api.model.IMapPoint", modid = "journeymap")
public final class MapPoint implements journeymap.client.api.model.IMapPoint
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

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    @Override
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
        if (!(o instanceof journeymap.client.api.model.IMapPoint))
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
