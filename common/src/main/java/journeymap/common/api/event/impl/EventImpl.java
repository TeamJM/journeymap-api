package journeymap.common.api.event.impl;

import java.util.ArrayList;
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
    public Class<E> getEventClass()
    {
        return clazz;
    }

    public List<Listener<E>> getListeners()
    {
        return listeners;
    }

    public record Listener<T>(String modId, Consumer<T> listener)
    {
    }

}
