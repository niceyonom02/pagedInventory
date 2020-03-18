package pagedinventory;

import org.bukkit.plugin.java.JavaPlugin;

public class PagedInventory extends JavaPlugin{
    public static PagedInventory pagedInventory;

    @Override
    public void onEnable(){
        pagedInventory = this;
    }

    @Override
    public void onDisable(){

    }


}
