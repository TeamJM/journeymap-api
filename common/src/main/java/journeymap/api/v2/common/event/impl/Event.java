package journeymap.api.v2.common.event.impl;

import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public interface Event<E>
{
    /**
     * Permanently Registered listeners.
     *
     * @param modId    - the mod id.
     * @param listener - the listener.
     */
    void subscribe(String modId, Consumer<E> listener);

    /**
     * When using this method, you can unsubscribe to this event.
     *
     * @param subscriber - the subscribing object, used as the key for unsubscribing.
     * @param modId            - the mod id.
     * @param listener         - the listener.
     */
    void subscribe(Object subscriber, String modId, Consumer<E> listener);

    /**
     * @param subscriber - the subscribing object, used as the key for unsubscribing.
     * @param modId            - the mod id.
     */
    void unsubscribe(Object subscriber, String modId) throws ConcurrentModificationException;
}
