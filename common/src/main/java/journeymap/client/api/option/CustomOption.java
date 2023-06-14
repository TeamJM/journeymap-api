package journeymap.client.api.option;

/**
 * Custom Option generates a text box in the options menu.
 */
public abstract class CustomOption<T> extends Option<T>
{
    public CustomOption(OptionCategory category, String fieldName, String label, T defaultValue)
    {
        super(category, fieldName, label, defaultValue);
    }
}
