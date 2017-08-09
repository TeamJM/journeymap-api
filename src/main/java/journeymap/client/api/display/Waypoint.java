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
import com.google.common.base.Objects;
import com.google.common.primitives.Ints;
import com.google.gson.annotations.Since;
import journeymap.client.api.model.WaypointBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Definition for a waypoint that is offered to a player.
 * <p>
 * Setters use the Builder pattern so they can be chained.
 * <p>
 * Note that like all Displayables, simply changing this object doesn't guarantee the player will get the changes.
 * You must call {@link journeymap.client.api.IClientAPI#show(Displayable)} in order for the changes to take effect
 * in JourneyMap.
 */
public class Waypoint extends WaypointBase<Waypoint>
{
    public static final double VERSION = 1.4;

    protected final transient CachedDimPosition cachedDimPosition = new CachedDimPosition();
    @Since(1.4)
    protected final double version = VERSION;
    @Since(1.4)
    protected int dim;
    @Since(1.4)
    protected BlockPos pos;
    @Since(1.4)
    protected WaypointGroup group;
    @Since(1.4)
    protected boolean persistent = true;
    @Since(1.4)
    protected boolean editable = true;

    /**
     * Constructor.
     *
     * @param modId Your mod id
     * @param name  Waypoint name
     */
    public Waypoint(String modId, String name, int dimension, BlockPos position)
    {
        super(modId, name);
        setPosition(dimension, position);
    }

    /**
     * Constructor.
     *
     * @param modId Your mod id
     * @param id    Unique id scoped to mod
     * @param name  Waypoint name
     */
    public Waypoint(String modId, String id, String name, int dimension, BlockPos position)
    {
        super(modId, id, name);
        setPosition(dimension, position);
    }

    /**
     * (Optional) Group or category name for the waypoint.
     */
    public final WaypointGroup getGroup()
    {
        return group;
    }

    /**
     * Sets the waypoint group.
     *
     * @param group the group
     * @return this
     */
    public Waypoint setGroup(@Nullable WaypointGroup group)
    {
        this.group = group;
        return setDirty();
    }

    public final int getDimension()
    {
        return dim;
    }

    /**
     * Waypoint location.
     */
    public final BlockPos getPosition()
    {
        return pos;
    }

    /**
     * Gets block position within the specified dimension
     *
     * @return the block pos
     */
    public BlockPos getPosition(int targetDimension)
    {
        return cachedDimPosition.getPosition(targetDimension);
    }

    /**
     * Gets block position within the specified dimension (not cached)
     *
     * @return the block pos
     */
    private BlockPos getInternalPosition(int targetDimension)
    {
        if (this.dim != targetDimension)
        {
            if (this.dim == -1)
            {
                // Convert coords to 8x horizontal scale outside of the Nether
                pos = new BlockPos(pos.getX() * 8, pos.getY(), pos.getZ() * 8);
            }
            else if (targetDimension == -1)
            {
                // Convert coords to 1/8 horizontal scale for display in the Nether
                pos = new BlockPos(pos.getX() / 8.0, pos.getY(), pos.getZ() / 8.0);
            }
        }
        return pos;
    }

    /**
     * Sets the waypoint location.
     *
     * @param position the BlockPos
     * @return this
     */
    public Waypoint setPosition(int dimension, BlockPos position)
    {
        if (position == null)
        {
            throw new IllegalArgumentException("position may not be null");
        }
        this.dim = dimension;
        this.pos = position;
        this.cachedDimPosition.reset();
        return setDirty();
    }

    /**
     * Gets Vec3D position relative to dimension.
     * Caches the result.
     *
     * @return the position
     */
    public Vec3d getVec(int dimension)
    {
        return this.cachedDimPosition.getVec(dimension);
    }

    /**
     * Gets block-centered position as a Vec3D
     *
     * @return the position
     */
    public Vec3d getCenteredVec(int dimension)
    {
        return this.cachedDimPosition.getCenteredVec(dimension);
    }

    /**
     * Whether or not the waypoint should be persisted (saved to file)
     * after the player disconnects from the world or changes displayDims.
     *
     * @return true if persistent
     */
    public final boolean isPersistent()
    {
        return this.persistent;
    }

    /**
     * Sets whether or not the waypoint should be persisted (saved to file)
     * after the player disconnects from the world or changes displayDims.
     *
     * @param persistent true if save to file
     * @return this
     */
    public final Waypoint setPersistent(boolean persistent)
    {
        this.persistent = persistent;
        if (!persistent)
        {
            dirty = false;
        }
        return setDirty();
    }

    /**
     * Whether the player can edit the waypoint.
     *
     * @return true if editable
     */
    public final boolean isEditable()
    {
        return editable;
    }

    /**
     * Sets whether the player can edit the waypoint.
     *
     * @param editable is editable
     * @return this
     */
    public final Waypoint setEditable(boolean editable)
    {
        this.editable = editable;
        return setDirty();
    }

    /**
     * Safe to teleport?
     *
     * @return true if yes
     */
    public final boolean isTeleportReady(int targetDimension)
    {
        BlockPos pos = getPosition(targetDimension);
        return pos != null && pos.getY() >= 0;
    }

    @Override
    protected WaypointGroup getDelegate()
    {
        return getGroup();
    }

    @Override
    protected boolean hasDelegate()
    {
        return group != null;
    }

    /**
     * Dimensions where this should be displayed.
     * Auto-sets to the waypoint dimension if not
     * specified.
     */
    @Override
    public int[] getDisplayDimensions()
    {
        int[] dims = super.getDisplayDimensions();
        if (dims == null)
        {
            setDisplayDimensions(dim);
        }
        return displayDims;
    }

    @Override
    public int getDisplayOrder()
    {
        return (group != null) ? group.getDisplayOrder() : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Waypoint))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        Waypoint that = (Waypoint) o;
        return isPersistent() == that.isPersistent() &&
                isEditable() == that.isEditable() &&
                Objects.equal(getDimension(), that.getDimension()) &&
                Objects.equal(getColor(), that.getColor()) &&
                Objects.equal(getBackgroundColor(), that.getBackgroundColor()) &&
                Objects.equal(getName(), that.getName()) &&
                Objects.equal(getPosition(), that.getPosition()) &&
                Objects.equal(getIcon(), that.getIcon()) &&
                Arrays.equals(getDisplayDimensions(), that.getDisplayDimensions());
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), getName());
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("dim", dim)
                .add("pos", pos)
                .add("group", group)
                .add("icon", icon)
                .add("color", color)
                .add("bgColor", bgColor)
                .add("displayDims", displayDims == null ? null : Ints.asList(displayDims))
                .add("editable", editable)
                .add("persistent", persistent)
                .add("dirty", dirty)
                .toString();
    }

    /**
     * Caches frequently-used positions/vectors within a dimension,
     * rather than calculating them on every use.
     */
    class CachedDimPosition
    {
        Integer cachedDim;
        BlockPos cachedPos;
        Vec3d cachedVec;
        Vec3d cachedCenteredVec;

        CachedDimPosition()
        {
        }

        /**
         * Reset cached values.
         */
        CachedDimPosition reset()
        {
            cachedDim = null;
            cachedPos = null;
            cachedVec = null;
            cachedCenteredVec = null;
            return this;
        }

        /**
         * Ensure cached values are relative to the requested dimension.
         */
        private CachedDimPosition ensure(int dimension)
        {
            if (this.cachedDim != dimension)
            {
                this.cachedDim = dimension;
                this.cachedPos = Waypoint.this.getInternalPosition(dimension);
                this.cachedVec = new Vec3d(this.cachedPos.getX(), this.cachedPos.getY(), this.cachedPos.getZ());
                this.cachedCenteredVec = this.cachedVec.addVector(.5, .5, .5);
            }
            return this;
        }

        /**
         * Gets position relative to dimension.
         *
         * @param dimension targetDimension
         * @return position
         */
        public BlockPos getPosition(int dimension)
        {
            return ensure(dimension).cachedPos;
        }

        /**
         * Gets Vec3D position relative to dimension.
         *
         * @param dimension targetDimension
         * @return position
         */
        public Vec3d getVec(int dimension)
        {
            return ensure(dimension).cachedVec;
        }

        /**
         * Gets block-centered position as a Vec3D
         *
         * @return the position
         */
        public Vec3d getCenteredVec(int dimension)
        {
            return ensure(dimension).cachedCenteredVec;
        }
    }
}
