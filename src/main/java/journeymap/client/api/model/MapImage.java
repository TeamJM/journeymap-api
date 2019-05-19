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
import com.google.gson.annotations.Since;
import journeymap.client.api.display.Displayable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;

/**
 * Defines attributes needed to display an image on the map.
 * <p>
 * Note that the default anchorX and anchorY will cause the top left of the image to be placed at an associated BlockPos.
 * To center the image on a BlockPos, use:
 * <code>mapImage.setAnchorX(mapImage.getDisplayWidth()/2.0).setAnchorY(mapImage.getDisplayHeight()/2.0);</code>
 * <p>
 * Setters use the Builder pattern so they can be chained.
 */
public final class MapImage
{
    @Since(1.1)
    private transient BufferedImage image;

    @Since(1.1)
    private ResourceLocation imageLocation;

    @Since(1.1)
    private Integer color = 0xffffff;

    @Since(1.1)
    private Float opacity = 1f;

    @Since(1.1)
    private Integer textureX = 0;

    @Since(1.1)
    private Integer textureY = 0;

    @Since(1.1)
    private Integer textureWidth;

    @Since(1.1)
    private Integer textureHeight;

    @Since(1.1)
    private Integer rotation;

    @Since(1.1)
    private Double displayWidth;

    @Since(1.1)
    private Double displayHeight;

    @Since(1.1)
    private Double anchorX;

    @Since(1.1)
    private Double anchorY;
    /**
     * Constructor.
     * <p>
     * Defaults tint to white (0xffffff) and opacity to 1f.
     * Defaults displayWidth and displayHeight to the texture dimensions.
     *
     * @param image Image texture
     */
    public MapImage(BufferedImage image)
    {
        this(image, 0, 0, image.getWidth(), image.getHeight(), 0xffffff, 1f);
    }

    /**
     * Constructor.
     * <p>
     * Defaults displayWidth and displayHeight to the texture dimensions.
     *
     * @param image         Image texture
     * @param textureX      Start x of texture within image. Useful in sprite sheets.
     * @param textureY      Start y of texture within image. Useful in sprite sheets.
     * @param textureWidth  texture width
     * @param textureHeight texture height
     * @param color         Sets a color tint (rgb) on the image.  Use white (0xffffff) for no tint.
     * @param opacity       opacity between 0 and 1
     */
    public MapImage(BufferedImage image, int textureX, int textureY, int textureWidth, int textureHeight, int color, float opacity)
    {
        this.image = image;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureWidth = Math.max(1, textureWidth);
        this.textureHeight = Math.max(1, textureHeight);
        setDisplayWidth(this.textureWidth);
        setDisplayHeight(this.textureHeight);
        setColor(color);
        setOpacity(opacity);
    }

    /**
     * Constructor.
     *
     * Defaults tint to white (0xffffff) and opacity to 1f.
     * Defaults displayWidth and displayHeight to the texture dimensions.
     *
     * @param imageLocation     location for image
     * @param textureWidth      width of texture
     * @param textureHeight     height of texture
     */
    public MapImage(ResourceLocation imageLocation, int textureWidth, int textureHeight)
    {
        this(imageLocation, 0, 0, textureWidth, textureHeight, 0xffffff, 1f);
    }

