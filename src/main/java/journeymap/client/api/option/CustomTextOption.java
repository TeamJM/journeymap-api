package journeymap.client.api.option;


public class CustomTextOption extends CustomOption<String>
{
    /**
     * Creates a String textbox.
     * @param category - The category
     * @param fieldName - the field name
     * @param label - the label
     * @param defaultValue - The default value.
     */
    public CustomTextOption(OptionCategory category, String fieldName, String label, String defaultValue)
    {
        super(category, fieldName, label, defaultValue);
    }
}
