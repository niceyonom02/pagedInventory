package pagedinventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.LinkedList;

public class CustomInventory {
    public static class Builder{
        private String title;
        private int size;

        public Builder name(String title){
            this.title = title;
            return this;
        }

        public Builder size(int size){
            this.size = size;
            return this;
        }

        public CustomInventory build(){
            return new CustomInventory(this);
        }
    }

    public CustomInventory(Builder builder){
        inventory = Bukkit.createInventory(null, builder.size, builder.title);
        this.title = builder.title;
        this.size = builder.size;
    }

    Inventory inventory;
    private String title;
    private int size;
    LinkedList<ItemStackWrapper> itemList = new LinkedList<>();

    public boolean addItem(ItemStack itemStack, ClickAction action){
        if(itemList.size() < size){
            ItemStackWrapper itemStackWrapper = new ItemStackWrapper(itemStack, action);
            itemList.add(itemStackWrapper);
            updateInventory();
            return true;
        }
        return false;
    }

    public boolean removeItem(){
        if(!itemList.isEmpty()){
            itemList.remove();
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

    public void updateInventory(){
        inventory.clear();

        for(ItemStackWrapper wrapper : itemList){
            inventory.addItem(wrapper.getItem());
        }
    }

    public void save(){

    }
}
