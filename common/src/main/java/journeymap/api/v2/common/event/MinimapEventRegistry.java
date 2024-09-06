package journeymap.api.v2.common.event;

import journeymap.api.v2.client.event.InfoSlotDisplayEvent;
import journeymap.api.v2.common.event.impl.Event;
import journeymap.api.v2.common.event.impl.EventFactory;

public class MinimapEventRegistry
{

    /**
     * This event allows addons to add, update, remove, replace current info slots. It will allow to force display your own slot anywhere in the displayed lists.
     * Four info slots not enough? This event can add unlimited slots!
     */
    public static final Event<InfoSlotDisplayEvent> INFO_SLOT_DISPLAY_EVENT = EventFactory.create(InfoSlotDisplayEvent.class);

}
