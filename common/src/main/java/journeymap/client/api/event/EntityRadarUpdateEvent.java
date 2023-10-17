package journeymap.client.api.event;

import journeymap.client.api.model.WrappedEntity;
import journeymap.client.api.util.UIState;
import journeymap.common.api.event.impl.ClientEvent;

import javax.annotation.Nullable;

/**
 * This event is fired when JourneyMap updates an entity before it is displayed on the map.
 * This event is cancellable, when cancelled, it will prevent the entity from being displayed on the map.
 */
public class EntityRadarUpdateEvent extends ClientEvent
{
    private final UIState activeUiState;
    private final EntityType entityType;
    private final WrappedEntity wrappedEntity;

    public EntityRadarUpdateEvent(UIState activeUiState, EntityType entityType, WrappedEntity wrappedEntity)
    {
        super(true);
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

    public enum EntityType
    {
        MOB,
        PLAYER
    }
}
