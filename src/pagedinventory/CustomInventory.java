package pagedinventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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

    ArrayList<ItemStackWrapper> itemList = new ArrayList<>();

    Inventory inventory;
    private String title;
    private int size;

    public CustomInventory(Builder builder) {
        inventory = Bukkit.createInventory(null, builder.size, builder.title);
        this.title = builder.title;
        this.size = builder.size;

        Bukkit.getPluginManager().registerEvents(this, PagedInventory.pagedInventory);
    }

    public boolean addItem(ItemStack itemStack, ClickAction action) {
        if (itemList.size() < size) {
            ItemStackWrapper itemStackWrapper = new ItemStackWrapper(itemStack, action);
            itemList.add(itemStackWrapper);
            updateInventory();
            return true;
        }
        return false;
    }

    public boolean removeItem(){
        if(!itemList.isEmpty()){
            itemList.remove(itemList.size() - 1);
            updateInventory();
            return true;
        }
        return false;
    }

    public boolean removeItem(ItemStack itemStack){
            if(itemList.removeIf(wrapper -> wrapper.compareItem(itemStack))){
                updateInventory();
                return true;
            }
        return false;
    }

    public void openInventory(Player player){
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

    public void updateInventory() {
        inventory.clear();

        for (ItemStackWrapper wrapper : itemList) {
            inventory.addItem(wrapper.getItem());
        }
    }

    private ItemStackWrapper getWrapper(int index) {
        if (itemList.get(index) != null) {
            return itemList.get(index);
        }
        return null;
    }

    public void save(String path) {

    }

    public ClickAction getAction(int slot) {
        return itemList.get(slot).getClickAction();
    }

    public boolean isInventoryEquals(Inventory inventory) {
        if (inventory.getTitle().equalsIgnoreCase(title)) {
            if (inventory.getSize() == size) {
                return true;
            }
        }
        return false;
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
}
