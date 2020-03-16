package pagedinventory;

import net.minecraft.server.v1_12_R1.Items;
import org.bukkit.inventory.ItemStack;

public class ItemStackWrapper {
    private ItemStack itemStack;
    private ClickAction clickAction;

    public ItemStackWrapper(ItemStack itemStack, ClickAction clickAction){
        this.itemStack = itemStack;
        this.clickAction = clickAction;
    }

    public boolean compareItem(ItemStack itemStack){
        return this.itemStack.equals(itemStack);
    }

    public ItemStack getItem() {
        return itemStack;
    }

    public ClickAction getClickAction() {
        return clickAction;
    }
}
