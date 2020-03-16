package event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pagedinventory.CustomInventory;
import pagedinventory.InventorySet;

public class InventoryCountChangedEvent extends Event {
    public InventoryCountChangedEvent(InventorySet inventorySet){
        this.inventorySet = inventorySet;
    }

    private InventorySet inventorySet;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public InventorySet getInventorySet() {
        return inventorySet;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}
