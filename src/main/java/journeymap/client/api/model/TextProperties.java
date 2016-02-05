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

import com.google.common.base.Objects;
import journeymap.client.api.display.Context;
import journeymap.client.api.display.Displayable;

import java.util.EnumSet;

/**
 * Properties defining the display of text.
 * <p/>
 * Setters use the Builder pattern so they can be chained.
 */
public class TextProperties
{
    protected EnumSet<Context.UI> activeUIs = EnumSet.of(Context.UI.Any);
    private float scale = 1;
    private int color = 0xffffff;
    private int backgroundColor = 0x000000;
    private float opacity = 1f;
    private float backgroundOpacity = .5f;
    private boolean fontShadow = true;

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
     * <p/>
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
     * <p/>
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof TextProperties))
        {
            return false;
        }
        TextProperties that = (TextProperties) o;
        return Objects.equal(activeUIs, that.activeUIs) &&
                Objects.equal(scale, that.scale) &&
                Objects.equal(color, that.color) &&
                Objects.equal(backgroundColor, that.backgroundColor) &&
                Objects.equal(opacity, that.opacity) &&
                Objects.equal(backgroundOpacity, that.backgroundOpacity);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(scale, color, backgroundColor, opacity, backgroundOpacity);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("backgroundColor", backgroundColor)
                .add("backgroundOpacity", backgroundOpacity)
                .add("color", color)
                .add("opacity", opacity)
                .add("scale", scale)
                .toString();
    }

}
