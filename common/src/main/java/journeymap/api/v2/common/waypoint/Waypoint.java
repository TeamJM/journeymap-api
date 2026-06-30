package journeymap.api.v2.common.waypoint;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.TreeSet;

public interface Waypoint
{
    /**
     * Non-unique id that is a comibination of modId and waypoint position.
     */
    String getId();

    /**
     * Unique Id for each waypoint, will not change.
     */
    String getGuid();

    /**
     * Unique id of the group this waypoint belongs to.
     */
    String getGroupId();

    String getModId();

    String getName();

    void setName(String name);

    /**
     * Optional free-form description shown in the waypoint manager tooltip.
     * Implementations may treat values starting with {@code "jm."} as i18n keys.
     * Default returns {@code null} for backwards compatibility with addons that
     * implement {@link Waypoint} directly.
     */
    default @Nullable String getDescription()
    {
        return null;
    }

    /**
     * @see #getDescription()
     */
    default void setDescription(@Nullable String description)
    {
    }

    void setPos(int x, int y, int z);

    void setBlockPos(BlockPos pos);

    BlockPos getBlockPos();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getZ();

    void setZ(int z);

    int getRed();

    void setRed(int red);

    int getGreen();

    void setGreen(int green);

    int getBlue();

    void setBlue(int blue);

    int getColor();

    void setColor(int color);

    TreeSet<String> getDimensions();

    void setDimensions(Collection<String> dims);

    default void setPrimaryDimension(ResourceKey<Level> dimension)
    {
        setPrimaryDimension(dimension.location().toString());
    }

    void setPrimaryDimension(String dimension);

    String getPrimaryDimension();

    boolean isPersistent();

    void setPersistent(boolean persistent);


    /**
     * Is waypoint enabled.
     *
     * @return - the Enabled boolean
     */
    boolean isEnabled();

    /**
     * Set waypoint enabled / disabled
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
     * Sets color used to tint the image.  Use 0xffffff for no tint.
     * This is only to be used if the icon needs to have a separate color from the waypoint.
     * Defaults to -1 which will tint the icon to the beacon color.
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
    ResourceLocation getIconIdentifier();

    /**
     * Sets the texture resource location.
     *
     * @param ResourceLocation - the resource location
     */
    void setIconIdentifier(ResourceLocation ResourceLocation);

    /**
     * Gets the image textureWidth.
     *
     * @return textureWidth
     */
    int getIconTextureWidth();

    /**
     * Sets the texture width
     *
     * @param width - the texture width
     */
    void setIconTextureWidth(Integer width);

    /**
     * Gets the image textureHeight.
     *
     * @return textureHeight
     */
    int getIconTextureHeight();

    /**
     * Sets the texture height
     *
     * @param height - the texture height
     */
    void setIconTextureHeight(Integer height);

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

    /**
     * Allows add-on devs to set custom data on their waypoints for use, this is not used by journeymap.
     *
     * @param key  - they key for the data
     * @param data - String
     */
    void setCustomData(String key, @Nullable String data);

    /**
     * Gets the custom data stored on a waypoint
     *
     * @param key - they key for the data
     * @return - String
     */
    @Nullable
    String getCustomData(String key);

    /**
     * @return whether the waypoint's beacon beam renders
     */
    default boolean showBeacon()
    {
        return true;
    }

    default void setShowBeacon(boolean showBeacon)
    {
    }

    /**
     * @return whether the waypoint renders on the 2D map
     */
    default boolean showOnMap()
    {
        return true;
    }

    default void setShowOnMap(boolean showOnMap)
    {
    }

    /**
     * @return whether the waypoint renders in the world
     */
    default boolean showInWorld()
    {
        return true;
    }

    default void setShowInWorld(boolean showInWorld)
    {
    }

    /**
     * @return whether the waypoint's name label renders
     */
    default boolean showLabel()
    {
        return true;
    }

    default void setShowLabel(boolean showLabel)
    {
    }

    /**
     * @return whether the waypoint's icon renders
     */
    default boolean showIcon()
    {
        return true;
    }

    default void setShowIcon(boolean showIcon)
    {
    }

    /**
     * @return whether the waypoint shows on the vanilla locator bar
     */
    default boolean showOnLocatorBar()
    {
        return false;
    }

    default void setShowOnLocatorBar(boolean showOnLocatorBar)
    {
    }

    /**
     * @return the beacon color, or {@code null} to follow the icon color
     */
    default Integer getBeaconColor()
    {
        return null;
    }

    default void setBeaconColor(Integer beaconColor)
    {
    }

    /**
     * @return the label color, or {@code null} to follow the icon color
     */
    default Integer getLabelColor()
    {
        return null;
    }

    default void setLabelColor(Integer labelColor)
    {
    }
}
