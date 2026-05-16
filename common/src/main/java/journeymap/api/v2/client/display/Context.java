package journeymap.api.v2.client.display;

/**
 * Enums that describe the various display contexts in JourneyMap.
 *
 * @deprecated Scheduled to move to {@code journeymap.api.v2.common.Context}
 *     in the MC 26.2 update so it is reachable from the server-side API
 *     surface. Symbols will be identical; addons will need to update one
 *     import line at that time. No action required in the current release.
 */
@Deprecated(since = "2.1.0")
public interface Context
{
    /**
     * Map UIs.
     *
     * @deprecated See {@link Context}. Moves with its parent in MC 26.2.
     */
    @Deprecated(since = "2.1.0")
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
     *
     * @deprecated See {@link Context}. Moves with its parent in MC 26.2.
     */
    @Deprecated(since = "2.1.0")
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
