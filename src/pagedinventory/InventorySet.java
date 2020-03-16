package pagedinventory;

import event.InventoryCountChangedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class InventorySet {
    private String identifier;
    private LinkedList<CustomInventory> inventories = new LinkedList<>();

    public void addInventory(CustomInventory inventory) {
        inventories.add(inventory);
        Bukkit.getPluginManager().callEvent(new InventoryCountChangedEvent(this));
    }

    public boolean removeInventory() {
        if (!inventories.isEmpty()) {
            inventories.remove();
            Bukkit.getPluginManager().callEvent(new InventoryCountChangedEvent(this));
            return true;
        }
        return false;
    }

    public boolean removeInventory(int index) {
        if (index >= 0 && index < inventories.size()) {
            inventories.remove(index);
            Bukkit.getPluginManager().callEvent(new InventoryCountChangedEvent(this));
            return true;
        }
        return false;
    }

    public void save(String path) {

    }

    public boolean openPage(Player player) {
        if (!inventories.isEmpty()) {
            inventories.get(0).openInventory(player);
            return true;
        }
        return false;
    }

    public boolean openPage(Player player, int index) {
        if (!inventories.isEmpty()) {
            if (index >= 0 && index < inventories.size()) {
                inventories.get(index).openInventory(player);
                return true;
            }
        }
        return false;
    }

}
