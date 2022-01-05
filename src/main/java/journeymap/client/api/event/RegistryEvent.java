package journeymap.client.api.event;

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
     *
     * This event is fired very early during the mod loading process. It is fired at the init phase of Journeymap
     * which is during the FMLLoadCompleteEvent.
     */
    public static class OptionsRegistryEvent extends RegistryEvent
    {

        public OptionsRegistryEvent()
        {
            super(RegistryType.OPTIONS);
        }
    }

    /**
     * The registry types.
     */
    public enum RegistryType
    {
        OPTIONS
    }
}
