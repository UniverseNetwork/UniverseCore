package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.manager.helpme.HelpMeData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HelpMeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        HelpMeData data = new HelpMeData(UniverseCore.getInstance());

        if (p.hasPermission("universenetwork.staff.helpme")) {
            data.setStaffOnline(p.getUniqueId(), p);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        HelpMeData data = new HelpMeData(UniverseCore.getInstance());

        if (p.hasPermission("universenetwork.staff.helpme")) {
            data.removeOnlineStaff(p.getUniqueId());
        }

        if (data.isInHelpList(p.getUniqueId())) {
            data.removeHelpList(p.getUniqueId());
        }
    }

}
