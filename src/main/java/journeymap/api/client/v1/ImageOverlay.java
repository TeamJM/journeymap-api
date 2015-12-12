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
 * An image overlay places an image on the map between the given coordinates.
 */
public class ImageOverlay extends OverlayBase
{
    private String imageId;
    private MapPoint northWestPoint;
    private MapPoint southEastPoint;
    private MapIcon image;

    /**
     * Constructor.
     *
     * @param imageId        A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param northWestPoint Location of the top-left corner of the image.
     * @param southEastPoint Location of the lower-right corner of the image.
     * @param image          The image to display as the overlay.
     */
    public ImageOverlay(String imageId, MapPoint northWestPoint, MapPoint southEastPoint, MapIcon image)
    {
        this(imageId, null, northWestPoint, southEastPoint, null, null, image);
    }

    /**
     * Constructor.
     *
     * @param imageId          A unique id for the marker (scoped to your mod) which can be used to remove/update it.
     * @param overlayGroupName A suggested group or category name used to organize map overlays.
     * @param northWestPoint   Location of the top-left of the image.
     * @param southEastPoint   Location of the lower-right corner of the image.
     * @param title            (Optional) Rollover text to be displayed when the mouse is over the overlay.
     * @param label            (Optional) Label text to be displayed on the overlay.
     * @param image            The image to display as the overlay.
     */
    public ImageOverlay(String imageId, String overlayGroupName, MapPoint northWestPoint, MapPoint southEastPoint,
                        String title, String label, MapIcon image)
    {
        Verify.verifyNotNull(imageId);
        Verify.verifyNotNull(image);
        Verify.verifyNotNull(northWestPoint);
        Verify.verifyNotNull(southEastPoint);
        this.imageId = imageId;
        this.image = image;
        this.northWestPoint = northWestPoint;
        this.southEastPoint = southEastPoint;
        super.setOverlayGroupName(overlayGroupName);
        super.setTitle(title);
        super.setLabel(label);
    }

    /**
     * A unique id for the image overlay which can be used to remove/update it.
     */
    public String getImageId()
    {
        return imageId;
    }

    /**
     * Top-left location of the image overlay.
     */
    public MapPoint getNorthWestPoint()
    {
        return northWestPoint;
    }

    /**
     * Bottom-right location of the image overlay.
     */
    public MapPoint getSouthEastPoint()
    {
        return southEastPoint;
    }

    /**
     * Image to display as the overlay.
     *
     * @return icon
     */
    public MapIcon getImage()
    {
        return image;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("imageId", imageId)
                .add("northWestPoint", northWestPoint)
                .add("southEastPoint", southEastPoint)
                .add("image", image)
                .add("label", label)
                .add("title", title)
                .add("overlayGroupName", overlayGroupName)
                .add("color", color)
                .add("inFullscreen", inFullscreen)
                .add("inMinimap", inMinimap)
                .add("inWebmap", inWebmap)
                .add("maxZoom", maxZoom)
                .add("minZoom", minZoom)
                .add("zIndex", zIndex)
                .toString();
    }
}
