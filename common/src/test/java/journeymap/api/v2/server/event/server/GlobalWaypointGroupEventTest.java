package journeymap.api.v2.server.event.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GlobalWaypointGroupEventTest
{
    @Test
    void createIsCancelable()
    {
        var event = new GlobalWaypointGroupEvent(null, GlobalWaypointGroupEvent.Context.CREATE);
        assertTrue(event.isCancellable());
    }

    @Test
    void deletedIsNotCancelable()
    {
        var event = new GlobalWaypointGroupEvent(null, GlobalWaypointGroupEvent.Context.DELETED);
        assertFalse(event.isCancellable());
    }
}
