package journeymap.common.api.event.impl;

import java.util.HashMap;
import java.util.Map;

public class EventFactory<T extends JourneyMapEvent>
{
    private final Map<Class<T>, EventImpl<T>> events;

    private static final EventFactory INSTANCE;

    static
    {
        INSTANCE = new EventFactory<>();
    }

    public EventFactory()
    {
        this.events = new HashMap<>();
    }

    public static <E> Event<E> create(Class<E> clazz)
    {
        EventImpl<E> impl = new EventImpl<>(clazz);
        INSTANCE.events.putIfAbsent(clazz, impl);
        return impl;
    }

    public Map<Class<T>, EventImpl<T>> getEvents()
    {
        return this.events;
    }

    public static <K extends JourneyMapEvent> EventFactory<K> getInstance()
    {
        return INSTANCE;
    }
}


