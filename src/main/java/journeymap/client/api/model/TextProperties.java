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

package journeymap.client.api.model;

import com.google.common.base.MoreObjects;
import journeymap.client.api.display.Context;
import journeymap.client.api.display.Displayable;
import journeymap.client.api.util.UIState;

import java.util.EnumSet;

/**
 * Properties defining the display of text.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 */
public class TextProperties
{
    protected EnumSet<Context.UI> activeUIs = EnumSet.of(Context.UI.Any);
    protected EnumSet<Context.MapType> activeMapTypes = EnumSet.of(Context.MapType.Any);
    protected float scale = 1;
    protected int color = 0xffffff;
    protected int backgroundColor = 0x000000;
    protected float opacity = 1f;
    protected float backgroundOpacity = .5f;
    protected boolean fontShadow = true;
    protected int minZoom = 0;
    protected int maxZoom = 8;
    protected int offsetX = 0;
    protected int offsetY = 0;

    /**
     * Font scale.
     *
     * @return 1 scale
     */
    public float getScale()
    {
        return scale;
    }

    /**
     * Sets the font scale. Best results are powers of 2: 1,2,4,8.
     * Range is 1f - 8f;
     *
     * @param scale the scale
     * @return this
     */
    public TextProperties setScale(float scale)
    {
        this.scale = Math.max(1f, Math.min(scale, 8f));
        return this;
    }

    /**
     * Gets the font color.
     *
     * @return rgb color
     */
    public int getColor()
    {
        return color;
    }

    /**
     * Sets the font color (rgb).  Range is 0x000000 - 0xffffff.
     *
     * @param color rgb
     * @return this
     */
    public TextProperties setColor(int color)
    {
        this.color = Displayable.clampRGB(color);
        return this;
    }

    /**
     * Gets background color.
     *
     * @return the background color
     */
    public int getBackgroundColor()
    {
        return backgroundColor;
    }

    /**
     * Sets background color.
     *
     * @param backgroundColor the background color
     * @return this
     */
    public TextProperties setBackgroundColor(int backgroundColor)
    {
        this.backgroundColor = Displayable.clampRGB(backgroundColor);
        return this;
    }

    /**
     * Gets opacity.
     *
     * @return the opacity
     */
    public float getOpacity()
    {
        return opacity;
    }

    /**
     * Sets opacity.
     *
     * @param opacity the opacity
     * @return this
     */
    public TextProperties setOpacity(float opacity)
    {
        this.opacity = Displayable.clampOpacity(opacity);
        return this;
    }

    /**
     * Gets background opacity.
     *
     * @return the background opacity
     */
    public float getBackgroundOpacity()
    {
        return backgroundOpacity;
    }

    /**
     * Sets background opacity.  Range is 0f - 1f.
     *
     * @param backgroundOpacity the background opacity
     * @return this
     */
    public TextProperties setBackgroundOpacity(float backgroundOpacity)
    {
        this.backgroundOpacity = Displayable.clampOpacity(backgroundOpacity);
        return this;
    }

    /**
     * Whether font shadow should be used.
     *
     * @return true if shadowed
     */
    public boolean hasFontShadow()
    {
        return fontShadow;
    }

    /**
     * Sets whether font shadow should be used.
     *
     * @param fontShadow true if shadow
     * @return this
     */
    public TextProperties setFontShadow(boolean fontShadow)
    {
        this.fontShadow = fontShadow;
        return this;
    }

    /**
     * Returns a set of enums indicating which JourneyMap UIs (Fullscreen, Minimap, Webmap)
     * the text should be displayed in.  This is only checked if the overlay containing these
     * text properties is already active.
     * <p>
     * For example, this can be specified to have labels only displayed in the fullscreen map, but not the minimap.
     * @return enumset
     */
    public EnumSet<Context.UI> getActiveUIs()
    {
        return activeUIs;
    }

