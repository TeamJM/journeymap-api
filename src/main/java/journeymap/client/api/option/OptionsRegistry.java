package journeymap.client.api.option;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is not intended for use by modders, Options register themselves.
 */
@Deprecated
public class OptionsRegistry
{
    public static final Map<String, Map<String, Option<?>>> OPTION_REGISTRY = new HashMap<>();

    /**
     * @deprecated modders do not use this, options register themselves.
     * @param modId - the modid
     * @param option - the option.
     */
    @Deprecated
    public static void register(String modId, Option<?> option)
    {
        Map<String, Option<?>> addonRegistry = OPTION_REGISTRY.get(modId);
        if (addonRegistry == null)
        {
            addonRegistry = new HashMap<>();
        }
        addonRegistry.put(option.getFieldName(), option);
        OPTION_REGISTRY.put(modId, addonRegistry);
    }
}