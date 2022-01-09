package journeymap.client.api.option;

import java.util.Locale;

public abstract class Option<T>
{

    private final OptionCategory category;
    private final String label;
    private final String fieldName;
    protected final T defaultValue;
    protected Config<T> config;

    /**
     * Parent for all options.
     * For tooltips, your i18n key plus .tooltip in your language json example  mod.option.label and mod.option.label.tooltip
     *
     * @param category     - The category.
     * @param fieldName    - The field name.
     * @param label        - the label, can be the display string or i18n key.
     * @param defaultValue - the default value.
     */
    protected Option(OptionCategory category, String fieldName, String label, T defaultValue)
    {
        this.category = category;
        this.fieldName = fieldName;
        this.label = label;
        this.defaultValue = defaultValue;
        OptionsRegistry.register(category.getModId(), this);
    }

    public OptionCategory getCategory()
    {
        return category;
    }

    public String getFieldName()
    {
        return fieldName.toLowerCase(Locale.ROOT).replaceAll("\\s", "");
    }

    public String getLabel()
    {
        return label;
    }

    private void setConfig(Config config)
    {
        this.config = config;
    }

    public T get()
    {
        return config.get();
    }

    public void set(T value)
    {
        config.set(value);
    }

    public T getDefaultValue()
    {
        return this.defaultValue;
    }
}
