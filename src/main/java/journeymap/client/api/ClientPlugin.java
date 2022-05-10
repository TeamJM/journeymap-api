package journeymap.client.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Used by JourneyMap to discover and classload plugin classes.
 * <p>
 * Classes with this annotation must have a no-arg constructor
 * and must also implement the {@link IClientPlugin} interface.
 *
 * @deprecated - This annotation is not used in the fabric environment,
 * it is only included so the common projects build in multi-loader setups.
 */
@Deprecated
@Target(ElementType.TYPE)
public @interface ClientPlugin
{
}
