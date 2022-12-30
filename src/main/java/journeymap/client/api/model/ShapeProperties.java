/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
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
import com.mojang.blaze3d.platform.NativeImage;
import journeymap.client.api.display.Displayable;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

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
    private NativeImage image;
    private ResourceLocation imageLocation;
    private double texturePositionX = 0;
    private double texturePositionY = 0;
    private double textureScaleX = 1;
    private double textureScaleY = 1;

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

    /**
     * Gets the image, if there is one.
     *
     * @return the image
     */
    @Nullable
    public NativeImage getImage()
    {
        return image;
    }

    /**
     * Sets the image.
     *
     * @param image the image
     * @return this
     */
    public ShapeProperties setImage(NativeImage image)
    {
        this.image = image;
        return this;
    }

    /**
     * Gets the image location, if there is one.
     *
     * @return the location
     */
    @Nullable
    public ResourceLocation getImageLocation()
    {
        return imageLocation;
    }

    /**
     * Sets the image location.
     *
     * @param imageLocation the image location
     * @return this
     */
    public ShapeProperties setImageLocation(ResourceLocation imageLocation)
    {
        this.imageLocation = imageLocation;
        return this;
    }

    /**
     * Gets texture position x.
     *
     * @return the texture position x
     */
    public double getTexturePositionX()
    {
        return texturePositionX;
    }

    /**
     * Sets texture position x.
     *
     * @param texturePositionX the texture position x
     * @return this
     */
    public ShapeProperties setTexturePositionX(double texturePositionX)
    {
        this.texturePositionX = texturePositionX;
        return this;
    }

    /**
     * Gets texture position y.
     *
     * @return the texture position y
     */
    public double getTexturePositionY()
    {
        return texturePositionY;
    }

    /**
     * Sets texture position y.
     *
     * @param texturePositionY the texture position y
     * @return this
     */
    public ShapeProperties setTexturePositionY(double texturePositionY)
    {
        this.texturePositionY = texturePositionY;
        return this;
    }

    /**
     * Gets texture scale x.
     *
     * @return the texture scale x
     */
    public double getTextureScaleX()
    {
        return textureScaleX;
    }

    /**
     * Sets texture scale x.
     *
     * @param textureScaleX the texture scale x
     * @return this
     */
    public ShapeProperties setTextureScaleX(double textureScaleX)
    {
        this.textureScaleX = textureScaleX;
        return this;
    }

    /**
     * Gets texture scale y.
     *
     * @return the texture scale y
     */
    public double getTextureScaleY()
    {
        return textureScaleY;
    }

    /**
     * Sets texture scale y.
     *
     * @param textureScaleY the texture scale y
     * @return this
     */
    public ShapeProperties setTextureScaleY(double textureScaleY)
    {
        this.textureScaleY = textureScaleY;
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
                Objects.equal(strokeWidth, that.strokeWidth) &&
                Objects.equal(imageLocation, that.imageLocation) &&
                Objects.equal(texturePositionX, that.texturePositionX) &&
                Objects.equal(texturePositionY, that.texturePositionY) &&
                Objects.equal(textureScaleX, that.textureScaleX) &&
                Objects.equal(textureScaleY, that.textureScaleY);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(strokeColor, fillColor, strokeOpacity, fillOpacity, strokeWidth, imageLocation, texturePositionX, texturePositionY, textureScaleX, textureScaleY);
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
                .add("imageLocation", imageLocation)
                .add("texturePositionX", texturePositionX)
                .add("texturePositionY", texturePositionY)
                .add("textureScaleX", textureScaleX)
                .add("textureScaleY", textureScaleY)
                .toString();
    }


}
