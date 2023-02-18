package journeymap.client.api.event.forge;

import journeymap.client.api.model.WrappedEntity;
import journeymap.client.api.util.UIState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

/**
 * This event is fired when Journeymap updates an entity before it is displayed on the map.
 */
@Cancelable
public class EntityRadarUpdateEvent extends Event
{
    private final UIState activeUiState;
    private final EntityType entityType;
    private final WrappedEntity wrappedEntity;

    public EntityRadarUpdateEvent(UIState activeUiState, EntityType entityType, WrappedEntity wrappedEntity)
    {
        this.activeUiState = activeUiState;
        this.entityType = entityType;
        this.wrappedEntity = wrappedEntity;
    }

    /**
     * Gets the current active UIState, small chance it may be null.
     *
     * @return - The UIState
     */
    @Nullable
    public UIState getActiveUiState()
    {
        return activeUiState;
    }

    /**
     * Gets the entity type.
     *
     * @return - The entity type.
     */
    public EntityType getType()
    {
        return entityType;
    }

    /**
     * Gets the wrapped entity, modifying the LivingEntity itself will not change anything.
     *
     * @return - The wrapped entity.
     */
    public WrappedEntity getWrappedEntity()
    {
        return wrappedEntity;
    }

    /**
     * The entity types
     */
    public enum EntityType
    {
        MOB,
        PLAYER
    }
}
