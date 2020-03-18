package pagedinventory;

import itemcrafting.ItemCrafting;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class CustomInventory implements Listener {
    public static class Builder {
        private String title;
        private int size;

        public Builder name(String title) {
            this.title = title;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public CustomInventory build() {
            return new CustomInventory(this);
        }
    }

    LinkedHashMap<Integer, ItemStackWrapper> itemList = new LinkedHashMap<>();

    Inventory inventory;

    private String title;
    private int size;

    public CustomInventory(Builder builder) {
        inventory = Bukkit.createInventory(null, builder.size, builder.title);
        this.title = builder.title;
        this.size = builder.size;

        Bukkit.getPluginManager().registerEvents(this, ItemCrafting.itemCrafting);
    }

    public boolean addItem(ItemStack itemStack, ClickAction clickAction) {
        if (itemList.size() < size) {
            ItemStackWrapper itemStackWrapper = new ItemStackWrapper(itemStack, clickAction);
            itemList.put(getEmptySlot(), itemStackWrapper);
            updateInventory();
            return true;
        }
        return false;
    }

    public boolean addItem(ItemStack itemStack, ClickAction clickAction, int slot) {
        if (itemList.size() < size) {
            if (slot < inventory.getSize()) {
                ItemStackWrapper itemStackWrapper = new ItemStackWrapper(itemStack, clickAction);
                itemList.put(slot, itemStackWrapper);
                updateInventory();
                return true;
            }
        }
        return false;
    }

    public boolean removeItem() {
        if (!itemList.isEmpty()) {
            itemList.remove(itemList.size() - 1);
            updateInventory();
            return true;
        }
        return false;
    }

    public boolean removeItem(int slot) {
        if (!itemList.isEmpty()) {
            if (inventory.getItem(slot) != null) {
                itemList.remove(slot);
                updateInventory();
                return true;
            }
        }
        return false;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    public void changeTitle(String title){
        for(HumanEntity h : inventory.getViewers()){
            Player player = (Player)h;

            player.closeInventory();
        }

        this.title = title;
        inventory = Bukkit.createInventory(null, size, title);
        updateInventory();
    }

    private void updateInventory() {
        inventory.clear();

        for (int slot : itemList.keySet()) {
            inventory.setItem(slot, itemList.get(slot));
        }
    }

    public ClickAction getAction(int slot) {
        return itemList.get(slot).getClickAction();
    }

    public boolean isInventoryEquals(Inventory inventory) {
        return this.inventory.equals(inventory);
    }

    private int getEmptySlot() {
        int slot = 0;

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null) {
                slot++;
            } else {
                return slot;
            }
        }
        return slot;
    }

    public int inventoryItemCount(int start, int end) {
        int count = 0;

        for (int i = start; i < end; i++) {
            if (inventory.getItem(i) != null) {
                count++;
            }
        }
        return count;
    }

    public boolean hasItem(ItemStack itemStack, int slot) {
        return inventory.getItem(slot).equals(itemStack);
    }
}
