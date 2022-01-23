package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.Flag;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class HealCommand extends UNCommand {

    @CommandMethod("heal|uheal [target]")
    @CommandPermission("universenetwork.heal")
    public void healCMD(final @NonNull CommandSender sender,
                        final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
                        final @Nullable @Flag(value = "silent", aliases = "s") Boolean silent) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "heal", true)) return;

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setHealth(core.getMultiVersion().getMaxHealth(player));
                player.setFoodLevel(20);
                player.setFireTicks(0);
                for (PotionEffect potion : player.getActivePotionEffects()) {
                    player.removePotionEffect(potion.getType());
                }
                if (silent == null || !silent) Utils.sendMsg(player, Utils.getPrefix() + Utils.getMsgString(MessageEnum.HEALM));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        Utils.sendMsg(sender, Utils.getPrefix() +
                                StringUtils.replace(Utils.getMsgString(MessageEnum.HEALS),
                                        "%player%", player.getName()));
                    });
                } else {
                    Utils.sendMsg(sender, Utils.getPrefix() + StringUtils.replace(Utils.getMsgString(MessageEnum.HEALSA),
                            "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    Utils.sendMsg(sender, Utils.getPrefix() +
                            StringUtils.replace(Utils.getMsgString(MessageEnum.HEALS),
                                    "%player%", player.getName()));
                });
            }
        }, this.canSkip("heal all", targets, sender));

    }

}
