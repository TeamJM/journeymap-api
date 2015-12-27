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
 * + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 * + Write your own code that uses, modifies, or extends the example source code in example.* packages
 * + Distribute compiled classes of unmodified API source code in journeymap.* packages
 * + Fork and modify any source code for the purpose of submitting Pull Requests to the
 *        TeamJM/journeymap-api repository.  Submitting new or modified code to the repository
 *        means that you are granting Techbrew all rights over the code.
 *
 * You MAY NOT:
 *   - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *   - Distribute modified API source code from journeymap.* packages or compiled classes of modified API
 *        source code.  In this context, "modified" means changes which have not been both approved
 *        and merged into the TeamJM/journeymap-api repository.
 *   - Use or distribute the API source code or example source code in any way not explicitly granted
 *        by this license statement.
 *
 */

package example.client.impl.model;

import com.google.common.base.Objects;
import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

import java.awt.image.BufferedImage;

/**
 * Defines attributes needed to display an icon on the map.
 */
@Optional.Interface(iface = "journeymap.client.api.model.IMapIcon", modid = "journeymap")
public final class MapIcon implements journeymap.client.api.model.IMapIcon
{
    private final BufferedImage image;
    private final int color;
    private final float opacity;
    private final float anchorX;
    private final float anchorY;

    /**
     * Constructor.
     *
     * @param image   image texture for icon. Upper-left (origin) of icon is presumed to be 0,0.
     * @param color   Sets the color (rgb) to be applied to the image.  Use white (0xffffff) to not change the icon color.
     * @param opacity opacity between 0 and 1
     * @param anchorX X pixel offset to move the icon relative to the map point
     * @param anchorY Y pixel offset to move the icon relative to the map point
     */
    public MapIcon(BufferedImage image, int color, float opacity, float anchorX, float anchorY)
    {
        Verify.verifyNotNull(image);
        this.image = image;
        this.color = Math.max(0x000000, Math.min(color, 0xffffff));
        this.opacity = Math.max(0, Math.min(opacity, 1));
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }

    @Override
    public BufferedImage getImage()
    {
        return null;
    }

    @Override
    public int getColor()
    {
        return color;
    }

    @Override
    public float getOpacity()
    {
        return opacity;
    }

    @Override
    public float getAnchorX()
    {
        return anchorX;
    }

    @Override
    public float getAnchorY()
    {
        return anchorY;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("color", color)
                .add("opacity", opacity)
                .add("height", image.getHeight())
                .add("width", image.getWidth())
                .toString();
    }
}
