package journeymap.api.v2.common.event.impl;

public class CommonEvent extends JourneyMapEvent
{
    private final Side side;

    protected CommonEvent(boolean cancellable, Side side)
    {
        super(cancellable);
        this.side = side;
    }

    public Side getSide()
    {
        return side;
    }

    public enum Side
    {
        Client,
        Server
    }
}
