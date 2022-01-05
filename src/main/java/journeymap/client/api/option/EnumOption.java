package journeymap.client.api.option;

public class EnumOption<E extends KeyedEnum> extends Option<E>
{

    /**
     * Boolean - creates a dropdown of the enum list.
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
