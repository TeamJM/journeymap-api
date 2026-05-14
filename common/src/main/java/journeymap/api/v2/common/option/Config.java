package journeymap.api.v2.common.option;

public interface Config<T>
{
    T get();

    Config<T> set(T value);
}
