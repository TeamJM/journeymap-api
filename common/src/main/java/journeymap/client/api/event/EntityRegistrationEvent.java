package journeymap.client.api.event;

import journeymap.common.api.event.impl.ClientEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Register entity classes that are not a typical
 * LivingEntity or Entity that is used by vanilla that needs to be displayed on the map.
 * <p>
 * To add custom icons use this style of pathing `enderzoo:textures/entity_icon/wither_cat.png`.
 * LivingEntities will likely have their icons auto generated. Non-LivingEntities will not.
 * <p>
 * Some limitations with this api, only entity types can be added. Adding Item or TileEntities may cause issues.
 * The interfaces Npc.class and Enemy.class are handled internally by JourneyMap.
 * <p>
 * JourneyMap already adds the following classes, no need to re-add them.
 * <p>
 * Hostiles - PathfinderMob.class
 * Villagers - Villager.class
 * Passives - AbstractGolem.class, WaterAnimal.class, PathfinderMob.class, Animal.class
 * Ambients - AmbientCreatures.class
 * Entities - Minecrafts.class
 */
public class EntityRegistrationEvent extends ClientEvent
{
    private final Map<Type, List<Class<? extends Entity>>> entityClasses;

    public EntityRegistrationEvent(Map<Type, List<Class<? extends Entity>>> entityClasses)
    {
        super(false);
        this.entityClasses = new HashMap<>();
    }

    /**
     * Adds passive entities like animals.
     *
     * @param entityClass - the entity class
     */
    public EntityRegistrationEvent addPassiveEntity(Class<? extends LivingEntity> entityClass)
    {
        entityClasses.computeIfAbsent(Type.PASSIVE, p -> new ArrayList<>()).add(entityClass);
        return this;
    }

    /**
     * Adds hostile entities, monsters, aggressive creatures.
     *
     * @param entityClass - the entity class
     */
    public EntityRegistrationEvent addHostileEntity(Class<? extends LivingEntity> entityClass)
    {
        entityClasses.computeIfAbsent(Type.HOSTILE, h -> new ArrayList<>()).add(entityClass);
        return this;
    }

    /**
     * Adds villager entities like villagers or npcs.
     *
     * @param entityClass - the entity class
     */
    public EntityRegistrationEvent addVillagerEntity(Class<? extends LivingEntity> entityClass)
    {
        entityClasses.computeIfAbsent(Type.VILLAGER, v -> new ArrayList<>()).add(entityClass);
        return this;
    }

    /**
     * Adds ambient entities like bats.
     *
     * @param entityClass - the entity class
     */
    public EntityRegistrationEvent addAmbientEntity(Class<? extends LivingEntity> entityClass)
    {
        entityClasses.computeIfAbsent(Type.AMBIENT, a -> new ArrayList<>()).add(entityClass);
        return this;
    }

    /**
     * Adds entities to be displayable, do not register a LivingEntity here.
     *
     * @param entityClass - the entity class
     */
    //TODO: Implement - unused currently
    private EntityRegistrationEvent addEntity(Class<? extends Entity> entityClass)
    {
        if (entityClass.isAssignableFrom(LivingEntity.class))
        {
            throw new UnsupportedOperationException("Attempted to register LivingEntity as a NonLivingEntity");
        }
        else
        {
            entityClasses.computeIfAbsent(Type.ENTITY, e -> new ArrayList<>()).add(entityClass);
            return this;
        }
    }

    @ApiStatus.Internal
    public List<Class<? extends Entity>> getEntityClasses(Type type)
    {
        return Collections.unmodifiableList(entityClasses.get(type));
    }

    public enum Type
    {
        PASSIVE,
        HOSTILE,
        VILLAGER,
        AMBIENT,
        ENTITY
    }
}
