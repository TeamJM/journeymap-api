package journeymap.api.v2.common.tuple;

import java.util.Objects;

public final class Tuple2<A, B>
{
    private final A a;
    private final B b;

    public Tuple2(A a, B b)
    {
        this.a = a;
        this.b = b;
    }

    public A a()
    {
        return a;
    }

    public B b()
    {
        return b;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Tuple2))
        {
            return false;
        }
        Tuple2<?, ?> other = (Tuple2<?, ?>) o;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(a, b);
    }

    @Override
    public String toString()
    {
        return "Tuple2[a=" + a + ", b=" + b + "]";
    }
}
