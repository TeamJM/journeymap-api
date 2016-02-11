package journeymap.client.api.util;

import com.google.common.base.Objects;
import journeymap.client.api.display.Context;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import javax.annotation.Nullable;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
     * The center pixel of the UI.  If active==false, this will be null.
     */
    public final Point2D.Double displayCenter;

    /**
     * The screen area (pixels) used by the UI.  If active==false, this will be null.
     */
    public final Rectangle2D.Double displayBounds;


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
                   @Nullable AxisAlignedBB blockBounds,
                   @Nullable Point2D.Double displayCenter,
                   @Nullable Rectangle2D.Double displayBounds)
    {
        this.ui = ui;
        this.active = active;
        this.dimension = dimension;
        this.zoom = zoom;
        this.mapType = mapType;
        this.mapCenter = mapCenter;
        this.blockBounds = blockBounds;
        this.displayCenter = displayCenter;
        this.displayBounds = displayBounds;
    }

    /**
     * Convenience factory method to create an inactive UIState.
     *
     * @param ui the ui
     * @return a UIState
     */
    public static UIState inactive(Context.UI ui)
    {
        return new UIState(ui, false, 0, 0, null, null, null, null, null);
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
                Objects.equal(displayCenter, mapState.displayCenter) &&
                Objects.equal(displayBounds, mapState.displayBounds);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(ui, active, dimension, zoom, mapType, displayCenter, displayBounds);
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
                .add("displayCenter", displayCenter)
                .add("displayBounds", displayBounds)
                .toString();
    }


}
