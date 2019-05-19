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

import journeymap.client.api.model.MapImage;
import net.minecraft.util.math.BlockPos;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An image overlay places an image on the map between the given coordinates.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 * <p>
 * Note that like all Displayables, simply changing this object doesn't guarantee the player will get the changes.
 * You must call {@link journeymap.client.api.IClientAPI#show(Displayable)} in order for the changes to take effect
 * in JourneyMap.
 */
@ParametersAreNonnullByDefault
public final class ImageOverlay extends Overlay
{
    private BlockPos northWestPoint;
    private BlockPos southEastPoint;
    private MapImage image;

    /**
     * Constructor.
     *
     * @param modId          Your mod id.
     * @param imageId        A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param northWestPoint Location of the top-left corner of the image.
     * @param southEastPoint Location of the lower-right corner of the image.
     * @param image          The image to display as the overlay.
     */
    public ImageOverlay(String modId, String imageId, BlockPos northWestPoint, BlockPos southEastPoint, MapImage image)
    {
        super(modId, imageId);
        setNorthWestPoint(northWestPoint);
        setSouthEastPoint(southEastPoint);
        setImage(image);
    }

    /**
     * Top-left location of the image overlay.
     */
    public BlockPos getNorthWestPoint()
    {
        return northWestPoint;
    }

    /**
     * Sets top-left location of the image overlay.
     *
     * @param northWestPoint point
     * @return this
     */
    public ImageOverlay setNorthWestPoint(BlockPos northWestPoint)
    {
        this.northWestPoint = northWestPoint;
        return this;
    }

    /**
     * Bottom-right location of the image overlay.
     */
    public BlockPos getSouthEastPoint()
    {
        return southEastPoint;
    }

    /**
     * Sets bottom-right location of the image overlay.
     *
     * @param southEastPoint point
     * @return this
     */
    public ImageOverlay setSouthEastPoint(BlockPos southEastPoint)
    {
        this.southEastPoint = southEastPoint;
        return this;
    }

    /**
     * Image to display as the overlay.
     *
     * @return icon
     */
    public MapImage getImage()
    {
        return image;
    }

    /**
     * Sets image to display as the overlay.
     *
     * @param image image
     * @return this
     */
    public ImageOverlay setImage(MapImage image)
    {
        this.image = image;
        return this;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("image", image)
                .add("northWestPoint", northWestPoint)
                .add("southEastPoint", southEastPoint)
                .toString();
    }
}
