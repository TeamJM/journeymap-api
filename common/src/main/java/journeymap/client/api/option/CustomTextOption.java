package journeymap.client.api.option;


public class CustomTextOption extends CustomOption<String>
{
    /**
     * Creates a String textbox.
     * For tooltips, your i18n key plus .tooltip in your language json example  mod.option.label and mod.option.label.tooltip
     *
     * @param category     - The category
     * @param fieldName    - the field name
     * @param label        - the label, can be the display string or i18n key.
     * @param defaultValue - The default value.
     */
    public CustomTextOption(OptionCategory category, String fieldName, String label, String defaultValue)
    {
        super(category, fieldName, label, defaultValue);
    }
}
