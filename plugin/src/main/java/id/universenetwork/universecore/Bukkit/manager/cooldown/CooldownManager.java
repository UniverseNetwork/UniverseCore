package id.universenetwork.universecore.Bukkit.manager.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CooldownManager {

    private final Table<UUID, String, Long> cooldowns;
    private final UniverseCore core;

    public CooldownManager(UniverseCore core) {
        this.core = core;
        this.cooldowns = HashBasedTable.create();
    }

    public Table<UUID, String, Long> getCooldowns() {
        return cooldowns;
    }

    public double getCooldown(UUID uuid, String type) {
        return this.calculateRemainder(cooldowns.get(uuid, type));
    }

    public void setCooldown(UUID uuid, String type, long cooldown) {
        this.calculateRemainder(cooldowns.put(uuid, type, System.currentTimeMillis() + (cooldown * 1000)));
    }

    public void removeCooldown(UUID uuid, String type) {
        calculateRemainder(cooldowns.remove(uuid, type));
    }

    public boolean tryCooldown(UUID uuid, String type, long delay) {
        if (getCooldown(uuid, type) / 1000 > 0) return false;
        this.setCooldown(uuid, type, delay + 1);
        return true;
    }

    public boolean hasCooldown(UUID uuid, String type) {
        return cooldowns.contains(uuid, type);
    }

    private long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }

    public void smartDeleteCooldowns() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(core, () -> {
            for (UUID uuid : cooldowns.rowKeySet()) {
                for (String type : cooldowns.row(uuid).keySet()) {
                    if (cooldowns.get(uuid, type) <= System.currentTimeMillis()) {
                        cooldowns.remove(uuid, type);
                    }
                }
            }
        }, 1, 1);
    }

    public void requestCooldown(Callback callback,String type, long delay, String permission, CommandSender sender, @Nullable List<String> warnings) {
        String permiss = "universenetwork." + permission + ".bypass-cooldown";

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!hasCooldown(player.getUniqueId(), type)) {
                callback.callback();
                if (!player.hasPermission(permiss)) this.setCooldown(player.getUniqueId(), type, delay);
            } else {
                if (!player.hasPermission(permiss)) {
                    if (getCooldown(player.getUniqueId(), type) <= 0) {
                        removeCooldown(player.getUniqueId(), type);
                        /*callback.callback();
                        this.setCooldown(player.getUniqueId(), type, delay);*/
                        return;
                    }
                    if (getCooldown(player.getUniqueId(), type) / 1000 > 0) {
                        if (warnings != null && !warnings.isEmpty()) {
                            warnings.forEach(s -> Utils.sendMsg(player, s));
                        }
                    }
                } else {
                    callback.callback();
                }
            }
        } else {
            callback.callback();
        }
    }

    public void requestCooldown(Callback callback, CanSkipCooldown canSkipCooldown) {
        this.requestCooldown(callback, canSkipCooldown.getKey(), canSkipCooldown.getTime(), canSkipCooldown.getPermission(), canSkipCooldown.getSender(), canSkipCooldown.getWarnings());
    }

    public CanSkipCooldown canSkipCooldown(String type, long delay, String permission, CommandSender sender) {
        if (sender instanceof Player) {
            return new CanSkipCooldown(type, delay, permission, sender,
                    Collections.singletonList(
                            "&cYou can't use this command for another &e" +
                                    (int) (getCooldown(((Player) sender).getUniqueId(), type) / 1000) + "&c."));
        } else {
            return new CanSkipCooldown(type, delay, permission, sender, null);
        }
    }

    public UniverseCore getCore() {
        return core;
    }

    public interface Callback {
        void callback();
    }

}
