package journeymap.client.api.display;

/**
 * Enums that describe the various display contexts in JourneyMap.
 */
public interface Context
{
    /**
     * Map UIs.
     */
    enum UI implements Context
    {
        /**
         * Any UI.
         */
        Any,

        /**
         * The Fullscreen map UI.
         */
        Fullscreen,

        /**
         * The Minimap UI.
         */
        Minimap,

        /**
         * The Webmap UI.
         */
        Webmap
    }

    /**
     * Map types.
     */
    enum MapType implements Context
    {
        Any,
        Day,
        Night,
        Underground,
        Topo
    }
}
