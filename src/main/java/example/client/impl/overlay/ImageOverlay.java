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

package example.client.impl.overlay;

import com.google.common.base.Objects;
import com.google.common.base.Verify;
import cpw.mods.fml.common.Optional;

/**
 * An image overlay places an image on the map between the given coordinates.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "journeymap.client.api.overlay.IImageOverlay", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.overlay.IOverlayProperties", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapPoint", modid = "journeymap"),
        @Optional.Interface(iface = "journeymap.client.api.model.IMapIcon", modid = "journeymap")
})
public final class ImageOverlay extends Overlay implements journeymap.client.api.overlay.IImageOverlay
{
    private final journeymap.client.api.overlay.IOverlayProperties overlayProperties;
    private final journeymap.client.api.model.IMapPoint northWestPoint;
    private final journeymap.client.api.model.IMapPoint southEastPoint;
    private final journeymap.client.api.model.IMapIcon image;

    /**
     * Constructor.
     * @param modId          Your mod id.
     * @param imageId        A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param northWestPoint Location of the top-left corner of the image.
     * @param southEastPoint Location of the lower-right corner of the image.
     * @param image          The image to display as the overlay.
     * @param overlayProperties  Common overlay characteristics.
     */
    public ImageOverlay(String modId, String imageId, journeymap.client.api.model.IMapPoint northWestPoint,
                        journeymap.client.api.model.IMapPoint southEastPoint, journeymap.client.api.model.IMapIcon image,
                        journeymap.client.api.overlay.IOverlayProperties overlayProperties)
    {
        super(modId, imageId);
        Verify.verifyNotNull(image);
        Verify.verifyNotNull(northWestPoint);
        Verify.verifyNotNull(southEastPoint);
        Verify.verifyNotNull(overlayProperties);
        this.image = image;
        this.northWestPoint = northWestPoint;
        this.southEastPoint = southEastPoint;
        this.overlayProperties = overlayProperties;
    }

    /**
     * Top-left location of the image overlay.
     */
    @Override
    public journeymap.client.api.model.IMapPoint getNorthWestPoint()
    {
        return northWestPoint;
    }

    /**
     * Bottom-right location of the image overlay.
     */
    @Override
    public journeymap.client.api.model.IMapPoint getSouthEastPoint()
    {
        return southEastPoint;
    }

    /**
     * Image to display as the overlay.
     *
     * @return icon
     */
    @Override
    public journeymap.client.api.model.IMapIcon getImage()
    {
        return image;
    }

    /**
     * Common overlay characteristics.
     *
     * @return properties
     */
    @Override
    public journeymap.client.api.overlay.IOverlayProperties getOverlayProperties()
    {
        return overlayProperties;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("imageId", getDisplayId())
                .add("northWestPoint", northWestPoint)
                .add("southEastPoint", southEastPoint)
                .add("image", image)
                .add("overlayProperties", overlayProperties)
                .toString();
    }
}
