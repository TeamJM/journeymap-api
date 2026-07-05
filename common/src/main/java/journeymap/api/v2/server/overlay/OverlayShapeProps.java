package journeymap.api.v2.server.overlay;

import journeymap.api.v2.common.Context;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

/**
 * Style and visibility properties applied to every polygon in a single
 * {@link ServerPolygon}. Mirrors the subset of {@code Overlay} fields that are
 * meaningful for server-pushed overlays. Interactive listeners are not
 * supported in v1 and are therefore not represented here.
 */
@SuppressWarnings("deprecation")
public final class OverlayShapeProps
{
    private final int fillColor;
    private final float fillOpacity;
    private final int strokeColor;
    private final float strokeWidth;
    private final float strokeOpacity;
    private final int displayOrder;
    private final int minZoom;
    private final int maxZoom;
    private final Set<Context.UI> activeUIs;
    private final Set<Context.MapType> activeMapTypes;
    @Nullable
    private final String label;
    @Nullable
    private final String title;

    /**
     * @param fillColor      0xRRGGBB; alpha is supplied separately as fillOpacity
     * @param fillOpacity    0.0 (transparent) to 1.0 (opaque)
     * @param strokeColor    0xRRGGBB
     * @param strokeWidth    pixels; 0 disables the stroke
     * @param strokeOpacity  0.0 to 1.0
     * @param displayOrder   z-order among overlays; higher draws in front (default 1000)
     * @param minZoom        minimum fullscreen zoom at which the overlay is visible
     * @param maxZoom        maximum fullscreen zoom at which the overlay is visible
     * @param activeUIs      which UIs (Fullscreen, Minimap, Webmap) the overlay is visible in
     * @param activeMapTypes which map types (Day, Night, etc.) the overlay is visible in
     * @param label          static label rendered with the polygon, or null for none
     * @param title          hover tooltip text, or null for none
     */
    public OverlayShapeProps(int fillColor,
                             float fillOpacity,
                             int strokeColor,
                             float strokeWidth,
                             float strokeOpacity,
                             int displayOrder,
                             int minZoom,
                             int maxZoom,
                             Set<Context.UI> activeUIs,
                             Set<Context.MapType> activeMapTypes,
                             @Nullable String label,
                             @Nullable String title)
    {
        this.fillColor = fillColor;
        this.fillOpacity = fillOpacity;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        this.strokeOpacity = strokeOpacity;
        this.displayOrder = displayOrder;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.activeUIs = activeUIs;
        this.activeMapTypes = activeMapTypes;
        this.label = label;
        this.title = title;
    }

    public int fillColor()
    {
        return fillColor;
    }

    public float fillOpacity()
    {
        return fillOpacity;
    }

    public int strokeColor()
    {
        return strokeColor;
    }

    public float strokeWidth()
    {
        return strokeWidth;
    }

    public float strokeOpacity()
    {
        return strokeOpacity;
    }

    public int displayOrder()
    {
        return displayOrder;
    }

    public int minZoom()
    {
        return minZoom;
    }

    public int maxZoom()
    {
        return maxZoom;
    }

    public Set<Context.UI> activeUIs()
    {
        return activeUIs;
    }

    public Set<Context.MapType> activeMapTypes()
    {
        return activeMapTypes;
    }

    @Nullable
    public String label()
    {
        return label;
    }

    @Nullable
    public String title()
    {
        return title;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof OverlayShapeProps))
        {
            return false;
        }
        OverlayShapeProps other = (OverlayShapeProps) o;
        return fillColor == other.fillColor
                && Float.compare(fillOpacity, other.fillOpacity) == 0
                && strokeColor == other.strokeColor
                && Float.compare(strokeWidth, other.strokeWidth) == 0
                && Float.compare(strokeOpacity, other.strokeOpacity) == 0
                && displayOrder == other.displayOrder
                && minZoom == other.minZoom
                && maxZoom == other.maxZoom
                && Objects.equals(activeUIs, other.activeUIs)
                && Objects.equals(activeMapTypes, other.activeMapTypes)
                && Objects.equals(label, other.label)
                && Objects.equals(title, other.title);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(fillColor, fillOpacity, strokeColor, strokeWidth, strokeOpacity, displayOrder,
                minZoom, maxZoom, activeUIs, activeMapTypes, label, title);
    }

    @Override
    public String toString()
    {
        return "OverlayShapeProps[fillColor=" + fillColor + ", fillOpacity=" + fillOpacity
                + ", strokeColor=" + strokeColor + ", strokeWidth=" + strokeWidth
                + ", strokeOpacity=" + strokeOpacity + ", displayOrder=" + displayOrder
                + ", minZoom=" + minZoom + ", maxZoom=" + maxZoom + ", activeUIs=" + activeUIs
                + ", activeMapTypes=" + activeMapTypes + ", label=" + label + ", title=" + title + "]";
    }
}
