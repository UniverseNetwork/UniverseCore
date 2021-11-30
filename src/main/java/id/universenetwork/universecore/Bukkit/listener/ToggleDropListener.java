package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.command.ToggleDropCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ToggleDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ToggleDropCommand td = new ToggleDropCommand();

        if (td.contains(p)) {
            e.setCancelled(true);
        }
    }

}
