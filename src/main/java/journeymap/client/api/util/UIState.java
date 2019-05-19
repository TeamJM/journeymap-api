package journeymap.client.api.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import journeymap.client.api.display.Context;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
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
     * Whether the UI is active or not. If false, other values reflect the
     * state of the display when it was last used.
     */
    public final boolean active;

    /**
     * The dimension displayed in the UI.  If active==false and the display
     * has never been used, this will default to 0.
     */
    public final int dimension;

    /**
     * The current zoom level of the UI. If active==false and the display
     * has never been used, this will default to 0.
     */
    public final int zoom;

    /**
     * The current map type of the UI. If active==false and the display
     * has never been used, this will default to Context.MapType.Day.
     */
    public final Context.MapType mapType;

    /**
     * The block position at the center of the UI. If active==false and the display
     * has never been used, this will default to the world spawnpoint.
     */
    public final BlockPos mapCenter;

    /**
     * For underground/cave/nether/end maps, the vertical slice (chunk y) displayed.
     */
    public final Integer chunkY;

    /**
     * The area of blocks displayed in the UI. If active==false, this will be null.
     */
    public final AxisAlignedBB blockBounds;

    /**
     * The screen area (pixels) used by the UI.  If active==false, this will be null.
     */
    public final Rectangle2D.Double displayBounds;

    /**
     * The width in pixels of a single block in the UI's map at the current zoom level.
     */
    public final double blockSize;


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
                   @Nullable Integer chunkY,
                   @Nullable AxisAlignedBB blockBounds,
                   @Nullable Rectangle2D.Double displayBounds)
    {
        this.ui = ui;
        this.active = active;
        this.dimension = dimension;
        this.zoom = zoom;
        this.mapType = mapType;
        this.mapCenter = mapCenter;
        this.chunkY = chunkY;
        this.blockBounds = blockBounds;
        this.displayBounds = displayBounds;
        this.blockSize = Math.pow(2, zoom);
    }

    /**
     * Convenience factory method to create an inactive UIState.
     *
     * @param ui the ui
     * @return a UIState
     */
    public static UIState newInactive(Context.UI ui, Minecraft minecraft)
    {
        BlockPos center = minecraft.world == null ? new BlockPos(0, 68, 0) : minecraft.world.getSpawnPoint();
        return new UIState(ui, false, 0, 0, Context.MapType.Day, center, null, null, null);
    }

    /**
     * Convenience factory method to create an inactive UIState.
     *
     * @param priorState the prior UIState
     * @return a UIState
     */
    public static UIState newInactive(UIState priorState)
    {
        return new UIState(priorState.ui, false, priorState.dimension, priorState.zoom, priorState.mapType,
                priorState.mapCenter, priorState.chunkY, priorState.blockBounds, priorState.displayBounds);
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
                Objects.equal(displayBounds, mapState.displayBounds);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(ui, active, dimension, zoom, mapType, displayBounds);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("ui", ui)
                .add("active", active)
                .add("dimension", dimension)
                .add("mapType", mapType)
                .add("zoom", zoom)
                .add("mapCenter", mapCenter)
                .add("chunkY", chunkY)
                .add("blockBounds", blockBounds)
                .add("displayBounds", displayBounds)
                .add("blockSize", blockSize)
                .toString();
    }


}
