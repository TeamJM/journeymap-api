package journeymap.client.api.option;

public interface Config<T>
{
    T get();

    Config<T> set(T value);
}
