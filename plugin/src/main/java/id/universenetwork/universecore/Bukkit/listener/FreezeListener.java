package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.data.FreezeData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        FreezeData data = new FreezeData(p.getUniqueId());

        if (data.hasID()) {
            data.removeID(p.getUniqueId());
        }
    }

}
