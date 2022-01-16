package id.universenetwork.universecore.Bukkit.command.Troll;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.data.FreezeData;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class FreezeCommand extends UNCommand {

    @CommandMethod("freeze [target]")
    @CommandPermission("universenetwork.freeze")
    public void freezeCommand(final @NonNull CommandSender sender,
                              final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "freeze", true)) return;

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                FreezeData freeze = new FreezeData(player.getUniqueId());

                if (!targetName.equals("self") && sender instanceof Player) {
                    if (player.getName().equals(sender.getName())) return;
                }

                freeze.setID(player.getUniqueId(), true);
                core.getMultiVersion().sendTitle(player, Utils.colors(Utils.getMsgString(MessageEnum.FREEZET)), null, 20, 50, 20);
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        Utils.sendMsg(sender, Utils.getPrefix() +
                                StringUtils.replace(Utils.getMsgString(MessageEnum.FREEZES),
                                        "%player%", player.getName()));
                    });
                } else {
                    Utils.sendMsg(sender, Utils.getPrefix() + StringUtils.replace(Utils.getMsgString(MessageEnum.FREEZESA),
                            "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    Utils.sendMsg(sender, Utils.getPrefix() +
                            StringUtils.replace(Utils.getMsgString(MessageEnum.FREEZES),
                                    "%player%", player.getName()));
                });
            }
        }, this.canSkip("freeze player", targets, sender));
    }

    @CommandMethod("unfreeze [target]")
    @CommandPermission("universenetwork.unfreeze")
    public void unfreezeCMD(final @NonNull CommandSender sender,
                            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "unfreeze", true)) return;

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                FreezeData freeze = new FreezeData(player.getUniqueId());

                if (freeze.hasID()) {
                    freeze.removeID(player.getUniqueId());
                    core.getMultiVersion().sendTitle(player, Utils.colors(Utils.getMsgString(MessageEnum.UNFREEZET)), null, 20, 50, 20);
                }
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        Utils.sendMsg(sender, Utils.getPrefix() +
                                StringUtils.replace(Utils.getMsgString(MessageEnum.UNFREEZES),
                                        "%player%", player.getName()));
                    });
                } else {
                    Utils.sendMsg(sender, Utils.getPrefix() + StringUtils.replace(Utils.getMsgString(MessageEnum.UNFREEZESA),
                            "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    Utils.sendMsg(sender, Utils.getPrefix() +
                            StringUtils.replace(Utils.getMsgString(MessageEnum.UNFREEZES),
                                    "%player%", player.getName()));
                });
            }
        }, this.canSkip("unfreeze all", targets, sender));

    }

}
