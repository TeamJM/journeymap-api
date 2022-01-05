package journeymap.client.api.option;

public class BooleanOption extends Option<Boolean>
{
    private final Boolean isMaster;

    /**
     * Boolean - creates a checkbox
     *
     * @param category     - The category;
     * @param fieldName    - The fieldName;
     * @param label        - The label
     * @param defaultValue - The default value
     */
    public BooleanOption(OptionCategory category, String fieldName, String label, Boolean defaultValue)
    {
        super(category, fieldName, label, defaultValue);
        this.isMaster = false;
    }

    /**
     * Boolean - creates a checkbox
     *
     * @param category     - The category;
     * @param fieldName    - The fieldName;
     * @param label        - The label;
     * @param defaultValue - The default value
     * @param isMaster     - Makes this value first in the last, and if disabled the whole category is disabled.
     */
    public BooleanOption(OptionCategory category, String fieldName, String label, Boolean defaultValue, Boolean isMaster)
    {
        super(category, fieldName, label, defaultValue);
        this.isMaster = isMaster;
    }

    public Boolean isMaster()
    {
        return isMaster;
    }
}
