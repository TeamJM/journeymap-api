package journeymap.api.v2.server.event.server;

import journeymap.api.v2.common.waypoint.Waypoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GlobalWaypointEventTest
{
    @Test
    void createIsCancelable()
    {
        var event = new GlobalWaypointEvent(null, GlobalWaypointEvent.Context.CREATE);
        assertTrue(event.isCancellable());
    }

    @Test
    void updateIsCancelable()
    {
        var event = new GlobalWaypointEvent(null, GlobalWaypointEvent.Context.UPDATE);
        assertTrue(event.isCancellable());
    }

    @Test
    void deletedIsNotCancelable()
    {
        var event = new GlobalWaypointEvent(null, GlobalWaypointEvent.Context.DELETED);
        assertFalse(event.isCancellable());
    }
}
