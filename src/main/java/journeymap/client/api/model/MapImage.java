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
import example.mod.ExampleMod;
import journeymap.client.api.display.Displayable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Defines attributes needed to display an image on the map.
 * <p/>
 * Setters use the Builder pattern so they can be chained.
 */
public final class MapImage
{
    private BufferedImage image;
    private ResourceLocation imageLocation;
    private int color = 0xffffff;
    private float opacity = 1f;
    private double anchorX;
    private double anchorY;
    private int textureX = 0;
    private int textureY = 0;
    private int width;
    private int height;
    private float scale = 1;


    /**
     * Constructor.
     *
     * @param imageLocation image location for image.
     * @param width         displayed image width
     * @param height        displayed image height
     */
    public MapImage(ResourceLocation imageLocation, int width, int height)
    {
        this(imageLocation, 0, 0, width, height, 0xffffff, 1f);
    }

    /**
     * Constructor.
     *
     * @param imageLocation image location for texture.
     * @param width         displayed image width
     * @param height        displayed image height
     * @param color         Sets a color tint (rgb) on the image.  Use white (0xffffff) for no tint.
     * @param opacity       opacity between 0 and 1
     * @param textureX      X coordinate in BufferedImage where image begins. Useful in sprite sheets.
     * @param textureY      Y coordinate in BufferedImage where image begins. Useful in sprite sheets.
     */
    public MapImage(ResourceLocation imageLocation, int textureX, int textureY, int width, int height, int color, float opacity)
    {
        setImage(imageLocation, textureX, textureY, width, height);
        setAnchorX(anchorX);
        setAnchorY(anchorY);
        setColor(color);
        setOpacity(opacity);
    }

    /**
     * Gets a buffered image from the resource location
     *
     * @param location location
     * @return image
     */
    private static BufferedImage resolveImage(ResourceLocation location)
    {
        IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        try
        {
            InputStream is = resourceManager.getResource(location).getInputStream();
            if (is == null)
            {
                throw new IOException("Resource not found: " + location);
            }
            return TextureUtil.readBufferedImage(is);
        }
        catch (Exception e)
        {
            ExampleMod.LOGGER.error("Can't resolve image: " + location, e);
            return new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        }
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Sets image. Image anchorX, anchorY also set to the center of the image.
     *
     * @param imageLocation the image location
     * @return this
     */
    public MapImage setImage(ResourceLocation imageLocation)
    {
        this.imageLocation = imageLocation;
        BufferedImage image = resolveImage(imageLocation);
        setImage(image, 0, 0, image.getWidth(), image.getHeight());
        return this;
    }

    /**
     * Sets image with args useful when using a sprite sheet.
     * Image anchorX, anchorY also set to the center of the image.
     *
     * @param imageLocation image location
     * @param textureX      start x of texture within image
     * @param textureY      start y of texture within image
     * @param width         width of displayed texture
     * @param height        height of displayed texture
     * @return this
     */
    public MapImage setImage(ResourceLocation imageLocation, int textureX, int textureY, int width, int height)
    {
        this.imageLocation = imageLocation;
        return setImage(resolveImage(imageLocation), textureX, textureY, width, height);
    }

    /**
     * Sets image with args useful when using a sprite sheet.
     * Image anchorX, anchorY also set to the center of the image.
     *
     * @param image    image
     * @param textureX start x of texture within image
     * @param textureY start y of texture within image
     * @param width    width of displayed texture
     * @param height   height of displayed texture
     * @return this
     */
    public MapImage setImage(BufferedImage image, int textureX, int textureY, int width, int height)
    {
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
        this.image = image.getSubimage(textureX, textureY, width, height);

        // Centers the image on the map point
        setAnchorX(width / 2.0).setAnchorY(height/2.0);
        return this;
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
     * Gets the image width.
     *
     * @return width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the image height.
     *
     * @return height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Gets the image location.
     *
     * @return the location
     */
    public ResourceLocation getImageLocation()
    {
        return imageLocation;
    }

    /**
     * Gets the scale (multiplier of height and width)
     * the image is drawn at.  Default is 1.
     *
     * @return scale
     */
    public float getScale()
    {
        return scale;
    }

    /**
     * Sets the scale (multiplier of height and width)
     * the image is drawn at.  Default is 1.  Range is .5f - 16f.
     *
     * @param scale
     * @return
     */
    public MapImage setScale(float scale)
    {
        this.scale = Math.max(.5f, Math.min(scale, 16f));
        return this;
    }

    @Override
    public boolean equals(Object o)
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
                Objects.equal(width, mapImage.width) &&
                Objects.equal(height, mapImage.height) &&
                Objects.equal(imageLocation, mapImage.imageLocation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(imageLocation, color, opacity, anchorX, anchorY, textureX, textureY, width, height);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("imageLocation", imageLocation)
                .add("anchorX", anchorX)
                .add("anchorY", anchorY)
                .add("color", color)
                .add("height", height)
                .add("opacity", opacity)
                .add("textureX", textureX)
                .add("textureY", textureY)
                .add("width", width)
                .toString();
    }

}
