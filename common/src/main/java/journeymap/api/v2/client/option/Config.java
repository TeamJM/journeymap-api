package journeymap.api.v2.client.option;

/**
 * @deprecated Use {@link journeymap.api.v2.common.option.Config} instead.
 * Scheduled for removal in JourneyMap for Minecraft 26.2.
 */
@Deprecated(forRemoval = true)
public interface Config<T>
{
    T get();

    Config<T> set(T value);
}
