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

package journeymap.client.api.overlay;

/**
 * Common map overlay properties.
 */
public interface IOverlayProperties
{
    /**
     * The dimension where the overlay should be displayed. (0=Overworld, -1=Nether, 1=End, etc.)
     *
     * @return dimension id
     */
    int getDimension();

    /**
     * A suggested group or category name that lets the user toggle on/off groups of map overlays.
     * For example: "Claimed Chunks", or "Power Sources", or "Quest Locations".
     */
    String getOverlayGroupName();

    /**
     * Rollover text to be displayed when the mouse is over the overlay.
     */
    String getTitle();

    /**
     * Label text to be displayed on the overlay.
     */
    String getLabel();

    /**
     * Font color (rgb) of the label and title. Range is 0x000000 - 0xffffff).
     */
    int getColor();

    /**
     * The minimum zoom level (0 is lowest) where the overlay should be visible.
     */
    int getMinZoom();

    /**
     * The maximum zoom level (8 is highest) where the overlay should be visible.
     */
    int getMaxZoom();

    /**
     * All features are displayed on the map in order of their zIndex, with higher values
     * displaying in front of features with lower values. Default is 1000.
     */
    int getZIndex();

    /**
     * Whether the overlay should be displayed in the Minimap.
     */
    boolean isInMinimap();

    /**
     * Whether the overlay should be displayed in the Fullscreen map.
     */
    boolean isInFullscreen();

    /**
     * Whether the overlay should be displayed in the Web map (when enabled).
     */
    boolean isInWebmap();
}
