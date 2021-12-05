package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.data.ToggleDropData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ToggleDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ToggleDropData td = new ToggleDropData(p.getUniqueId());

        if (td.hasID().equals(true)) {
            e.setCancelled(true);
        }
    }
}
