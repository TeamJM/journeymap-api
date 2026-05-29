package journeymap.api.v2.client.event;

import journeymap.api.v2.common.option.KeyedEnum;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;

public class InfoSlotDisplayEvent extends ClientEvent
{
    private final LinkedHashMap<String, Position> infoSlotMap;

    public InfoSlotDisplayEvent(LinkedHashMap<String, Position> infoSlotMap)
    {
        super(false);
        this.infoSlotMap = infoSlotMap;
    }

    /**
     * This will put it at the top, of either the top of minimap slots or bottoms of minimap slots.
     *
     * @param key      - the key of the registered info slot.
     * @param position - Position (top or bottom of minimap)
     */
    public void addBefore(String key, Position position)
    {
        infoSlotMap.putFirst(key, position);
    }

    /**
     * This will add a slot to the bottom, of either the bottom of minimap slots or bottom of minimap slots.
     *
     * @param key      - the key of the registered info slot.
     * @param position - Position (top or bottom of minimap)
     */
    public void addLast(String key, Position position)
    {
        infoSlotMap.putLast(key, position);
    }

    /**
     * Gets the map, remove, add, replace. Be careful!
     *
     * @return the map
     */
    public LinkedHashMap<String, Position> getInfoSlotMap()
    {
        return infoSlotMap;
    }

    public enum Position implements KeyedEnum
    {
        Top("jm.minimap.info_slot.top"),
        Bottom("jm.minimap.info_slot.bottom");

        public final String key;

        Position(String key)
        {
            this.key = key;
        }

        @Override
        public String getKey()
        {
            return key;
        }

        @Override
        public String toString()
        {
            return Component.translatable(this.key).getString();
        }
    }
}
