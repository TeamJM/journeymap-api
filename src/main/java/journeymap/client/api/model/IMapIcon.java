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

package journeymap.client.api.model;

import java.awt.image.BufferedImage;

/**
 * Describes an image to be displayed on a map.
 */
public interface IMapIcon
{
    /**
     * Gets the image.  If you're using a sprite sheet, you can return the result of
     * BufferedImage.getSubImage() to provide just the image area needed.
     *
     * @return the resourceLocation
     */
    BufferedImage getImage();

    /**
     * Gets the tint color (rgb) to be applied to the image (range 0x000000 - 0xffffff).
     * Should return white (0xffffff) if the image needs no tint.
     *
     * @return color int
     */
    int getColor();

    /**
     * Gets the opacity of the image (range 0f - 1f).
     * Should return 1f if the image is completely opaque.
     *
     * @return opacity
     */
    float getOpacity();

    /**
     * Gets the x pixel offset used to move the icon relative to the point it is paired with.
     * To center the image horizontally, return { getImage().getWidth()/2.0f }.
     *
     * @return left offset in pixels
     */
    float getAnchorX();

    /**
     * Gets the y pixel offset used to move the icon relative to the point it is paired with.
     * To center the image vertically, return { getImage().getHeight()/2.0f }.
     *
     * @return pixels top offset in pixels
     */
    float getAnchorY();
}
