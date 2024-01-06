package journeymap.common.api.waypoint;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface WaypointGroup
{
    Map<String, Waypoint> getWaypoints();

    void addWaypoint(Waypoint waypoint);

    Waypoint getWaypoint(String guid);

    String getGuid();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    /**
     * Is waypoint group enabled.
     *
     * @return - the Enabled boolean
     */
    boolean isEnabled();

    /**
     * Set waypoint group enabled / disabled
     *
     * @param enabled - enabled
     */
    void setEnabled(boolean enabled);

    /**
     * Is deviation being shown
     *
     * @return - is showing
     */
    boolean showDeviation();

    /**
     * Sets the show deviation text on the beacon.
     *
     * @param showDeviation - the boolean
     */
    void setShowDeviation(boolean showDeviation);

    /**
     * Can users change group settings, including adding/removing waypoints
     *
     * @return the is locked
     */
    boolean isLocked();

    /**
     * Set to true to lock users from modifying the group.
     * This includes adding/removing waypoints and changing any of the settings of the group.
     * Default - False
     *
     * @param locked - the locked boolean
     */
    void setLocked(boolean locked);

    // ############ ICON ############
    /*
     * These values do not need to be set if using the default icon.
     * If using a custom image for your icon, setting the resource location is all that is needed.
     * Use the other values to fine tune image size, rotation, alpha, and color.
     * Setting the color will use a different color than the waypoint beacon color for the icon.
     *
     * All Icon setters are optional.
     */

    /**
     * Gets the rotation in degrees the image should be oriented.
     * Zero is the default.
     *
     * @return degrees
     */
    int getIconRotation();

    /**
     * Sets the rotation in degrees the image should be oriented.
     * Zero is the default.
     *
     * @param rotation in degrees
     */
    void setIconRotation(int rotation);

    /**
     * Gets color.
     *
     * @return the color
     */
    Integer getIconColor();

    /**
     * Sets color used to tint the image.  Use 0xffffff for white (no tint).
     * This is only to be used if the icon needs to have a separate color from the waypoint.
     *
     * @param color the color
     */
    void setIconColor(Integer color);

    /**
     * Gets opacity.
     *
     * @return the opacity
     */
    float getIconOpacity();

    /**
     * Sets opacity.
     *
     * @param opacity the opacity
     */
    void setIconOpacity(float opacity);

    /**
     * Gets the texture resource location.
     *
     * @return - ResourceLocation
     */
    ResourceLocation getIconResourceLocation();

    /**
     * Sets the texture resource location.
     *
     * @param resourceLocation - the resource location
     */
    void setIconResourceLoctaion(ResourceLocation resourceLocation);

    /**
     * Gets the image textureWidth.
     *
     * @return textureWidth
     */
    int getIconTextureWidth();

    /**
     * Sets the texture width
     *
     * @param textureWidth - the texture width
     */
    void setIconTextureWidth(Integer textureWidth);

    /**
     * Gets the image textureHeight.
     *
     * @return textureHeight
     */
    int getIconTextureHeight();

    /**
     * Sets the texture height
     *
     * @param textureHeight - the texture height
     */
    void setIconTextureHeight(Integer textureHeight);

    /**
     * Set the texture size
     *
     * @param width  - the texture width
     * @param height - the texture height
     */
    default void setIconTextureSize(int width, int height)
    {
        setIconTextureWidth(width);
        setIconTextureHeight(height);
    }
}
