package journeymap.client.api.option;

public class IntegerOption extends Option<Integer>
{

    private final int minValue;
    private final int maxValue;

    /**
     * Creates a slider button of Integer values
     * For tooltips, your i18n key plus .tooltip in your language json example  mod.option.label and mod.option.label.tooltip
     *
     * @param category     - The category;
     * @param fieldName    - The fieldName;
     * @param label        - The label, can be the display string or i18n key.
     * @param defaultValue - The default value
     * @param minValue     - The min value
     * @param maxValue     - The max value
     */
    public IntegerOption(OptionCategory category, String fieldName, String label, Integer defaultValue, int minValue, int maxValue)
    {
        super(category, fieldName, label, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue()
    {
        return minValue;
    }

    public int getMaxValue()
    {
        return maxValue;
    }
}
