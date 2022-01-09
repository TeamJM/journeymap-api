package journeymap.client.api.option;

public class CustomIntegerOption extends CustomOption<Integer>
{
    private final Integer minValue;
    private final Integer maxValue;
    private final Boolean allowNeg;

    /**
     * Creates an Integer textbox.
     * For tooltips, your i18n key plus .tooltip in your language json example  mod.option.label and mod.option.label.tooltip
     *
     * @param category     - The category.
     * @param fieldName    - The fieldname.
     * @param label        - The label, can be the display string or i18n key.
     * @param minValue     - The minimum allowed value.
     * @param maxValue     - The maximum allowed value.
     * @param defaultValue - The default value.
     * @param allowNeg     - Allow negative values.
     */
    public CustomIntegerOption(OptionCategory category, String fieldName, String label, Integer defaultValue, Integer minValue, Integer maxValue, Boolean allowNeg)
    {
        super(category, fieldName, label, defaultValue);
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.allowNeg = allowNeg;
    }

    public Integer getMinValue()
    {
        return minValue;
    }

    public Integer getMaxValue()
    {
        return maxValue;
    }

    public Boolean getAllowNeg()
    {
        return allowNeg;
    }
}
