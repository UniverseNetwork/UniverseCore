package id.universenetwork.universecore.listener;

import id.universenetwork.universecore.command.ToggleDropCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ToggleDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (ToggleDropCommand.td.contains(p)) {
            e.setCancelled(true);
        }
    }

}
