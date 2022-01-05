package journeymap.client.api.option;

import java.util.HashMap;
import java.util.Map;

public class OptionsRegistry
{
    public static final Map<String, Map<String, Option<?>>> OPTION_REGISTRY = new HashMap<>();

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