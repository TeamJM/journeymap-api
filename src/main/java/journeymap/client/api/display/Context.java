package journeymap.client.api.display;

/**
 * Container for enums that describe the context where Overlays should be active.
 */
public class Context
{
    public enum UI
    {
        All,
        Fullscreen,
        Minimap,
        Webmap
    }

    public enum MapLayer
    {
        All,
        Surface,
        Underground
    }

    public enum MapType
    {
        All,
        Day,
        Night
    }
}
