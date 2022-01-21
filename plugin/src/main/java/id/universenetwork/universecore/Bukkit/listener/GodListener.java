package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.file.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GodListener implements Listener {

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        PlayerData pd = new PlayerData();

        if (pd.hasPlayerData(p, "godMode")) {
            e.setCancelled(true);
        }

    }

}
