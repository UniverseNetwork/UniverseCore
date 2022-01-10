package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.data.ToggleDropData;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ToggleDropCommand extends UNCommand {

    @CommandMethod("toggledrop|td [toggle] [target]")
    public void commandToggleDrop(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "toggle", defaultValue = "none", suggestions = "toggles") String toggle,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        if (!Utils.checkPermission(sender, "toggledrop")) return;

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "toggledrop", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.forEach(player -> {
                ToggleDropData td = new ToggleDropData(player.getUniqueId());
                switch (toggle) {
                    case "none":
                    case "toggle": {
                        if (!td.hasID()) {
                            td.setId(player.getUniqueId(), true);
                            Utils.sendMsg(player, Utils.getMsgString(MessageEnum.TDON));
                            break;
                        }
                        td.removeID(player.getUniqueId());
                        Utils.sendMsg(player, Utils.getMsgString(MessageEnum.TDOFF));
                        break;
                    }
                    case "on": {
                        td.setId(player.getUniqueId(), true);
                        Utils.sendMsg(player, Utils.getMsgString(MessageEnum.TDON));
                        break;
                    }
                    case "off": {
                        td.removeID(player.getUniqueId());
                        Utils.sendMsg(player, Utils.getMsgString(MessageEnum.TDOFF));
                        break;
                    }
                    default: {
                        Utils.sendMsg(player, Utils.getPrefix() + "&cWrong value!");
                    }
                }
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        ToggleDropData td = new ToggleDropData(player.getUniqueId());
                        Utils.sendMsg(player, td.checkID(player.getUniqueId()) ?
                                StringUtils.replace(Utils.getMsgString(MessageEnum.TDONT), "%player%", player.getName()) :
                                StringUtils.replace(Utils.getMsgString(MessageEnum.TDOFFT), "%player%", player.getName()));
                    });
                } else {
                    Utils.sendMsg(sender, "&7Toggled drop for &e" + targets.size() + " &7players!");
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    ToggleDropData td = new ToggleDropData(player.getUniqueId());
                    Utils.sendMsg(player, td.checkID(player.getUniqueId()) ?
                            StringUtils.replace(Utils.getMsgString(MessageEnum.TDONT), "%player%", player.getName()) :
                            StringUtils.replace(Utils.getMsgString(MessageEnum.TDOFFT), "%player%", player.getName()));
                });
            }
        }, this.canSkip("toggledrop", targets, sender));

    }
}
