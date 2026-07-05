package journeymap.api.v2.common.tuple;

import java.util.Objects;

public final class Tuple3<A, B, C>
{
    private final A a;
    private final B b;
    private final C c;

    public Tuple3(A a, B b, C c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A a()
    {
        return a;
    }

    public B b()
    {
        return b;
    }

    public C c()
    {
        return c;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Tuple3))
        {
            return false;
        }
        Tuple3<?, ?, ?> other = (Tuple3<?, ?, ?>) o;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b) && Objects.equals(c, other.c);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(a, b, c);
    }

    @Override
    public String toString()
    {
        return "Tuple3[a=" + a + ", b=" + b + ", c=" + c + "]";
    }
}
