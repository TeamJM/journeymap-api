package journeymap.client.api.option;

public class EnumOption<E extends KeyedEnum> extends Option<E>
{

    /**
     * Boolean - creates a dropdown of the enum list.
     * Labels are specified in the getKey from {@link KeyedEnum} that your enum must extend.
     * For tooltips, your i18n key plus .tooltip in your language json example  mod.option.label and mod.option.label.tooltip
     *
     * @param category     - The category;
     * @param fieldName    - The fieldName;
     * @param defaultValue - The default enum value.
     */
    public EnumOption(OptionCategory category, String fieldName, String label, E defaultValue)
    {
        super(category, fieldName, label, defaultValue);
    }
}
