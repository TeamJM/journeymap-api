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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import journeymap.client.api.display.Displayable;

/**
 * Properties defining the display of a shape.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 */
public class ShapeProperties
{
    private int strokeColor = 0x000000;
    private int fillColor = 0x000000;
    private float strokeOpacity = 1f;
    private float fillOpacity = .5f;
    private float strokeWidth = 2;

    /**
     * Gets the stroke color.
     *
     * @return rgb strokeColor
     */
    public int getStrokeColor()
    {
        return strokeColor;
    }

    /**
     * Sets the stroke Color (rgb).  Range is 0x000000 - 0xffffff.
     *
     * @param strokeColor rgb
     * @return this
     */
    public ShapeProperties setStrokeColor(int strokeColor)
    {
        this.strokeColor = Displayable.clampRGB(strokeColor);
        return this;
    }

    /**
     * Gets stroke Color. Range is 0x000000 - 0xffffff.
     *
     * @return the fill color
     */
    public int getFillColor()
    {
        return fillColor;
    }

    /**
     * Sets fill color. Range is 0x000000 - 0xffffff.
     *
     * @param fillColor the fill color
     * @return this
     */
    public ShapeProperties setFillColor(int fillColor)
    {
        this.fillColor = Displayable.clampRGB(fillColor);
        return this;
    }

    /**
     * Gets stroke opacity. Range is 0f - 1f.
     *
     * @return the stroke opacity
     */
    public float getStrokeOpacity()
    {
        return strokeOpacity;
    }

    /**
     * Sets stroke opacity. Range is 0f - 1f.
     *
     * @param strokeOpacity the stroke opacity
     * @return this
     */
    public ShapeProperties setStrokeOpacity(float strokeOpacity)
    {
        this.strokeOpacity = Displayable.clampOpacity(strokeOpacity);
        return this;
    }

    /**
     * Gets fill opacity. Range is 0f - 1f.
     *
     * @return the fill opacity
     */
    public float getFillOpacity()
    {
        return fillOpacity;
    }

    /**
     * Sets fillOpacity.  Range is 0f - 1f.
     *
     * @param fillOpacity the fillOpacity
     * @return this
     */
    public ShapeProperties setFillOpacity(float fillOpacity)
    {
        this.fillOpacity = Displayable.clampOpacity(fillOpacity);
        return this;
    }

    /**
     * Gets the stroke width.
     *
     * @return width
     */
    public float getStrokeWidth()
    {
        return strokeWidth;
    }

    /**
     * Sets the stroke width.
     *
     * @param strokeWidth width
     * @return this
     */
    public ShapeProperties setStrokeWidth(float strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ShapeProperties))
        {
            return false;
        }
        ShapeProperties that = (ShapeProperties) o;
        return Objects.equal(strokeColor, that.strokeColor) &&
                Objects.equal(fillColor, that.fillColor) &&
                Objects.equal(strokeOpacity, that.strokeOpacity) &&
                Objects.equal(fillOpacity, that.fillOpacity) &&
                Objects.equal(strokeWidth, that.strokeWidth);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(strokeColor, fillColor, strokeOpacity, fillOpacity, strokeWidth);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("fillColor", fillColor)
                .add("fillOpacity", fillOpacity)
                .add("strokeColor", strokeColor)
                .add("strokeOpacity", strokeOpacity)
                .add("strokeWidth", strokeWidth)
                .toString();
    }


}
