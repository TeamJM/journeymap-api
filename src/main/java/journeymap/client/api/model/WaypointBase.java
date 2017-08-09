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
import com.google.common.base.Strings;
import com.google.gson.annotations.Since;
import journeymap.client.api.display.Displayable;
import journeymap.client.api.display.IWaypointDisplay;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Internal use only.  Mods should not extend this class.
 */
public abstract class WaypointBase<T extends WaypointBase> extends Displayable implements IWaypointDisplay
{
    @Since(1.4)
    protected String name;

    @Since(1.4)
    protected Integer color;

    @Since(1.4)
    protected Integer bgColor;

    @Since(1.4)
    protected MapImage icon;

    @Since(1.4)
    protected int[] displayDims;

    @Since(1.4)
    protected transient boolean dirty;

    /**
     * Constructor.
     *
     * @param modId Your mod id
     * @param name  Display name
     */
    protected WaypointBase(String modId, String name)
    {
        super(modId);
        setName(name);
    }

    /**
     * Constructor.
     *
     * @param modId Your mod id
     * @param id    Unique id scoped to mod
     * @param name  Display name
     */
    protected WaypointBase(String modId, String id, String name)
    {
        super(modId, id);
        setName(name);
    }

    /**
     * Gets a delegate for this object, if one exists.
     *
     * @return delegate or null
     */
    protected abstract IWaypointDisplay getDelegate();

    /**
     * Whether this has a delegate.
     *
     * @return true if delegate exists.
     */
    protected abstract boolean hasDelegate();

    /**
     * Waypoint name.
     */
    public final String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     * @return this
     */
    public final T setName(String name)
    {
        if (Strings.isNullOrEmpty(name))
        {
            throw new IllegalArgumentException("name may not be blank");
        }
        this.name = name;
        return setDirty();
    }

    /**
     * Color for label.
     *
     * @return rgb int
     */
    public final Integer getColor()
    {
        if (color == null && hasDelegate())
        {
            return getDelegate().getColor();
        }
        return color;
    }

    /**
     * Sets the rgb color (between 0x000000 - 0xffffff) of the name.
     *
     * @param color the color
     * @return this
     */
    public final T setColor(int color)
    {
        this.color = clampRGB(color);
        return setDirty();
    }

    /**
     * Clears color on this to ensure
     * delegate provides it on subsequent calls.
     *
     * @return this
     */
    public final T clearColor()
    {
        this.color = null;
        return setDirty();
    }

    /**
     * Background color for label.
     *
     * @return rgb int
     */
    public final Integer getBackgroundColor()
    {
        if (bgColor == null && hasDelegate())
        {
            return getDelegate().getBackgroundColor();
        }
        return bgColor;
    }


    /**
     * Sets the rgb color (between 0x000000 - 0xffffff) of the name's background.
     *
     * @param bgColor the color
     * @return this
     */
    public final T setBackgroundColor(int bgColor)
    {
        this.bgColor = clampRGB(bgColor);
        return setDirty();
    }

    /**
     * Clears backgroundColor on this to ensure
     * delegate provides it on subsequent calls.
     *
     * @return this
     */
    public final T clearBackgroundColor()
    {
        this.bgColor = null;
        return setDirty();
    }

    /**
     * Dimensions where this should be displayed.
     */
    public int[] getDisplayDimensions()
    {
        if (displayDims == null && hasDelegate())
        {
            return getDelegate().getDisplayDimensions();
        }
        return displayDims;
    }

    /**
     * Sets the displayDims in which this should appear.
     *
     * @param dimensions the displayDims
     * @return this
     */
    public final T setDisplayDimensions(int... dimensions)
    {
        this.displayDims = dimensions;
        return setDirty();
    }

    /**
     * Clears displayDimensions on this to ensure
     * delegate provides them on subsequent calls.
     *
     * @return this
     */
    public final T clearDisplayDimensions()
    {
        this.displayDims = null;
        return setDirty();
    }

    /**
     * Sets whether to display in a given dimension.
     *
     * @param dimension dim id
     * @param displayed true to display
     */
    public void setDisplayed(int dimension, boolean displayed)
    {
        if (displayed && !isDisplayed(dimension))
        {
            setDisplayDimensions(ArrayUtils.add(getDisplayDimensions(), dimension));
        }
        else if (!displayed && isDisplayed(dimension))
        {
            setDisplayDimensions(ArrayUtils.removeElement(getDisplayDimensions(), dimension));
        }
    }

    /**
     * Whether the waypoint is shown in the dimension.
     *
     * @param dimension dim id
     * @return true if dim id is in getDisplayDimensions()
     */
    public final boolean isDisplayed(int dimension)
    {
        return Arrays.binarySearch(getDisplayDimensions(), dimension) > -1;
    }

    /**
     * Icon specification for waypoint.
     *
     * @return spec
     */
    public MapImage getIcon()
    {
        if (icon == null && hasDelegate())
        {
            return getDelegate().getIcon();
        }
        return icon;
    }

    /**
     * Sets the icon.
     *
     * @param icon the icon
     * @return this
     */
    public final T setIcon(@Nullable MapImage icon)
    {
        this.icon = icon;
        return setDirty();
    }

    /**
     * Clears icon on this to ensure
     * delegate provides it on subsequent calls.
     *
     * @return this
     */
    public final T clearIcon()
    {
        this.icon = null;
        return setDirty();
    }

    /**
     * Whether needs to be saved.
     *
     * @return
     */
    public boolean isDirty()
    {
        return dirty;
    }

    /**
     * Sets dirty.
     *
     * @param dirty the dirty
     * @return the dirty
     */
    public T setDirty(boolean dirty)
    {
        this.dirty = dirty;
        return (T) this;
    }

    /**
     * Set state as needing to be saved.
     *
     * @return the dirty
     */
    public T setDirty()
    {
        return setDirty(true);
    }

    /**
     * Whether this has an icon set.  Returns
     * false if not, even if delegate exists
     * and would provide an icon.
     *
     * @return true if set
     */
    public boolean hasIcon()
    {
        return icon != null;
    }

    /**
     * Whether this has a color set.  Returns
     * false if not, even if delegate exists
     * and would provide a color.
     *
     * @return true if set
     */
    public boolean hasColor()
    {
        return color != null;
    }

    /**
     * Whether this has a background color set.  Returns
     * false if not, even if delegate exists
     * and would provide a background color.
     *
     * @return true if set
     */
    public boolean hasBackgroundColor()
    {
        return bgColor != null;
    }

    /**
     * Whether this has display dimensions set.  Returns
     * false if not, even if delegate exists
     * and would provide a background color.
     *
     * @return true if set
     */
    public boolean hasDisplayDimensions()
    {
        return displayDims != null;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof WaypointBase))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        WaypointBase<?> that = (WaypointBase<?>) o;
        return Objects.equal(getName(), that.getName()) &&
                Objects.equal(getIcon(), that.getIcon()) &&
                Objects.equal(getColor(), that.getColor()) &&
                Objects.equal(getBackgroundColor(), that.getBackgroundColor()) &&
                Arrays.equals(getDisplayDimensions(), that.getDisplayDimensions());
    }
}