    /**
     * Set of enums indicating which JourneyMap UIs (Fullscreen, Minimap, Webmap) the text should be displayed in.
     * This is only checked if the overlay containing these text properties is already active.
     * <p>
     * For example, this can be specified to have labels only displayed in the fullscreen map, but not the minimap.
     * @param activeUIs active UIs
     * @return this
     */
    public TextProperties setActiveUIs(EnumSet<Context.UI> activeUIs)
    {
        if (activeUIs.contains(Context.UI.Any))
        {
            activeUIs = EnumSet.of(Context.UI.Any);
        }
        this.activeUIs = activeUIs;
        return this;
    }

    /**
     * Returns a set of enums indicating which map types (Day, Night) the text should be active in.
     *
     * @return enumset
     */
    public EnumSet<Context.MapType> getActiveMapTypes()
    {
        return activeMapTypes;
    }

    /**
     * Set of enums indicating which JourneyMap map types (Day, Night) the text should be active in.
     *
     * @param activeMapTypes active types
     * @return this
     */
    public TextProperties setActiveMapTypes(EnumSet<Context.MapType> activeMapTypes)
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
     * @param uiState UIState
     * @return true if the overlay should be active
     */
    public boolean isActiveIn(UIState uiState)
    {
        return uiState.active
                && (activeUIs.contains(Context.UI.Any) || activeUIs.contains(uiState.ui))
                && (activeMapTypes.contains(Context.MapType.Any) || activeMapTypes.contains(uiState.mapType))
                && (this.minZoom <= uiState.zoom && this.maxZoom >= uiState.zoom);
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
     * Sets the minimum zoom level (0 is lowest) where text should be visible.
     *
     * @param minZoom the min zoom
     * @return this
     */
    public TextProperties setMinZoom(int minZoom)
    {
        this.minZoom = Math.max(0, minZoom);
        return this;
    }

    /**
     * The maximum zoom level (8 is highest) where text should be visible.
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
    public TextProperties setMaxZoom(int maxZoom)
    {
        this.maxZoom = Math.min(8, maxZoom);
        return this;
    }

    /**
     * Gets how many horizontal pixels to shift the center of the label from the center of the overlay.
     * (For MarkerOverlays, the "center" is directly over MarkerOverlay.getPoint(), regardless of how
     * it's icon is placed.)
     *
     * @return pixels to offset
     */
    public int getOffsetX()
    {
        return offsetX;
    }

    /**
     * Sets how many horizontal pixels to shift the center of the label from the center of the overlay.
     * (For MarkerOverlays, the "center" is directly over MarkerOverlay.getPoint(), regardless of how
     * it's icon is placed.)
     *
     * @param offsetX
     * @return this
     */
    public TextProperties setOffsetX(int offsetX)
    {
        this.offsetX = offsetX;
        return this;
    }

    /**
     * Gets how many vertical pixels to shift the center of the label from the center of the overlay.
     * (For MarkerOverlays, the "center" is directly over MarkerOverlay.getPoint(), regardless of how
     * it's icon is placed.)
     *
     * @return pixels to offset
     */
    public int getOffsetY()
    {
        return offsetY;
    }

    /**
     * Sets how many vertical pixels to shift the center of the label from the center of the overlay.
     * (For MarkerOverlays, the "center" is directly over MarkerOverlay.getPoint(), regardless of how
     * it's icon is placed.)
     *
     * @param offsetY
     * @return this
     */
    public TextProperties setOffsetY(int offsetY)
    {
        this.offsetY = offsetY;
        return this;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("activeMapTypes", activeMapTypes)
                .add("activeUIs", activeUIs)
                .add("backgroundColor", backgroundColor)
                .add("backgroundOpacity", backgroundOpacity)
                .add("color", color)
                .add("opacity", opacity)
                .add("fontShadow", fontShadow)
                .add("maxZoom", maxZoom)
                .add("minZoom", minZoom)
                .add("offsetX", offsetX)
                .add("offsetY", offsetY)
                .add("scale", scale)
                .toString();
    }
}
