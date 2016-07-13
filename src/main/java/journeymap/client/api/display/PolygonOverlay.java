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

package journeymap.client.api.display;

import journeymap.client.api.model.MapPolygon;
import journeymap.client.api.model.ShapeProperties;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * A polygon overlay displays a geometric area on a map.  For example: An area of claimed chunks.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 * <p>
 * Note that like all Displayables, simply changing this object doesn't guarantee the player will get the changes.
 * You must call {@link journeymap.client.api.IClientAPI#show(Displayable)} in order for the changes to take effect
 * in JourneyMap.
 */
@ParametersAreNonnullByDefault
public final class PolygonOverlay extends Overlay
{
    private MapPolygon outerArea;
    private List<MapPolygon> holes;
    private ShapeProperties shapeProperties;

    /**
     * Constructor.
     *
     * @param modId     Your mod id.
     * @param displayId A unique id for the polygon (scoped within your mod) which can be used to remove/update it.
     * @param outerArea A polygon of the outer area to be displayed.
     */
    public PolygonOverlay(String modId, String displayId, int dimension, ShapeProperties shapeProperties, MapPolygon outerArea)
    {
        this(modId, displayId, dimension, shapeProperties, outerArea, null);
    }

    /**
     * Constructor.
     *
     * @param modId     Your mod id.
     * @param displayId A unique id for the polygon (scoped within your mod) which can be used to remove/update it.
     * @param outerArea A polygon of the outer area to be displayed.
     * @param holes     (Optional) A list of polygons treated as holes inside the outerArea
     */
    public PolygonOverlay(String modId, String displayId, int dimension, ShapeProperties shapeProperties, MapPolygon outerArea, @Nullable List<MapPolygon> holes)
    {
        super(modId, displayId);
        setDimension(dimension);
        setShapeProperties(shapeProperties);
        setOuterArea(outerArea);
        setHoles(holes);
    }

    /**
     * A polygon of the outer area to be displayed.
     *
     * @return the outer area
     */
    public MapPolygon getOuterArea()
    {
        return outerArea;
    }

    /**
     * Sets the polygon of the outer area to be displayed.
     *
     * @param outerArea polygon
     * @return this
     */
    public PolygonOverlay setOuterArea(MapPolygon outerArea)
    {
        this.outerArea = outerArea;
        return this;
    }

    /**
     * (optional) A list of polygons treated as holes inside the outerArea
     *
     * @return null if none specified
     */
    public List<MapPolygon> getHoles()
    {
        return holes;
    }

    /**
     * Sets a list of polygons treated as holes inside the outerArea
     *
     * @param holes polygons
     * @return this
     */
    public PolygonOverlay setHoles(@Nullable List<MapPolygon> holes)
    {
        if (holes == null)
        {
            this.holes = null;
        }
        else
        {
            this.holes = new ArrayList<MapPolygon>(holes);
        }
        return this;
    }

    /**
     * Gets the shape properties used to display the polygons.
     *
     * @return properties
     */
    public ShapeProperties getShapeProperties()
    {
        return shapeProperties;
    }

    /**
     * Sets the shape properties used to display the polygons.
     *
     * @param shapeProperties properties
     * @return this
     */
    public PolygonOverlay setShapeProperties(ShapeProperties shapeProperties)
    {
        this.shapeProperties = shapeProperties;
        return this;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("holes", holes)
                .add("outerArea", outerArea)
                .add("shapeProperties", shapeProperties)
                .toString();
    }
}
