package id.universenetwork.universecore.Bukkit.command.Troll;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class SantetCommand extends UNCommand {

    @CommandMethod("santet|usantet [target]")
    public void commandsantet(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        if (!utils.checkPermission(sender, "santet")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);
        if (targets.notifyIfEmpty()) {
            return;
        }

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !utils.checkPermission(sender, "santet", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                Objects.requireNonNull(player.getLocation().getWorld()).strikeLightning(player.getLocation());
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,100,5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,100,1));
                player.sendTitle(MessageData.getInstance().getString(MessageEnum.SANTETT), null, 20, 40, 20);
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        utils.sendMsg(sender, utils.getPrefix() +
                                StringUtils.replace(utils.getMsgString(MessageEnum.SANTETS),
                                        "%player%", player.getName()));
                    });
                } else {
                    utils.sendMsg(sender, utils.getPrefix() +
                            StringUtils.replace(utils.getMsgString(MessageEnum.SANTETA),
                                    "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    utils.sendMsg(sender, utils.getPrefix() +
                            StringUtils.replace(utils.getMsgString(MessageEnum.SANTETS),
                                    "%player%", player.getName()));
                });
            }
        }, this.canSkip("santet player", targets, sender));
    }
}
