package journeymap.common.api.event.impl;

public abstract class JourneyMapEvent
{
    private boolean cancelled = false;
    private final boolean cancellable;

    /**
     * System millis when event was created.
     */
    public final long timestamp;

    protected JourneyMapEvent(boolean cancellable)
    {
        this.cancellable = cancellable;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Whether the event has been cancelled.
     *
     * @return true if cancelled
     */
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    /**
     * Cancels the event only if the Type is cancellable.
     */
    public void cancel()
    {
        if (!isCancellable())
        {
            throw new UnsupportedOperationException(this.getClass() + " is not cancelable");
        }
        this.cancelled = true;
    }

    /**
     * Whether the event is cancellable.
     *
     * @return true if cancellable.
     */
    public boolean isCancellable()
    {
        return this.cancellable;
    }


}
