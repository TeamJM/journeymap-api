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

import com.google.common.base.MoreObjects;
import journeymap.client.api.model.TextProperties;
import journeymap.client.api.util.UIState;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;

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
    protected int minZoom = 0;
    protected int maxZoom = 8;
    protected int displayOrder;
    protected EnumSet<Context.UI> activeUIs = EnumSet.of(Context.UI.Any);
    protected EnumSet<Context.MapType> activeMapTypes = EnumSet.of(Context.MapType.Any);
    protected TextProperties textProperties = new TextProperties();
    protected IOverlayListener overlayListener;
    protected boolean needsRerender = true;

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
    public Overlay setTitle(@Nullable String title)
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
    public Overlay setLabel(@Nullable String label)
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
        this.minZoom = Math.max(0, minZoom);
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
        this.maxZoom = Math.min(8, maxZoom);
        return this;
    }

    /**
     * Any features are displayed on the map in order of their screen displayOrder, with higher values
     * displaying in front of features with lower values. Default is 1000.
     *
     * @return the z index
     */
    public int getDisplayOrder()
    {
        return displayOrder;
    }

    /**
     * Any features are displayed on the map in order of their screen displayOrder, with higher values
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

    /**
     * Returns a set of enums indicating which JourneyMap UIs (Fullscreen, Minimap, Webmap)
     * the overlay should be active in.
     *
     * @return enumset
     */
    public EnumSet<Context.UI> getActiveUIs()
    {
        return activeUIs;
    }

    /**
     * Set of enums indicating which JourneyMap UIs (Fullscreen, Minimap, Webmap) the overlay should be active in.
     *
     * @param activeUIs active UIs
     * @return this
     */
    public Overlay setActiveUIs(EnumSet<Context.UI> activeUIs)
    {
        if (activeUIs.contains(Context.UI.Any))
        {
            activeUIs = EnumSet.of(Context.UI.Any);
        }
        this.activeUIs = activeUIs;
        return this;
    }

    /**
     * Returns a set of enums indicating which map types (Day, Night) the overlay should be active in.
     *
     * @return enumset
     */
    public EnumSet<Context.MapType> getActiveMapTypes()
    {
        return activeMapTypes;
    }

    /**
     * Set of enums indicating which JourneyMap map types (Day, Night) the overlay should be active in.
     *
     * @param activeMapTypes active types
     * @return this
     */
    public Overlay setActiveMapTypes(EnumSet<Context.MapType> activeMapTypes)
    {
        if (activeMapTypes.contains(Context.MapType.Any))
        {
            activeMapTypes = EnumSet.of(Context.MapType.Any);
        }
        this.activeMapTypes = activeMapTypes;
        return this;
    }

    /**
     * Whether the overlay should be active for the given contexts.
     *
     * @param uiState    UIState
     * @return true if the overlay should be active
     */
    public boolean isActiveIn(UIState uiState)
    {
        return ((uiState.active && this.dimension == uiState.dimension)
                && (activeUIs.contains(Context.UI.Any) || activeUIs.contains(uiState.ui))
                && (activeMapTypes.contains(Context.MapType.Any) || activeMapTypes.contains(uiState.mapType))
                && (this.minZoom <= uiState.zoom && this.maxZoom >= uiState.zoom));
    }

    /**
     * Gets the listener for user events on the overlay.
     *
     * @return listener impl or null
     */
    public IOverlayListener getOverlayListener()
    {
        return overlayListener;
    }

    /**
     * Sets a listener for receiving user events related to the Overlay.
     *
     * @param overlayListener IOverlayListener impl
     * @return this
     */
    public Overlay setOverlayListener(@Nullable IOverlayListener overlayListener)
    {
        this.overlayListener = overlayListener;
        return this;
    }

    /**
     * Indicate the overlay needs to be re-rendered. Typically you don't need to use this unless you
     * are updating an overlay dynamically and the chances aren't shown until the map is panned
     * or zoomed. For example within and IOverlayListener. Overusing this can cause performance problems.
     */
    public void flagForRerender()
    {
        needsRerender = true;
    }

    /**
     * Used by JourneyMap after the overlay has been re-rendered.
     */
    public void clearFlagForRerender()
    {
        needsRerender = false;
    }

    /**
     * Gets whether the overlay needs to be re-rendered.
     *
     * @return true if rerender needed
     */
    public boolean getNeedsRerender()
    {
        return needsRerender;
    }

    /**
     * Provides common output for toStringHelper() to subclasses
     *
     * @param instance subclass
     * @return helper for continuing to add properties on subclass
     */
    protected final MoreObjects.ToStringHelper toStringHelper(Overlay instance)
    {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .add("title", title)
                .add("overlayGroupName", overlayGroupName)
                .add("activeMapTypes", activeMapTypes)
                .add("activeUIs", activeUIs)
                .add("dimension", dimension)
                .add("displayOrder", displayOrder)
                .add("maxZoom", maxZoom)
                .add("minZoom", minZoom)
                .add("textProperties", textProperties)
                .add("hasOverlayListener", overlayListener!=null);
    }
}
