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

package journeymap.client.api.map;

/**
 * Base class for common Map overlay characteristics.
 */
public abstract class OverlayBase
{
    protected String overlayGroupName;
    protected String title;
    protected String label;
    protected int color;
    protected int minZoom = 0;
    protected int maxZoom = 8;
    protected int zIndex = 1000;
    protected boolean inMinimap = true;
    protected boolean inFullscreen = true;
    protected boolean inWebmap = true;

    /**
     * A suggested group or category name used to organize map overlays.
     */
    public String getOverlayGroupName()
    {
        return overlayGroupName;
    }

    public void setOverlayGroupName(String overlayGroupName)
    {
        this.overlayGroupName = overlayGroupName;
    }

    /**
     * Rollover text to be displayed when the mouse is over the overlay.
     */
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Label text to be displayed on the overlay.
     */
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Font color (rgb) of the label and title.
     */
    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    /**
     * The minimum zoom level (0 is lowest) where the polygon should be visible.
     */
    public int getMinZoom()
    {
        return minZoom;
    }

    public void setMinZoom(int minZoom)
    {
        this.minZoom = minZoom;
    }

    /**
     * The maximum zoom level (8 is highest) where the polygon should be visible.
     */
    public int getMaxZoom()
    {
        return maxZoom;
    }

    public void setMaxZoom(int maxZoom)
    {
        this.maxZoom = maxZoom;
    }

    /**
     * All features are displayed on the map in order of their zIndex, with higher values
     * displaying in front of features with lower values. Default is 1000.
     */
    public int getZIndex()
    {
        return zIndex;
    }

    public void setZIndex(int zIndex)
    {
        this.zIndex = zIndex;
    }

    /**
     * Whether the polygon should be displayed in the Minimap.
     */
    public boolean isInMinimap()
    {
        return inMinimap;
    }

    public void setInMinimap(boolean inMinimap)
    {
        this.inMinimap = inMinimap;
    }

    /**
     * Whether the polygon should be displayed in the Fullscreen map.
     */
    public boolean isInFullscreen()
    {
        return inFullscreen;
    }

    public void setInFullscreen(boolean inFullscreen)
    {
        this.inFullscreen = inFullscreen;
    }

    /**
     * Whether the polygon should be displayed in the Web map (when enabled).
     */
    public boolean isInWebmap()
    {
        return inWebmap;
    }

    public void setInWebmap(boolean inWebmap)
    {
        this.inWebmap = inWebmap;
    }
}
