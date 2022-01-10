package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class FlyCommand extends UNCommand {

    @CommandMethod("fly [target]")
    @CommandPermission("universenetwork.fly")
    public void flycmd(final @NonNull CommandSender sender,
                       final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "fly", true)) {
            return;
        }

        AtomicBoolean sudah_others = new AtomicBoolean(false);

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                if (!others) {
                    if (!player.getAllowFlight()) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        Utils.sendMsg(player, Utils.getPrefix() + "&aEnable &efly!");
                    } else {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        Utils.sendMsg(player, Utils.getPrefix() + "&cDisable &efly!");
                    }
                } else {
                    if (sudah_others.get()) {
                        sudah_others.set(true);
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        Utils.sendMsg(player, Utils.getPrefix() + "&aEnable &efly!");
                    } else {
                        sudah_others.set(false);
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        Utils.sendMsg(player, Utils.getPrefix() + "&cDisable &efly!");
                    }
                }
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, Utils.getPrefix() + (player.getAllowFlight() ? "&aEnable " : "&cDisable ") +  "&efly &7for &b" + player.getName()));
                } else {
                    Utils.sendMsg(sender, Utils.getPrefix() + (sudah_others.get() ? "&aEnable " : "&cDisable ") +  "&efly &7for &b" + targets.size() + " &7players!");
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, Utils.getPrefix() + (player.getAllowFlight() ? "&aEnable " : "&cDisable ") +  "&efly &7for &b" + player.getName()));
            }
        }, this.canSkip("fly", targets, sender));

    }

}
