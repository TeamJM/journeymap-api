package journeymap.api.v2.client.option;

public interface Config<T>
{
    T get();

    Config<T> set(T value);
}
