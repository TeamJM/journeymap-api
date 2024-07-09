package journeymap.api.v2.common.event.impl;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Consumer;

public class EventImpl<E> implements Event<E>
{
    private final Class<E> clazz;

    private final List<Listener<E>> listeners = new ArrayList<>();

    public EventImpl(Class<E> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public void subscribe(String modId, Consumer<E> listener)
    {
        listeners.add(new Listener<>(modId, listener));
    }

    @Override
    public void subscribe(Object subscriber, String modId, Consumer<E> listener)
    {
        listeners.add(new Listener<>(modId, listener, subscriber.getClass()));
    }

    @Override
    public void unsubscribe(Object subscriber, String modId) throws ConcurrentModificationException
    {
        listeners.removeIf(listener -> subscriber.getClass().equals(listener.subscribingClass()) && modId.equals(listener.modId()));
    }

    public Class<E> getEventClass()
    {
        return clazz;
    }

    public List<Listener<E>> getListeners()
    {
        return listeners;
    }

    public record Listener<T>(String modId, Consumer<T> listener, Class<?> subscribingClass)
    {
        public Listener(String modId, Consumer<T> listener)
        {
            this(modId, listener, null);
        }
    }

}
