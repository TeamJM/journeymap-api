package journeymap.client.api.model;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * This interface is used for setting values of an entity, this controls how an entity is displayed.
 * Setting values on the entity itself will have no impact on how journeymap displays it.
 * <p>
 * Values are reset each time Journeymap refreshes the entity cache.
 */
public interface WrappedEntity
{
    /**
     * This is a weak reference to the Entity, setting any values on this entity will not change how
     * journeymap displays or interacts with the entity.
     *
     * @return The entity reference.
     */
    WeakReference<LivingEntity> getEntityLivingRef();

    /**
     * The entity id, typically a UUID string.
     *
     * @return the entity id.
     */
    String getEntityId();

    /**
     * The icon resource location.
     *
     * @return - resource location.
     */
    @Nullable
    ResourceLocation getEntityIconLocation();

    /**
     * If the entity is hostile
     *
     * @return - the hostile boolean.
     */
    Boolean getHostile();

    /**
     * The position of the entity.
     *
     * @return - the position vector.
     */
    Vec3 getPosition();

    /**
     * The chunk position of the entity.
     * They y position is not the entity block Y. It is the Y slice.
     *
     * @return Block position of the entity's chunk.
     */
    BlockPos getChunkPos();

    /**
     * The heading of the entity.
     *
     * @return - the heading.
     */
    double getHeading();

    /**
     * The entity's custom name.
     * This is a custom name that is set on an entity. It is not a the name of the pet.
     *
     * @return - the custom name.
     */
    @Nullable
    Component getCustomName();

    /**
     * The owner Entity if the Entity is tamed.
     *
     * @return - the owner.
     */
    @Nullable
    Entity getOwner();

    /**
     * The profession of the npc.
     *
     * @return - the profession.
     */
    @Nullable
    String getProfession();

    /**
     * The player's name if the entity is a player.
     *
     * @return - the playername.
     */
    @Nullable
    String getPlayerName();

    /**
     * Gets the entity's biome.
     *
     * @return - the Biome.
     */
    @Nullable
    Biome getBiome();

    /**
     * The dimension where the entity is located.
     *
     * @return - the dimension.
     */
    ResourceKey<Level> getDimension();

    /**
     * Is the entity is a player and underground
     *
     * @return - is underground.
     */
    @Nullable
    Boolean getUnderground();

    /**
     * Is entity invisible to the current player.
     *
     * @return - is invisible.
     */
    boolean isInvisible();

    /**
     * Is entity sneaking
     *
     * @return - is sneaking.
     */
    boolean isSneaking();

    /**
     * Is entity a passive animal
     *
     * @return - is a passive animal.
     */
    boolean isPassiveAnimal();

    /**
     * Is entity an npc
     *
     * @return - is npc.
     */
    boolean isNpc();

    /**
     * Gets the color of the icon displayed on the map.
     *
     * @return - the color
     */
    int getColor();

    /**
     * Is the entity disabled from being drawn on the map.
     *
     * @return - is disabled.
     */
    boolean isDisabled();

    /**
     * Gets the current entity tooltip
     *
     * @return - The tooltip list
     */
    @Nullable
    List<Component> getEntityToolTips();

    /**
     * Sets the entity tooltip.
     *
     * @param entityToolTips - The tooltip list
     */
    void setEntityToolTips(@Nullable List<Component> entityToolTips);

    /**
     * Set the icon location for the entity. The icon is the image shown on the map if the player has display type set to icon.
     *
     * @param entityIconLocation - the resource location of the icon.
     */
    void setEntityIconLocation(ResourceLocation entityIconLocation);

    /**
     * Sets the custom name of the entity.
     *
     * @param customName - the custom name Component.
     */
    void setCustomName(Component customName);

    /**
     * Sets the custom name of the entity.
     *
     * @param customName - the custom name value.
     */
    void setCustomName(String customName);

    /**
     * Sets the icon color of the dot when the user has the display set to dots.
     *
     * @param color - the color.
     */
    void setColor(int color);

    /**
     * Disables the entity from being drawn on the map.
     *
     * @param disable - the disable.
     */
    void setDisable(boolean disable);
}
