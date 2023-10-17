package journeymap.client.api.event;

import journeymap.common.api.event.impl.ClientEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static journeymap.client.api.event.MappingEvent.Stage.MAPPING_STARTED;

/**
 * This event fires when mapping starts or stops.
 * WorldId can be set when mapping starts.
 * This event is not cancellable.
 */
public class MappingEvent extends ClientEvent
{
    private String worldId;
    private final Stage stage;

    public MappingEvent(Stage stage,
                        ResourceKey<Level> dimension,
                        String worldId)
    {
        super(false, dimension);
        this.stage = stage;
        this.worldId = worldId;
    }

    /**
     * Gets the current mapping stage.
     *
     * @return - The stage
     */
    public Stage getStage()
    {
        return stage;
    }

    /**
     * Gets the worldId for the current world. Will return null if no worldId has been set.
     *
     * @return worldId.
     */
    @Nullable
    public String getWorldId()
    {
        return worldId;
    }

    /**
     * Sets the world id when mapping starts. This can only be set on the mapping started stage.
     *
     * @param worldId - the new worldId
     */
    public void setWorldId(@Nullable String worldId)
    {
        if (MAPPING_STARTED == this.stage)
        {
            this.worldId = worldId;
        }
        else
        {
            throw new UnsupportedOperationException("World Id can only be set on the MAPPING_STARTED stage.");
        }
    }


    /**
     * The mapping stages
     */
    public enum Stage
    {
        MAPPING_STARTED,
        MAPPING_STOPPED
    }
}
