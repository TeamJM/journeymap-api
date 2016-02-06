package journeymap.client.api.event;

import com.google.common.base.Objects;
import journeymap.client.api.display.Context;
import net.minecraft.util.AxisAlignedBB;

/**
 * Indicates a change in the display characteristics of the specified UI.
 * Cannot be cancelled.
 */
public class DisplayUpdateEvent extends ClientEvent
{
    /**
     * The UI which has been updated.
     */
    public final Context.UI ui;

    /**
     * The current zoom level of the UI
     */
    public final int zoom;

    /**
     * The current map type of the UI.
     */
    public final Context.MapType mapType;

    /**
     * The current map layer of the UI.
     */
    public final Context.MapLayer mapLayer;

    /**
     * The area of blocks displayed in the UI.
     */
    public final AxisAlignedBB blockBounds;

    /**
     * Constructor
     *
     * @param ui          The UI which has been updated.
     * @param mapType     The current map type of the UI.
     * @param mapLayer    The current map layer of the UI.
     * @param blockBounds The area of blocks displayed in the UI.
     * @param dimension   The current dimension shown in the UI.
     * @param zoom        he current zoom level of the UI.
     */
    public DisplayUpdateEvent(Context.UI ui, Context.MapType mapType, Context.MapLayer mapLayer, AxisAlignedBB blockBounds, int dimension, int zoom)
    {
        super(Type.DISPLAY_UPDATE, dimension);
        this.zoom = zoom;
        this.ui = ui;
        this.mapType = mapType;
        this.mapLayer = mapLayer;
        this.blockBounds = blockBounds;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("ui", ui)
                .add("mapLayer", mapLayer)
                .add("mapType", mapType)
                .add("blockBounds", blockBounds)
                .add("zoom", zoom)
                .toString();
    }
}
