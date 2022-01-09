package journeymap.client.api.option;

public class FloatOption extends Option<Float>
{
    private final float minValue;
    private final float maxValue;
    private final float incrementValue;
    private final int precision;

    /**
     * Creates a slider button of Float values
     *
     * @param category     - The category;
     * @param fieldName    - The fieldName;
     * @param label        - The label, can be the display string or i18n key.
     * @param defaultValue - The default value
     * @param minValue     - The min value
     * @param maxValue     - The max value
     */
    public FloatOption(OptionCategory category, String fieldName, String label, Float defaultValue, float minValue, float maxValue)
    {
        this(category, fieldName, label, defaultValue, minValue, maxValue, 0.1f, 2);
    }

    /**
     * Creates a slider button of Float values
     *
     * @param category       - The category;
     * @param fieldName      - The fieldName;
     * @param label          - The label, can be the display string or i18n key.
     * @param defaultValue   - The default value
     * @param minValue       - The min value
     * @param maxValue       - The max value
     * @param incrementValue - The how much the value in incremented or decremented per click of the slider.
     * @param precision      - Points of precision.
     */
    public FloatOption(OptionCategory category, String fieldName, String label, Float defaultValue, float minValue, float maxValue, float incrementValue, int precision)
    {
        super(category, fieldName, label, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.incrementValue = incrementValue;
        this.precision = precision;
    }

    public float getMinValue()
    {
        return minValue;
    }

    public float getMaxValue()
    {
        return maxValue;
    }

    public float getIncrementValue()
    {
        return incrementValue;
    }

    public int getPrecision()
    {
        return precision;
    }
}
