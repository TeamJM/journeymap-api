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

package example.client.impl.model;

import com.google.common.base.Objects;
import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A MapPolygon is a sequence of at least 4 MapPoints, the first and last being equal.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "journeymap.client.api.model.IMapPolygon", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapPoint", modid = "journeymap")
})
public final class MapPolygon implements journeymap.client.api.model.IMapPolygon
{
    private final ArrayList<journeymap.client.api.model.IMapPoint> points;

    /**
     * Constructor.
     *
     * @param pointsList a sequence of at least 4 MapPoints, the first and last being equal.
     * @throws IllegalArgumentException if conditions for a proper polygon aren't met.
     */
    public MapPolygon(List<journeymap.client.api.model.IMapPoint> pointsList)
    {
        Verify.verifyNotNull(pointsList);
        if (pointsList.size() < 4)
        {
            throw new IllegalArgumentException("IMapPolygon must have at least 4 points.");
        }
        if (!pointsList.get(0).equals(pointsList.get(pointsList.size() - 1)))
        {
            throw new IllegalArgumentException("IMapPolygon must have equal first and last points.");
        }

        this.points = new ArrayList<journeymap.client.api.model.IMapPoint>(pointsList);
    }

    /**
     * Iterates the points.
     *
     * @return iterator
     */
    @Override
    public Iterator<journeymap.client.api.model.IMapPoint> iterator()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("points", points)
                .toString();
    }
}