    /**
     * Constructor.
     *
     * Defaults displayWidth and displayHeight to the texture dimensions.
     *
     * @param imageLocation Resource location for texture image.
     * @param textureX      Start x of texture within image. Useful in sprite sheets.
     * @param textureY      Start y of texture within image. Useful in sprite sheets.
     * @param textureWidth  texture width
     * @param textureHeight texture height
     * @param color         Sets a color tint (rgb) on the image.  Use white (0xffffff) for no tint.
     * @param opacity       opacity between 0 and 1
     */
    public MapImage(ResourceLocation imageLocation, int textureX, int textureY, int textureWidth, int textureHeight, int color, float opacity)
    {
        this.imageLocation = imageLocation;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureWidth = Math.max(1, textureWidth);
        this.textureHeight = Math.max(1, textureHeight);
        setDisplayWidth(this.textureWidth);
        setDisplayHeight(this.textureHeight);
        setColor(color);
        setOpacity(opacity);
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public int getColor()
    {
        return color;
    }

    /**
     * Sets color used to tint the image.  Use 0xffffff for white (no tint).
     *
     * @param color the color
     * @return this
     */
    public MapImage setColor(int color)
    {
        this.color = Displayable.clampRGB(color);
        return this;
    }

    /**
     * Gets opacity.
     *
     * @return the opacity
     */
    public float getOpacity()
    {
        return opacity;
    }

    /**
     * Sets opacity.
     *
     * @param opacity the opacity
     * @return this
     */
    public MapImage setOpacity(float opacity)
    {
        this.opacity = Displayable.clampOpacity(opacity);
        return this;
    }

    /**
     * Gets X coordinate in BufferedImage where image begins. Useful in sprite sheets.
     * @return textureX
     */
    public int getTextureX()
    {
        return textureX;
    }

    /**
     * Gets Y coordinate in BufferedImage where image begins. Useful in sprite sheets.
     *
     * @return textureY
     */
    public int getTextureY()
    {
        return textureY;
    }

    /**
     * Gets anchor x.
     *
     * @return the anchor x
     */
    public double getAnchorX()
    {
        return anchorX;
    }

    /**
     * Sets anchor x.
     *
     * @param anchorX the anchor x
     * @return this
     */
    public MapImage setAnchorX(double anchorX)
    {
        this.anchorX = anchorX;
        return this;
    }

    /**
     * Gets anchor y.
     *
     * @return the anchor y
     */
    public double getAnchorY()
    {
        return anchorY;
    }

    /**
     * Sets anchor y.
     *
     * @param anchorY the anchor y
     * @return this
     */
    public MapImage setAnchorY(double anchorY)
    {
        this.anchorY = anchorY;
        return this;
    }

    /**
     * Centers the image on the associated position.
     * @return this
     */
    public MapImage centerAnchors()
    {
        setAnchorX(this.displayWidth / 2.0);
        setAnchorY(this.displayHeight / 2.0);
        return this;
    }

    /**
     * Gets the image textureWidth.
     *
     * @return textureWidth
     */
    public int getTextureWidth()
    {
        return textureWidth;
    }

    /**
     * Gets the image textureHeight.
     *
     * @return textureHeight
     */
    public int getTextureHeight()
    {
        return textureHeight;
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
     * Gets the image, if there is one.
     *
     * @return the location
     */
    @Nullable
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Gets the rotation in degrees the image should be oriented.
     * Zero is the default.
     *
     * @return degrees
     */
    public int getRotation()
    {
        return rotation;
    }

    /**
     * Sets the rotation in degrees the image should be oriented.
     * Zero is the default.
     *
     * @param rotation in degrees
     * @return this
     */
    public MapImage setRotation(int rotation)
    {
        this.rotation = rotation % 360;
        return this;
    }

    /**
     * Gets the display width in pixels when rendered.
     * Default value is the texture width itself.
     *
     * @return display width
     */
    public double getDisplayWidth()
    {
        return displayWidth;
    }

    /**
     * Sets the image width in pixels when rendered, allowing the image
     * to be scaled if needed.
     *
     * @return this
     */
    public MapImage setDisplayWidth(double displayWidth)
    {
        this.displayWidth = displayWidth;
        return this;
    }

    /**
     * Gets the image height in pixels when rendered.
     * Default value is the texture width itself.
     *
     * @return display width
     */
    public double getDisplayHeight()
    {
        return displayHeight;
    }

    /**
     * Sets the image height in pixels when rendered, allowing the image
     * to be scaled if needed.
     *
     * @return this
     */
    public MapImage setDisplayHeight(double displayHeight)
    {
        this.displayHeight = displayHeight;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        MapImage mapImage = (MapImage) o;
        return Objects.equal(color, mapImage.color) &&
                Objects.equal(opacity, mapImage.opacity) &&
                Objects.equal(anchorX, mapImage.anchorX) &&
                Objects.equal(anchorY, mapImage.anchorY) &&
                Objects.equal(textureX, mapImage.textureX) &&
                Objects.equal(textureY, mapImage.textureY) &&
                Objects.equal(textureWidth, mapImage.textureWidth) &&
                Objects.equal(textureHeight, mapImage.textureHeight) &&
                Objects.equal(imageLocation, mapImage.imageLocation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(imageLocation, color, opacity, anchorX, anchorY, textureX, textureY, textureWidth, textureHeight);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("imageLocation", imageLocation)
                .add("anchorX", anchorX)
                .add("anchorY", anchorY)
                .add("color", color)
                .add("textureHeight", textureHeight)
                .add("opacity", opacity)
                .add("textureX", textureX)
                .add("textureY", textureY)
                .add("textureWidth", textureWidth)
                .toString();
    }

}
