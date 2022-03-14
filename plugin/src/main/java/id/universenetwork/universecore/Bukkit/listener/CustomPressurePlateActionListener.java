package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.manager.file.CustomPressurePlateAction;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class CustomPressurePlateActionListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        CustomPressurePlateAction file = new CustomPressurePlateAction();

        if (e.getAction().equals(Action.PHYSICAL)) {
            if (file.getListPlate().contains(Objects.requireNonNull(e.getClickedBlock()).getType())) {
                if (file.getSection("PressurePlate") == null) return;
                file.getSection("PressurePlate").getKeys(false).forEach(k -> {
                    String key = "PressurePlate." + k;
                    if (e.getClickedBlock().getWorld().getName().equals(file.getConfig().getString(key + ".world"))) {
                        if (file.getConfig().getDouble(key + ".x") == e.getClickedBlock().getLocation().getX()
                                && file.getConfig().getDouble(key + ".y") == e.getClickedBlock().getLocation().getY()
                                && file.getConfig().getDouble(key + ".z") == e.getClickedBlock().getLocation().getZ()) {
                            Objects.requireNonNull(file.getConfig().getConfigurationSection(key + ".PotionEffect.")).getKeys(false).forEach(effectType -> {
                                int duration = file.getConfig().getInt(key + ".PotionEffect." + effectType + ".duration") * 20;
                                int amplifier = file.getConfig().getInt(key + ".PotionEffect." + effectType + ".amplifier");
                                p.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(effectType)), duration, amplifier));
                            });
                        }
                    }
                });
            }
        }
    }

}
