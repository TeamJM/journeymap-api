package journeymap.client.api.display;

import com.google.common.base.Objects;
import com.google.gson.annotations.Since;
import journeymap.client.api.model.WaypointBase;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

/**
 * Group for ModWaypoints.
 */
@ParametersAreNonnullByDefault
public class WaypointGroup extends WaypointBase<WaypointGroup>
{
    public static final double VERSION = 1.4;
    @Since(1.4)
    protected final double version = VERSION;
    @Since(1.4)
    protected int order;
    protected transient IWaypointDisplay defaultDisplay;

    /**
     * Constructor.
     *
     * @param modId mod id
     * @param name  display name for group.
     */
    public WaypointGroup(String modId, String name)
    {
        this(modId, UUID.randomUUID().toString(), name);
    }

    /**
     * Constructor
     *
     * @param modId mod id
     * @param id    group id (unique to mod)
     * @param name  display name for group.
     */
    public WaypointGroup(String modId, String id, String name)
    {
        super(modId, id, name);
    }

    /**
     * JourneyMap use only:  Sets a defaultDisplay to delegate color and icons, but only during runtime. (Not persisted)
     *
     * @param defaultDisplay display
     * @return self
     */
    public WaypointGroup setDefaultDisplay(IWaypointDisplay defaultDisplay)
    {
        if (defaultDisplay == this)
        {
            throw new IllegalArgumentException("WaypointGroup may not use itself as a defaultDisplay");
        }
        this.defaultDisplay = defaultDisplay;
        return this;
    }

    @Override
    protected IWaypointDisplay getDelegate()
    {
        return defaultDisplay;
    }

    @Override
    protected boolean hasDelegate()
    {
        return defaultDisplay != null;
    }

    @Override
    public int getDisplayOrder()
    {
        return order;
    }

    public WaypointGroup setDisplayOrder(int order)
    {
        this.order = order;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof WaypointGroup))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        WaypointGroup that = (WaypointGroup) o;
        return order == that.order && Double.compare(that.version, version) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), version);
    }
}

