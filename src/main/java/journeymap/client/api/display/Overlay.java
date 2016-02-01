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

import journeymap.client.api.model.TextProperties;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Provides IDs and key information for map overlays in JourneyMap.
 */
@ParametersAreNonnullByDefault
public abstract class Overlay extends Displayable
{
    protected String overlayGroupName;
    protected String title;
    protected String label;
    protected int dimension;
    protected int minZoom;
    protected int maxZoom;
    protected int displayOrder;
    protected boolean inMinimap = true;
    protected boolean inFullscreen = true;
    protected boolean inWebmap = true;
    protected TextProperties textProperties = new TextProperties();

    /**
     * Constructor.
     *
     * @param modId     the mod id
     * @param displayId the display id
     */
    Overlay(String modId, String displayId)
    {
        super(modId, displayId);
    }

    /**
     * The dimension this overlay should be displayed in.
     *
     * @return dimension id
     */
    public int getDimension()
    {
        return dimension;
    }

    /**
     * Sets dimension.
     *
     * @param dimension the dimension
     * @return this
     */
    public Overlay setDimension(int dimension)
    {
        this.dimension = dimension;
        return this;
    }

    /**
     * A suggested group or category name used to organize map overlays.
     *
     * @return the overlay group name
     */
    public String getOverlayGroupName()
    {
        return overlayGroupName;
    }

    /**
     * A suggested group or category name used to organize map overlays.
     *
     * @param overlayGroupName the overlay group name
     * @return this
     */
    public Overlay setOverlayGroupName(String overlayGroupName)
    {
        this.overlayGroupName = overlayGroupName;
        return this;
    }

    /**
     * Rollover text to be displayed when the mouse is over the overlay.
     *
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Rollover text to be displayed when the mouse is over the overlay.
     *
     * @param title the title
     * @return this
     */
    public Overlay setTitle(String title)
    {
        this.title = title;
        return this;
    }

    /**
     * Label text to be displayed on the overlay.
     *
     * @return the label
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Label text to be displayed on the overlay.
     *
     * @param label the label
     * @return this
     */
    public Overlay setLabel(String label)
    {
        this.label = label;
        return this;
    }


    /**
     * The minimum zoom level (0 is lowest) where the polygon should be visible.
     *
     * @return the min zoom
     */
    public int getMinZoom()
    {
        return minZoom;
    }

    /**
     * Sets the minimum zoom level (0 is lowest) where the polygon should be visible.
     *
     * @param minZoom the min zoom
     * @return this
     */
    public Overlay setMinZoom(int minZoom)
    {
        this.minZoom = minZoom;
        return this;
    }

    /**
     * The maximum zoom level (8 is highest) where the polygon should be visible.
     *
     * @return the max zoom
     */
    public int getMaxZoom()
    {
        return maxZoom;
    }

    /**
     * Sets the maximum zoom level (8 is highest) where the polygon should be visible.
     *
     * @param maxZoom the max zoom
     * @return this
     */
    public Overlay setMaxZoom(int maxZoom)
    {
        this.maxZoom = maxZoom;
        return this;
    }

    /**
     * All features are displayed on the map in order of their screen displayOrder, with higher values
     * displaying in front of features with lower values. Default is 1000.
     *
     * @return the z index
     */
    public int getDisplayOrder()
    {
        return displayOrder;
    }

    /**
     * All features are displayed on the map in order of their screen displayOrder, with higher values
     * displaying in front of features with lower values. Default is 1000.
     *
     * @param zIndex the z index
     * @return this
     */
    public Overlay setDisplayOrder(int zIndex)
    {
        this.displayOrder = zIndex;
        return this;
    }

    /**
     * Whether the overlay should be displayed in the Minimap.
     *
     * @return the boolean
     */
    public boolean isInMinimap()
    {
        return inMinimap;
    }

    /**
     * Sets whether the overlay should be displayed in the Minimap.
     *
     * @param inMinimap the in minimap
     * @return this
     */
    public Overlay setInMinimap(boolean inMinimap)
    {
        this.inMinimap = inMinimap;
        return this;
    }

    /**
     * Whether the overlay should be displayed in the Fullscreen map.
     *
     * @return the boolean
     */
    public boolean isInFullscreen()
    {
        return inFullscreen;
    }

    /**
     * Sets whether the overlay should be displayed in the Fullscreen map.
     *
     * @param inFullscreen the in fullscreen
     * @return this
     */
    public Overlay setInFullscreen(boolean inFullscreen)
    {
        this.inFullscreen = inFullscreen;
        return this;
    }

    /**
     * Whether the overlay should be displayed in the Web map (when enabled).
     *
     * @return the boolean
     */
    public boolean isInWebmap()
    {
        return inWebmap;
    }

    /**
     * Sets whether the overlay should be displayed in the Web map (when enabled).
     *
     * @param inWebmap the in webmap
     * @return this
     */
    public Overlay setInWebmap(boolean inWebmap)
    {
        this.inWebmap = inWebmap;
        return this;
    }

    /**
     * Gets the text properties for the overlay.
     *
     * @return properties
     */
    public TextProperties getTextProperties()
    {
        return textProperties;
    }

    /**
     * Sets the text properties for the overlay.
     *
     * @param textProperties properties
     * @return this
     */
    public Overlay setTextProperties(TextProperties textProperties)
    {
        this.textProperties = textProperties;
        return this;
    }

}
