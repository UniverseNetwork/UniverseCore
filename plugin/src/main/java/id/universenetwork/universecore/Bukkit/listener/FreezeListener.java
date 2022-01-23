package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.data.FreezeData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class FreezeListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTP(PlayerTeleportEvent event) {
        Player p = event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event) {
        Player p = event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player p = (Player) event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryScroll(PlayerItemHeldEvent event) {
        Player p = event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());
        // spawn sound when player change item
        p.playSound(p.getLocation(), "random.orb", 1, 1);

        if (data.hasID()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            data.removeID(p.getUniqueId());
        }
    }

}
