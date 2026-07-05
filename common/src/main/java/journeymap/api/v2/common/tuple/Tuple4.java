package journeymap.api.v2.common.tuple;

import java.util.Objects;

public final class Tuple4<A, B, C, D>
{
    private final A a;
    private final B b;
    private final C c;
    private final D d;

    public Tuple4(A a, B b, C c, D d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
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

    public D d()
    {
        return d;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Tuple4))
        {
            return false;
        }
        Tuple4<?, ?, ?, ?> other = (Tuple4<?, ?, ?, ?>) o;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b) && Objects.equals(c, other.c) && Objects.equals(d, other.d);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(a, b, c, d);
    }

    @Override
    public String toString()
    {
        return "Tuple4[a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
    }
}
