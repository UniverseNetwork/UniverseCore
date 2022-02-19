package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.GuiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiListener implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        GuiManager gui = new GuiManager();

        if (event.getInventory().equals(gui.getInventory())) {
            gui.closeGui(event.getPlayer());
        }
    }

}
