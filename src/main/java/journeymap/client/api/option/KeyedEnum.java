package journeymap.client.api.option;

/**
 * Interface indicating an enum has a key.
 */
public interface KeyedEnum
{
    /**
     * Key can be an i18n key or a label.
     *
     * @return - The Key.
     */
    String getKey();
}
