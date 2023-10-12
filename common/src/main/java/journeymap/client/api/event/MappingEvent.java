package journeymap.client.api.event;

import journeymap.common.api.event.impl.ClientEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import static journeymap.client.api.event.MappingEvent.Stage.MAPPING_STARTED;

public class MappingEvent extends ClientEvent
{
    private String worldId;
    private final Stage stage;

    public MappingEvent(boolean cancellable,
                        Stage stage,
                        ResourceKey<Level> dimension,
                        String worldId)
    {
        super(cancellable, dimension);
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
     * Gets the worldId for the current world.
     *
     * @return worldId.
     */
    public String getWorldId()
    {
        return worldId;
    }

    /**
     * Sets the world id when mapping starts. This can only be set on the mapping started stage.
     *
     * @param worldId - the new worldId
     */
    public void setWorldId(String worldId)
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
