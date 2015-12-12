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

package journeymap.api.client.v1;

import com.google.common.base.Objects;
import com.google.common.base.Verify;

/**
 * Defines attributes needed to display an icon on the map.
 */
public class MapIcon
{
    private String resourceLocation;
    private int originX;
    private int originY;
    private int width;
    private int height;
    private int color = 0xffffff;
    private float anchorX;
    private float anchorY;

    /**
     * Constructor.
     *
     * @param resourceLocation to image texture for icon. Upper-left (origin) of icon is presumed to be 0,0.
     * @param width            icon width
     * @param height           icon height
     */
    public MapIcon(String resourceLocation, int width, int height)
    {
        this(resourceLocation, 0, 0, width, height);
    }

    /**
     * Constructor.
     *
     * @param resourceLocation to image texture for icon. Upper-left (origin) of icon is presumed to be 0,0.
     * @param originX          Starting x pixel of icon in sprite sheet.
     * @param originY          Starting y pixel of icon in sprite sheet.
     * @param width            icon width
     * @param height           icon height
     */
    public MapIcon(String resourceLocation, int originX, int originY, int width, int height)
    {
        Verify.verifyNotNull(resourceLocation);
        this.resourceLocation = resourceLocation;
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
        setAnchor(width / 2f, height / 2f);
    }

    /**
     * Sets the pixel offsets used to move the icon relative to the map point.
     *
     * @param anchorX left offset in pixels
     */
    public void setAnchor(float anchorX, float anchorY)
    {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }

    /**
     * Gets the icon image texture location.
     *
     * @return the resourceLocation
     */
    public String getResourceLocation()
    {
        return resourceLocation;
    }

    /**
     * Gets the starting x pixel of icon in sprite sheet.
     *
     * @return left offset in pixels
     */
    public int getOriginX()
    {
        return originX;
    }

    /**
     * Gets the starting y pixel of icon in sprite sheet.
     *
     * @return top offset in pixels
     */
    public int getOriginY()
    {
        return originY;
    }

    /**
     * Gets the icon width.
     *
     * @return width in pixels
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the icon height.
     *
     * @return height in pixels
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Gets the color (rgb) to be applied to the image.
     *
     * @return color int
     */
    public int getColor()
    {
        return color;
    }

    /**
     * Sets the color (rgb) to be applied to the image.  Use white (0xffffff)
     * to not change the icon color.
     *
     * @param color rgb int
     */
    public void setColor(int color)
    {
        this.color = Math.max(0x000000, Math.min(color, 0xffffff));
    }

    /**
     * Gets the x pixel offset used to move the icon relative to the
     * point it is paired with. By default anchorX is set to half the icon width.
     *
     * @return left offset in pixels
     */
    public float getAnchorX()
    {
        return anchorX;
    }

    /**
     * Gets the y pixel offset used to move the icon relative to the
     * point it is paired with. By default anchorY is set to half the icon height.
     *
     * @return pixels top offset in pixels
     */
    public float getAnchorY()
    {
        return anchorY;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("resourceLocation", resourceLocation)
                .add("anchorX", anchorX)
                .add("anchorY", anchorY)
                .add("color", color)
                .add("height", height)
                .add("originX", originX)
                .add("originY", originY)
                .add("width", width)
                .toString();
    }
}
