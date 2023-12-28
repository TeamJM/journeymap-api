package journeymap.common.api.waypoint;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.TreeSet;

public interface Waypoint
{
    String getId();

    String getGuid();

    String getName();

    void setName(String name);

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
}
