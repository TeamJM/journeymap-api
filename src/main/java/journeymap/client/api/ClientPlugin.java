package journeymap.client.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Used by JourneyMap to discover and classload plugin classes.
 * <p>
 * Classes with this annotation must have a no-arg constructor
 * and must also implement the {@link IClientPlugin} interface.
 */
@Target(ElementType.TYPE)
public @interface ClientPlugin
{
}
