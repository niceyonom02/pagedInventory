package pagedinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.LinkedList;

public class InventorySet {
    private LinkedList<CustomInventory> inventories = new LinkedList<>();

    public void addInventory(CustomInventory inventory) {
        inventories.add(inventory);
    }

    public boolean removeInventory() {
        if (!inventories.isEmpty()) {
            inventories.remove();
            return true;
        }
        return false;
    }

    public boolean removeInventory(int index) {
        if (index >= 0 && index < inventories.size()) {
            inventories.remove(index);
            return true;
        }
        return false;
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

    public CustomInventory getInventory(int index) {
        if (index >= 0 && index < inventories.size()) {
            return inventories.get(index);
        }
        return null;
    }

    public int getInventoryCount() {
        return inventories.size();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        for (int i = 0; i < inventories.size(); i++) {
            if (inventories.get(i).isInventoryEquals(e.getInventory())) {
                switch (inventories.get(i).getAction(e.getSlot())) {
                    case NONE:
                        break;
                    case NEXT:
                        e.setCancelled(true);
                        if (i < inventories.size() - 1) {
                            e.getWhoClicked().closeInventory();
                            inventories.get(i + 1).openInventory((Player) e.getWhoClicked());
                        }
                        break;
                    case CLOSE:
                        e.setCancelled(true);
                        e.getWhoClicked().closeInventory();
                        break;
                    case PREVIOUS:
                        e.setCancelled(true);
                        if (i > 0) {
                            e.getWhoClicked().closeInventory();
                            inventories.get(i - 1).openInventory((Player) e.getWhoClicked());
                        }
                        break;
                }
                break;
            }
        }
    }
}
