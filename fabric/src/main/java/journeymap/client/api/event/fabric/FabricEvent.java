package journeymap.client.api.event.fabric;

public abstract class FabricEvent
{
    protected boolean canceled = false;

    protected boolean isCancelable()
    {
        return false;
    }

    public boolean isCanceled()
    {
        return this.canceled;
    }

    public void setCanceled(boolean canceled)
    {
        if (!isCancelable())
        {
            throw new UnsupportedOperationException(this.getClass() + " is not cancelable");
        }
        this.canceled = canceled;
    }
}
