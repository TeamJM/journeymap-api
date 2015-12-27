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

package example.client.impl.overlay;

import com.google.common.base.Objects;
import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * A polygon overlay displays a geometric area on a map.  For example: An area of claimed chunks.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "journeymap.client.api.overlay.IPolygonOverlay", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.overlay.IOverlayProperties", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapPolygon", modid = "journeymap")
})
public final class PolygonOverlay extends Overlay implements journeymap.client.api.overlay.IPolygonOverlay
{
    private journeymap.client.api.model.IMapPolygon outerArea;
    private List<journeymap.client.api.model.IMapPolygon> holes;
    private float strokeWidth = 2;
    private int strokeColor = 0xffffff;
    private float strokeOpacity = 1;
    private int fillColor = 0x000000;
    private float fillOpacity = 0.5f;
    private journeymap.client.api.overlay.IOverlayProperties overlayProperties;

    /**
     * Constructor.
     *
     * @param modId            Your mod id.
     * @param polygonId        A unique id for the polygon (scoped within your mod) which can be used to remove/update it.
     * @param outerArea        A polygon of the outer area to be displayed.
     * @param overlayProperties  Common overlay properties.
     */
    public PolygonOverlay(String modId, String polygonId, journeymap.client.api.model.IMapPolygon outerArea,
                          journeymap.client.api.overlay.IOverlayProperties overlayProperties)
    {
        this(modId, polygonId, outerArea, null, overlayProperties);
    }

    /**
     * Constructor.
     *
     * @param modId            Your mod id.
     * @param polygonId        A unique id for the polygon (scoped within your mod) which can be used to remove/update it.
     * @param outerArea        A polygon of the outer area to be displayed.
     * @param holes            (Optional) A list of polygons treated as holes inside the outerArea
     * @param overlayProperties  Common overlay properties.
     */
    public PolygonOverlay(String modId, String polygonId, journeymap.client.api.model.IMapPolygon outerArea,
                          List<journeymap.client.api.model.IMapPolygon> holes,
                          journeymap.client.api.overlay.IOverlayProperties overlayProperties)
    {
        super(modId, polygonId);
        Verify.verifyNotNull(outerArea);
        Verify.verifyNotNull(overlayProperties);
        this.outerArea = outerArea;
        this.holes = new ArrayList<journeymap.client.api.model.IMapPolygon>(holes);
        this.overlayProperties = overlayProperties;
    }

    /**
     * Sets the display characteristics of the polygon
     *
     * @param strokeWidth   Line thickness of the polygon edges.
     * @param strokeColor   Line color (rgb) of the polygon edges.
     * @param strokeOpacity Line opacity (between 0 and 1) of the polygon edges.
     * @param fillColor     Fill color (rgb) of the polygon.
     * @param fillOpacity   Fill opacity (between 0 and 1) of the polygon area.
     */
    public void setStyle(float strokeWidth, int strokeColor, float strokeOpacity, int fillColor, float fillOpacity)
    {
        setStrokeWidth(strokeWidth);
        setStrokeColor(strokeColor);
        setStrokeOpacity(strokeOpacity);
        setFillColor(fillColor);
        setFillOpacity(fillOpacity);
    }

    /**
     * A polygon of the outer area to be displayed.
     *
     * @return the outer area
     */
    @Override
    public journeymap.client.api.model.IMapPolygon getOuterArea()
    {
        return outerArea;
    }

    /**
     * (optional) A list of polygons treated as holes inside the outerArea
     *
     * @return the holes
     */
    @Override
    public List<journeymap.client.api.model.IMapPolygon> getHoles()
    {
        return holes;
    }

    /**
     * Line thickness of the polygon edges.
     *
     * @return the stroke width
     */
    @Override
    public float getStrokeWidth()
    {
        return strokeWidth;
    }

    /**
     * Sets stroke width.
     *
     * @param strokeWidth the stroke width
     */
    public void setStrokeWidth(float strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }

    /**
     * Line color (rgb) of the polygon edges.
     *
     * @return the stroke color
     */
    @Override
    public int getStrokeColor()
    {
        return strokeColor;
    }

    /**
     * Sets stroke color.
     *
     * @param strokeColor the stroke color
     */
    public void setStrokeColor(int strokeColor)
    {
        this.strokeColor = Math.max(0x000000, Math.min(strokeColor, 0xffffff));
    }

    /**
     * Line opacity (between 0 and 1) of the polygon edges.
     *
     * @return the stroke opacity
     */
    @Override
    public float getStrokeOpacity()
    {
        return strokeOpacity;
    }

    /**
     * Sets stroke opacity.
     *
     * @param strokeOpacity the stroke opacity
     */
    public void setStrokeOpacity(float strokeOpacity)
    {
        this.strokeOpacity = Math.max(0, Math.min(strokeOpacity, 1));
    }

    /**
     * Fill color (rgb) of the polygon.
     *
     * @return the fill color
     */
    @Override
    public int getFillColor()
    {
        return fillColor;
    }

    /**
     * Sets fill color.
     *
     * @param fillColor the fill color
     */
    public void setFillColor(int fillColor)
    {
        this.fillColor = Math.max(0x000000, Math.min(fillColor, 0xffffff));
    }

    /**
     * Fill opacity of the polygon (between 0 and 1).
     *
     * @return the fill opacity
     */
    @Override
    public float getFillOpacity()
    {
        return fillOpacity;
    }

    /**
     * Sets fill opacity (between 0 and 1).
     *
     * @param fillOpacity the fill opacity
     */
    public void setFillOpacity(float fillOpacity)
    {
        this.fillOpacity = Math.max(0, Math.min(fillOpacity, 1));
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("polygonId", getDisplayId())
                .add("fillColor", fillColor)
                .add("fillOpacity", fillOpacity)
                .add("holes", holes)
                .add("outerArea", outerArea)
                .add("strokeColor", strokeColor)
                .add("strokeOpacity", strokeOpacity)
                .add("strokeWidth", strokeWidth)
                .add("overlayProperties", overlayProperties)
                .toString();
    }
}
