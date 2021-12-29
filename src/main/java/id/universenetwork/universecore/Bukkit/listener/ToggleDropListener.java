package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.data.ToggleDropData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ToggleDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ToggleDropData td = new ToggleDropData(p.getUniqueId());

        if (td.hasID().equals(true)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        ToggleDropData td = new ToggleDropData(p.getUniqueId());

        if (td.hasID()) {
            td.removeID(p.getUniqueId());
        }
    }
}
