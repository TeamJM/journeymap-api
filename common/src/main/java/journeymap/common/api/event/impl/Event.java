package journeymap.common.api.event.impl;

import java.util.function.Consumer;

public interface Event<E>
{
    void subscribe(String modId, Consumer<E> listener);
}
