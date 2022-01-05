package journeymap.client.api.option;

import com.google.common.base.MoreObjects;

public class OptionCategory
{
    private final String modId;
    private final String toolTip;
    private final String label;

    public OptionCategory(String modId, String toolTip, String label)
    {
        this.modId = modId;
        this.toolTip = toolTip;
        this.label = label;
    }

    public String getModId()
    {
        return modId;
    }

    public String getToolTip()
    {
        return toolTip;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("modId", modId)
                .add("toolTip", toolTip)
                .add("label", label)
                .toString();
    }
}
