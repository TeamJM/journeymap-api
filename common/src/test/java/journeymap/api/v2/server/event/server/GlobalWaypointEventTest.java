package journeymap.api.v2.server.event.server;

import journeymap.api.v2.server.event.GlobalWaypointEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GlobalWaypointEventTest
{
    @Test
    void createIsCancelable()
    {
        GlobalWaypointEvent event = new GlobalWaypointEvent(null, GlobalWaypointEvent.Context.CREATE);
        assertTrue(event.isCancellable());
    }

    @Test
    void updateIsCancelable()
    {
        GlobalWaypointEvent event = new GlobalWaypointEvent(null, GlobalWaypointEvent.Context.UPDATE);
        assertTrue(event.isCancellable());
    }

    @Test
    void deletedIsNotCancelable()
    {
        GlobalWaypointEvent event = new GlobalWaypointEvent(null, GlobalWaypointEvent.Context.DELETED);
        assertFalse(event.isCancellable());
    }
}
