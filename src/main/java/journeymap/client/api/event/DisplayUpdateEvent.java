package journeymap.client.api.event;

import com.google.common.base.Objects;
import journeymap.client.api.util.MapState;

/**
 * Indicates a change in the display characteristics of the specified UI.
 * Cannot be cancelled.
 */
public class DisplayUpdateEvent extends ClientEvent
{
    /**
     * The MapState of the UI which has been updated.
     */
    public final MapState mapState;

    /**
     * Constructor
     *
     * @param mapState    The MapState of the UI which has been updated.
     */
    public DisplayUpdateEvent(MapState mapState)
    {
        super(Type.DISPLAY_UPDATE, mapState.dimension);
        this.mapState = mapState;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this)
                .add("mapState", mapState)
                .toString();
    }
}
