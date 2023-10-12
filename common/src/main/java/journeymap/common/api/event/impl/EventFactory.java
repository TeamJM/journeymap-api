package journeymap.common.api.event.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventFactory
{
    static final Map<Class<?>, List<EventImpl<?>>> EVENTS;

    static
    {
        EVENTS = new HashMap<>();
    }

    public static <E> Event<E> create(Class<E> clazz)
    {
        EventImpl<E> impl = new EventImpl<>(clazz);
        EVENTS.putIfAbsent(clazz, List.of(impl));
        return impl;
    }
}


