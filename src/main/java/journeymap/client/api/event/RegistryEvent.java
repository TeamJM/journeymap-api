package journeymap.client.api.event;

import java.util.function.Supplier;

/**
 * Event classes for journeymap registries.
 */
public class RegistryEvent extends ClientEvent
{
    private final RegistryType registryType;

    /**
     * Fired when it is time for addons to register things for Journeymap.
     *
     * @param registryType - The registry type.
     */
    private RegistryEvent(RegistryType registryType)
    {
        super(Type.REGISTRY);
        this.registryType = registryType;
    }

    /**
     * @return - The registry type.
     */
    public RegistryType getRegistryType()
    {
        return registryType;
    }

    /**
     * Fired when it is time to register custom options to display in journeymap options screen.
     * <p>
     * This event is fired very early during the mod loading process. It is fired at the postInit phase of Journeymap
     * which is right before the main title screen is loaded.
     */
    public static class OptionsRegistryEvent extends RegistryEvent
    {
        public OptionsRegistryEvent()
        {
            super(RegistryType.OPTIONS);
        }
    }

    /**
     * Used for creating your own info slots.
     * This event is fired before the {@link OptionsRegistryEvent} as the info slots are need for the configs.
     */
    public static class InfoSlotRegistryEvent extends RegistryEvent
    {
        private final InfoSlotRegistrar registrar;

        public InfoSlotRegistryEvent(InfoSlotRegistrar registrar)
        {
            super(RegistryType.INFO_SLOT);
            this.registrar = registrar;
        }

        /**
         * Registers an infoslot.
         *
         * @param modId      - The ModId
         * @param key        - The i18n key or Label for the InfoSlot dropdown in the options menu.
         * @param updateTime - How often in milliseconds to update.
         * @param supplier   - The supplier that gets the value to be displayed.
         */
        public void register(String modId, String key, long updateTime, Supplier<String> supplier)
        {
            registrar.register(modId, key, updateTime, supplier);
        }

        public interface InfoSlotRegistrar
        {
            /**
             * Registers an infoslot.
             *
             * @param modId      - The ModId
             * @param key        - The i18n key or Label for the InfoSlot dropdown in the options menu.
             * @param updateTime - How often in milliseconds to update.
             * @param supplier   - The supplier that gets the value to be displayed.
             */
            void register(String modId, String key, long updateTime, Supplier<String> supplier);
        }
    }


    /**
     * The registry types.
     */
    public enum RegistryType
    {
        OPTIONS,
        INFO_SLOT
    }
}
