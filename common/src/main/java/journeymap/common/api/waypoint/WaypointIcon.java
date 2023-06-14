package journeymap.common.api.waypoint;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.Since;
import journeymap.client.api.display.Displayable;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class WaypointIcon
{
    @Since(1.0)
    private Double displayWidth;

    @Since(1.0)
    private Double displayHeight;

    @Since(1.0)
    private ResourceLocation resourceLocation;

    @Since(1.0)
    private Integer color = -1;

    @Since(1.0)
    private Float opacity = 1f;

    @Since(1.0)
    private Integer textureWidth;

    @Since(1.0)
    private Integer textureHeight;

    @Since(1.0)
    private Integer rotation = 0;

    @Since(1.0)
    private boolean useBeaconColor = true;

    protected transient boolean dirty;

    /**
     * Defaults tint to white (0xffffff) and opacity to 1f.
     * Defaults textureWidth and displayWidth to the texture dimensions.
     * Defaults textureHeight and displayHeight to the texture dimensions.
     *
     * @param resourceLocation location for image
     */
    public WaypointIcon(ResourceLocation resourceLocation)
    {
        this.resourceLocation = resourceLocation;
        this.opacity = 1f;
    }

    /**
     * Defaults tint to white (0xffffff) and opacity to 1f.
     * Defaults displayWidth and displayHeight to the texture dimensions.
     *
     * @param resourceLocation location for image
     * @param textureWidth     width of texture
     * @param textureHeight    height of texture
     */
    public WaypointIcon(ResourceLocation resourceLocation, int textureWidth, int textureHeight)
    {
        this(resourceLocation, textureWidth, textureHeight, -1, 1f);
    }

    /**
     * Defaults displayWidth and displayHeight to the texture dimensions.
     *
     * @param resourceLocation Resource location for texture image.
     * @param textureWidth     texture width
     * @param textureHeight    texture height
     * @param color            Sets a color tint (rgb) on the image.  Use white (0xffffff) for no tint.
     * @param opacity          opacity between 0 and 1
     */
    public WaypointIcon(ResourceLocation resourceLocation, int textureWidth, int textureHeight, int color, float opacity)
    {
        this.resourceLocation = resourceLocation;
        this.textureWidth = Math.max(1, textureWidth);
        this.textureHeight = Math.max(1, textureHeight);
        setDisplayWidth(this.textureWidth);
        setDisplayHeight(this.textureHeight);
        setColor(color);
        setOpacity(opacity);
    }

    public WaypointIcon(WaypointIcon original)
    {
        this.resourceLocation = original.resourceLocation;
        this.textureWidth = original.textureWidth;
        this.textureHeight = original.textureHeight;
        this.useBeaconColor = original.useBeaconColor;
        this.displayWidth = original.displayWidth;
        this.displayHeight = original.displayHeight;
        this.color = original.color;
        this.opacity = original.opacity;
        this.rotation = original.rotation;
    }

    /**
     * Gets the rotation in degrees the image should be oriented.
     * Zero is the default.
     *
     * @return degrees
     */
    public int getRotation()
    {
        return rotation;
    }

    /**
     * Sets the rotation in degrees the image should be oriented.
     * Zero is the default.
     *
     * @param rotation in degrees
     * @return this
     */
    public WaypointIcon setRotation(int rotation)
    {
        this.rotation = rotation % 360;
        return this;
    }

    /**
     * Gets the display width in pixels when rendered.
     * Default value is the texture width itself.
     *
     * @return display width
     */
    public double getDisplayWidth()
    {
        return displayWidth;
    }

    /**
     * Sets the image width in pixels when rendered, allowing the image
     * to be scaled if needed.
     *
     * @return this
     */
    public WaypointIcon setDisplayWidth(double displayWidth)
    {
        this.displayWidth = displayWidth;
        return this;
    }

    /**
     * Gets the image height in pixels when rendered.
     * Default value is the texture width itself.
     *
     * @return display width
     */
    public double getDisplayHeight()
    {
        return displayHeight;
    }

    /**
     * Sets the image height in pixels when rendered, allowing the image
     * to be scaled if needed.
     *
     * @return this
     */
    public WaypointIcon setDisplayHeight(double displayHeight)
    {
        this.displayHeight = displayHeight;
        return this;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Integer getColor()
    {
        return color;
    }

    /**
     * Sets color used to tint the image.  Use 0xffffff for white (no tint).
     * This is only to be used if the icon needs to have a separate color from the waypoint.
     *
     * @param color the color
     * @return this
     */
    public WaypointIcon setColor(Integer color)
    {
        this.color = Displayable.clampRGB(color);
        this.useBeaconColor = false;
        return this;
    }

    /**
     * This tells JourneyMap to use the waypoint beacon color for rendering icon.
     * Default is True.
     * If False, it will use the icon color, if icon color is not set, it will not apply any color to the icon.
     *
     * @return if using beacon color
     */
    public boolean useBeaconColor()
    {
        return useBeaconColor;
    }

    /**
     * Sets the use beacon color.
     *
     * @param useBeaconColor - the boolean
     */
    public void setUseBeaconColor(boolean useBeaconColor)
    {
        this.useBeaconColor = useBeaconColor;
    }

    /**
     * Gets opacity.
     *
     * @return the opacity
     */
    public float getOpacity()
    {
        return opacity;
    }

    /**
     * Sets opacity.
     *
     * @param opacity the opacity
     * @return this
     */
    public WaypointIcon setOpacity(float opacity)
    {
        this.opacity = Displayable.clampOpacity(opacity);
        return this;
    }

    public boolean isDirty()
    {
        return this.dirty;
    }

    public void setDirty(boolean dirty)
    {
        this.dirty = dirty;
    }

    public ResourceLocation getResourceLocation()
    {
        return resourceLocation;
    }

    /**
     * Gets the image textureWidth.
     *
     * @return textureWidth
     */
    public int getTextureWidth()
    {
        return textureWidth;
    }

    /**
     * Gets the image textureHeight.
     *
     * @return textureHeight
     */
    public int getTextureHeight()
    {
        return textureHeight;
    }

    @Override
    public boolean equals(@Nullable Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        WaypointIcon mapImage = (WaypointIcon) o;
        return Objects.equal(color, mapImage.color) &&
                Objects.equal(opacity, mapImage.opacity) &&
                Objects.equal(textureWidth, mapImage.textureWidth) &&
                Objects.equal(textureHeight, mapImage.textureHeight) &&
                Objects.equal(resourceLocation, mapImage.resourceLocation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(resourceLocation, color, opacity, textureWidth, textureHeight);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("imageLocation", resourceLocation)
                .add("color", color)
                .add("textureHeight", textureHeight)
                .add("opacity", opacity)
                .add("textureWidth", textureWidth)
                .toString();
    }


}
