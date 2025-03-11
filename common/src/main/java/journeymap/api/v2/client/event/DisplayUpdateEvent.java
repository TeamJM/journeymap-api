package journeymap.api.v2.client.event;

import com.google.common.base.MoreObjects;
import journeymap.api.v2.client.util.UIState;

/**
 * Indicates a change in the display characteristics of the specified UI.
 * Cannot be cancelled.
 */
public class DisplayUpdateEvent extends ClientEvent
{
    /**
     * The UIState of the UI which has been updated.
     */
    public final UIState uiState;

    /**
     * Constructor
     *
     * @param uiState    The UIState of the UI which has been updated.
     */
    public DisplayUpdateEvent(UIState uiState)
    {
        super(false, uiState.dimension);
        this.uiState = uiState;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("uiState", uiState)
                .toString();
    }
}
