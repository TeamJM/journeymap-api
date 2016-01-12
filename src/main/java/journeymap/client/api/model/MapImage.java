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
import journeymap.client.api.display.Displayable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    private float anchorX;
    private float anchorY;
    private int width;
    private int height;

    /**
     * Constructor.
     *
     * @param imageLocation image location for image.
     * @param width         displayed image width
     * @param height        displayed image height
     */
    public MapImage(ResourceLocation imageLocation, int width, int height)
    {
        this(imageLocation, width, height, 0xffffff, 1f, width / 2f, height / 2f);
    }

    /**
     * Constructor.
     *
     * @param imageLocation image location for texture.
     * @param width         displayed image width
     * @param height        displayed image height
     * @param color         Sets the color (rgb) to be applied to the image.  Use white (0xffffff) to not change the image color.
     * @param opacity       opacity between 0 and 1
     * @param anchorX       X pixel offset to move the image relative to the map point
     * @param anchorY       Y pixel offset to move the image relative to the map point
     */
    public MapImage(ResourceLocation imageLocation, int width, int height, int color, float opacity, float anchorX, float anchorY)
    {
        setAnchorX(anchorX);
        setAnchorY(anchorY);
        setWidth(width);
        setHeight(height);
        setImageLocation(imageLocation);
        setColor(color);
        setOpacity(opacity);
    }

    private static BufferedImage getImage(ResourceLocation location)
    {
        try
        {
            return ImageIO.read(Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(location));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            BufferedImage missing = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            missing.getRaster().setPixels(0, 0, 16, 16, TextureUtil.missingTextureData);
            return missing;
        }
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public BufferedImage getImage()
    {
        return null;
    }

    /**
     * Sets image.
     *
     * @param imageLocation the image
     * @return this
     */
    public MapImage setImage(ResourceLocation imageLocation)
    {
        this.image = getImage(imageLocation);
        return this;
    }

    /**
     * Sets image.
     *
     * @param image the image
     * @return this
     */
    public MapImage setImage(BufferedImage image)
    {
        this.image = image;
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
     * Sets color.
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
     * Gets anchor x.
     *
     * @return the anchor x
     */
    public float getAnchorX()
    {
        return anchorX;
    }

    /**
     * Sets anchor x.
     *
     * @param anchorX the anchor x
     * @return this
     */
    public MapImage setAnchorX(float anchorX)
    {
        this.anchorX = anchorX;
        return this;
    }

    /**
     * Gets anchor y.
     *
     * @return the anchor y
     */
    public float getAnchorY()
    {
        return anchorY;
    }

    /**
     * Sets anchor y.
     *
     * @param anchorY the anchor y
     * @return this
     */
    public MapImage setAnchorY(float anchorY)
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
     * Sets the width of the image when displayed.
     *
     * @param width the width
     * @return this
     */
    public MapImage setWidth(int width)
    {
        this.width = width;
        return this;
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
     * Sets the height of the image when displayed.
     *
     * @param height the height
     * @return this
     */
    public MapImage setHeight(int height)
    {
        this.height = height;
        return this;
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
     * Sets the image location.
     *
     * @param imageLocation the location
     * @return this
     */
    public MapImage setImageLocation(ResourceLocation imageLocation)
    {
        this.image = getImage(imageLocation);
        this.imageLocation = imageLocation;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MapImage))
        {
            return false;
        }
        MapImage mapImage = (MapImage) o;
        return Objects.equal(color, mapImage.color) &&
                Objects.equal(opacity, mapImage.opacity) &&
                Objects.equal(anchorX, mapImage.anchorX) &&
                Objects.equal(anchorY, mapImage.anchorY) &&
                Objects.equal(image, mapImage.image);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(image, color, opacity, anchorX, anchorY);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("height", image.getHeight())
                .add("width", image.getWidth())
                .add("anchorX", anchorX)
                .add("anchorY", anchorY)
                .add("color", color)
                .add("opacity", opacity)
                .toString();
    }

}
