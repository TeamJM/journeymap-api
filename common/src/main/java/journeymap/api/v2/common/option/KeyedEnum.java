package journeymap.api.v2.common.option;

/**
 * Interface indicating an enum has a key.
 */
public interface KeyedEnum
{
    /**
     * Key is the label.
     *
     * @return - The Key, can be the display string or i18n key.
     */
    String getKey();
}
