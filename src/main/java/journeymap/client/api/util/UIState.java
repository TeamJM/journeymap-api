package journeymap.client.api.util;

import com.google.common.base.Objects;
import journeymap.client.api.display.Context;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import javax.annotation.Nullable;

/**
 * Provides the current state of a Map UI in JourneyMap.
 */
public final class UIState
{
    /**
     * The UI to which this state applies.
     */
    public final Context.UI ui;

    /**
     * Whether the UI is active or not.
     */
    public final boolean active;

    /**
     * The dimension displayed in the UI.  If active==false, this value is meaningless.
     */
    public final int dimension;

    /**
     * The current zoom level of the UI. If active==false, this value is meaningless.
     */
    public final int zoom;

    /**
     * The current map type of the UI. If active==false, this will be null.
     */
    public final Context.MapType mapType;

    /**
     * The block position at the center of the UI. If active==false, this will be null.
     */
    public final BlockPos mapCenter;

    /**
     * The area of blocks displayed in the UI. If active==false, this will be null.
     */
    public final AxisAlignedBB blockBounds;

    /**
     * Constructor.
     *
     * @param ui          The UI which has been updated.
     * @param active      Whether the UI is active
     * @param dimension   The current dimension shown in the UI.
     * @param zoom        The current zoom level of the UI.
     * @param mapType     The current map type of the UI.
     * @param mapCenter   The block position at the center of the UI.
     * @param blockBounds The area of blocks displayed in the UI.
     */
    public UIState(Context.UI ui, boolean active, int dimension, int zoom,
                   @Nullable Context.MapType mapType,
                   @Nullable BlockPos mapCenter,
                   @Nullable AxisAlignedBB blockBounds)
    {
        this.ui = ui;
        this.active = active;
        this.dimension = dimension;
        this.zoom = zoom;
        this.mapType = mapType;
        this.mapCenter = mapCenter;
        this.blockBounds = blockBounds;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        UIState mapState = (UIState) o;
        return Objects.equal(active, mapState.active) &&
                Objects.equal(dimension, mapState.dimension) &&
                Objects.equal(zoom, mapState.zoom) &&
                Objects.equal(ui, mapState.ui) &&
                Objects.equal(mapType, mapState.mapType) &&
                Objects.equal(mapCenter, mapState.mapCenter) &&
                Objects.equal(blockBounds, mapState.blockBounds);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(ui, active, dimension, zoom, mapType, mapCenter, blockBounds);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("ui", ui)
                .add("active", active)
                .add("mapType", mapType)
                .add("zoom", zoom)
                .add("dimension", dimension)
                .add("mapCenter", mapCenter)
                .add("blockBounds", blockBounds)
                .toString();
    }


}
