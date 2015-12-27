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
import cpw.mods.fml.common.Optional;

/**
 * Common Map overlay characteristics.
 */
@Optional.Interface(iface = "journeymap.client.api.overlay.IOverlayProperties", modid = "journeymap")
public final class OverlayProperties implements journeymap.client.api.overlay.IOverlayProperties
{
    protected final String overlayGroupName;
    protected final String title;
    protected final String label;
    protected final int dimension;
    protected final int color;
    protected final int minZoom;
    protected final int maxZoom;
    protected final int zIndex;
    protected final boolean inMinimap;
    protected final boolean inFullscreen;
    protected final boolean inWebmap;

    /**
     * Constructor.
     *
     * @param overlayGroupName A suggested group or category name used to organize map overlays.
     * @param title            Rollover text to be displayed when the mouse is over the overlay.
     * @param label            Label text to be displayed on the overlay.
     */
    public OverlayProperties(String overlayGroupName, String title, String label, int dimension)
    {
        this(overlayGroupName, title, label, dimension, 0xffffff, 0, 8, 1000, true, true, true);
    }

    /**
     * Constructor.
     *
     * @param overlayGroupName A suggested group or category name used to organize map overlays.
     * @param title            Rollover text to be displayed when the mouse is over the overlay.
     * @param label            Label text to be displayed on the overlay.
     * @param color            Font color (rgb) of the label and title.
     * @param minZoom          The minimum zoom level (0 is lowest) where the polygon should be visible.
     * @param maxZoom          The maximum zoom level (8 is highest) where the polygon should be visible.
     * @param zIndex           All features are displayed on the map in order of their zIndex, higher over lower.
     * @param inMinimap        Whether the overlay should be displayed in the Minimap.
     * @param inFullscreen     Whether the overlay should be displayed in the Fullscreen map.
     * @param inWebmap         Whether the overlay should be displayed in the Web map (when enabled).
     */
    public OverlayProperties(String overlayGroupName, String title, String label, int dimension, int color, int minZoom, int maxZoom, int zIndex, boolean inMinimap, boolean inFullscreen, boolean inWebmap)
    {
        this.overlayGroupName = overlayGroupName;
        this.title = title;
        this.label = label;
        this.dimension = dimension;
        this.color = color;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.zIndex = zIndex;
        this.inMinimap = inMinimap;
        this.inFullscreen = inFullscreen;
        this.inWebmap = inWebmap;
    }

    @Override
    public int getDimension()
    {
        return 0;
    }

    /**
     * A suggested group or category name used to organize map overlays.
     */
    @Override
    public String getOverlayGroupName()
    {
        return overlayGroupName;
    }

    /**
     * Rollover text to be displayed when the mouse is over the overlay.
     */
    @Override
    public String getTitle()
    {
        return title;
    }

    /**
     * Label text to be displayed on the overlay.
     */
    @Override
    public String getLabel()
    {
        return label;
    }

    /**
     * Font color (rgb) of the label and title.
     */
    @Override
    public int getColor()
    {
        return color;
    }

    /**
     * The minimum zoom level (0 is lowest) where the polygon should be visible.
     */
    @Override
    public int getMinZoom()
    {
        return minZoom;
    }

    /**
     * The maximum zoom level (8 is highest) where the polygon should be visible.
     */
    @Override
    public int getMaxZoom()
    {
        return maxZoom;
    }

    /**
     * All features are displayed on the map in order of their zIndex, with higher values
     * displaying in front of features with lower values. Default is 1000.
     */
    @Override
    public int getZIndex()
    {
        return zIndex;
    }

    /**
     * Whether the overlay should be displayed in the Minimap.
     */
    @Override
    public boolean isInMinimap()
    {
        return inMinimap;
    }

    /**
     * Whether the overlay should be displayed in the Fullscreen map.
     */
    @Override
    public boolean isInFullscreen()
    {
        return inFullscreen;
    }

    /**
     * Whether the overlay should be displayed in the Web map (when enabled).
     */
    @Override
    public boolean isInWebmap()
    {
        return inWebmap;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof journeymap.client.api.overlay.IOverlayProperties))
        {
            return false;
        }
        OverlayProperties that = (OverlayProperties) o;
        return Objects.equal(color, that.color) &&
                Objects.equal(minZoom, that.minZoom) &&
                Objects.equal(maxZoom, that.maxZoom) &&
                Objects.equal(zIndex, that.zIndex) &&
                Objects.equal(inMinimap, that.inMinimap) &&
                Objects.equal(inFullscreen, that.inFullscreen) &&
                Objects.equal(inWebmap, that.inWebmap) &&
                Objects.equal(overlayGroupName, that.overlayGroupName) &&
                Objects.equal(title, that.title) &&
                Objects.equal(label, that.label);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(overlayGroupName, title, label, color, minZoom, maxZoom, zIndex, inMinimap, inFullscreen, inWebmap);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("color", color)
                .add("inFullscreen", inFullscreen)
                .add("inMinimap", inMinimap)
                .add("inWebmap", inWebmap)
                .add("label", label)
                .add("maxZoom", maxZoom)
                .add("minZoom", minZoom)
                .add("overlayGroupName", overlayGroupName)
                .add("title", title)
                .add("zIndex", zIndex)
                .toString();
    }


}
