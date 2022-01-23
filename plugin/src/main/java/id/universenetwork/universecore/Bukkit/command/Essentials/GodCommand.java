package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.PlayerData;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GodCommand extends UNCommand {

    @CommandMethod("ugod [target]")
    @CommandPermission("universenetwork.god")
    public void godCMD(final @NonNull CommandSender sender,
                       final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "god", true)) return;

        core.getConfirmationManager().requestConfirm(() -> {

            targets.stream().forEach(player -> {
                PlayerData data = new PlayerData();
                if (data.hasPlayerData(player, "godMode")) {
                    data.removePlayerData(player, "godMode");
                    player.setInvulnerable(false);
                    Utils.sendMsg(player, "&cGod mode disabled!");
                } else {
                    data.setPlayerData(player, "godMode", true);
                    player.setInvulnerable(true);
                    player.setHealth(core.getMultiVersion().getMaxHealth(player));
                    player.setFoodLevel(20);
                    player.setFireTicks(0);
                    Utils.sendMsg(player, "&aGod mode enabled!");
                }
                data.saveConfig();
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, "&7Toggled god mode for &a" + player.getName()));
                } else {
                    Utils.sendMsg(sender, "&7Toggled godMode for &e" + targets.size() + " &7players!");
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, "&7Toggled god mode for &a" + player.getName()));
            }

        }, this.canSkip("god all", targets, sender));

    }

}
