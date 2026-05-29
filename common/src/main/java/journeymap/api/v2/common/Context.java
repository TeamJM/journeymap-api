package journeymap.api.v2.common;

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
        Webmap;

        /**
         * Any/All UIs.
         */
        public static UI[] all()
        {
            return UI.values();
        }
    }

    /**
     * Map types.
     */
    enum MapType implements Context
    {
        Day,
        Night,
        Underground,
        Topo,
        Biome;

        public static MapType[] all()
        {
            return MapType.values();
        }
    }
}
