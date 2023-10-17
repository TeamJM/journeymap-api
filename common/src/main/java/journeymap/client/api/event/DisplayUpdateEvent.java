package journeymap.client.api.event;

import com.google.common.base.MoreObjects;
import journeymap.client.api.util.UIState;
import journeymap.common.api.event.impl.ClientEvent;

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
