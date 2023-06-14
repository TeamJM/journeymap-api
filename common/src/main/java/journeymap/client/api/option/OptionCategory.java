package journeymap.client.api.option;

import com.google.common.base.MoreObjects;

public class OptionCategory
{
    private final String modId;
    private final String toolTip;
    private final String label;

    /**
     * @param modId   - The modid.
     * @param label   - The label, can be the display string or i18n key.
     * @param toolTip - The tooltip, can be the display string or i18n key.
     */
    public OptionCategory(String modId, String label, String toolTip)
    {
        this.modId = modId;
        this.label = label;
        this.toolTip = toolTip;
    }

    /**
     * @param modId - The modid.
     * @param key   - The i18n key.
     */
    public OptionCategory(String modId, String key)
    {
        this.modId = modId;
        this.label = key;
        this.toolTip = key + ".tooltip";
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
