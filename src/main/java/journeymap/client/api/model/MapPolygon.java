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
import net.minecraft.util.BlockPos;

import java.util.*;

/**
 * A MapPolygon is a sequence of at least 4 BlockPoss, the first and last being equal.
 * <p/>
 * Note that the actual list passed into the constructor isn't retained; the points
 * are copied into an unmodifiable list. If you need to update the points, pass in a new
 * list entirely.
 * <p/>
 * Setters use the Builder pattern so they can be chained.
 */
public final class MapPolygon
{
    private List<BlockPos> points;

    /**
     * Constructor.
     *
     * @param points a sequence of at least 4 BlockPoss, the first and last being equal.
     * @throws IllegalArgumentException if conditions for a proper polygon aren't met.
     */
    public MapPolygon(BlockPos... points)
    {
        this(Arrays.asList(points));
    }

    /**
     * Constructor.
     *
     * @param points a sequence of at least 4 BlockPoss, the first and last being equal.
     * @throws IllegalArgumentException if conditions for a proper polygon aren't met.
     */
    public MapPolygon(List<BlockPos> points)
    {
        setPoints(points);
    }

    /**
     * Gets an unmodifiable list of the points.
     *
     * @return points
     */
    public List<BlockPos> getPoints()
    {
        return points;
    }

    public MapPolygon setPoints(List<BlockPos> points)
    {
        if (points.size() < 4)
        {
            throw new IllegalArgumentException("MapPolygon must have at least 4 points.");
        }

        if (!points.get(0).equals(points.get(points.size() - 1)))
        {
            // First and last points must be equal
            List<BlockPos> temp = new ArrayList<BlockPos>(points);
            temp.add(points.get(0));
            points = temp;
        }

        this.points = Collections.unmodifiableList(points);
        return this;
    }

    /**
     * Iterates the points.
     *
     * @return iterator
     */
    public Iterator<BlockPos> iterator()
    {
        return points.iterator();
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("points", points)
                .toString();
    }
}
