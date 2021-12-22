package id.universenetwork.universecore.Bukkit.command.Troll;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class KaboomCommand extends UNCommand {

    @CommandMethod("kaboom|boom|ukaboom|uboom [target]")
    public void commandKaboom(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        if (!utils.checkPermission(sender, "kaboom")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);
        if (targets.notifyIfEmpty()) {
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.NOPLAYER));
            return;
        }

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !utils.checkPermission(sender, "kaboom", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setVelocity(player.getLocation().getDirection().setY(10));
                Objects.requireNonNull(player.getLocation().getWorld()).strikeLightning(player.getLocation());
                player.sendTitle(MessageData.getInstance().getString(MessageEnum.KABOOMT), null,20,40,20);
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        utils.sendMsg(sender, utils.getPrefix() +
                                StringUtils.replace(utils.getMsgString(MessageEnum.KABOOMS),
                                        "%player%", player.getName()));
                    });
                } else {
                    utils.sendMsg(sender, utils.getPrefix() + StringUtils.replace(utils.getMsgString(MessageEnum.KABOOMA),
                            "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    utils.sendMsg(sender, utils.getPrefix() +
                            StringUtils.replace(utils.getMsgString(MessageEnum.KABOOMS),
                                    "%player%", player.getName()));
                });
            }
        }, this.canSkip("kaboom player", targets, sender));
    }
}
