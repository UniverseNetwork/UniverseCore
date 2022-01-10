package id.universenetwork.universecore.Bukkit.command.Troll;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CovidCommand extends UNCommand {

    @CommandMethod("covid|copid|covid19|ucovid|ucopid|ucovid19 [target]")
    public void covidCommand(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName
            ) {

        if (!Utils.checkPermission(sender, "covid")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);
        if (targets.notifyIfEmpty()) {
            return;
        }

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "covid", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 9));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 9));
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));

                for (int i = 0; i < 3; i++) {
                    Bat bat = (Bat) player.getWorld().spawnEntity(player.getLocation(), EntityType.BAT);
                    bat.setCustomName(Utils.colors("&cCovid-19"));
                    bat.setCustomNameVisible(true);
                    Bukkit.getScheduler().runTaskLater(core, () -> {
                        bat.setHealth(0);
                    }, 100L);
                }
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        Utils.sendMsg(sender, StringUtils.replace(
                                Utils.getMsgString(MessageEnum.COVIDS), "%player%", player.getName()
                        ));
                        core.getMultiVersion().sendTitle(player, Utils.colors(Utils.getMsgString(MessageEnum.COVIDT)), Utils.colors(Utils.getMsgString(MessageEnum.COVIDST)), 30, 40, 30);
                        /*player.sendTitle(Utils.colors(Utils.getMsgString(MessageEnum.COVIDT)), Utils.colors(Utils.getMsgString(MessageEnum.COVIDST)), 30, 40, 30);*/
                    });
                } else {
                    Utils.sendMsg(sender, StringUtils.replace(
                            Utils.getMsgString(MessageEnum.COVIDSA), "%player%", String.valueOf(targets.size())
                    ));
                    targets.stream().forEach(player -> {
                        core.getMultiVersion().sendTitle(player, Utils.colors(Utils.getMsgString(MessageEnum.COVIDTA)), Utils.colors(Utils.getMsgString(MessageEnum.COVIDST)), 30, 40, 30);
                        /*player.sendTitle(Utils.colors(Utils.getMsgString(MessageEnum.COVIDTA)), Utils.colors(Utils.getMsgString(MessageEnum.COVIDST)), 30, 40, 30);*/
                    });
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    Utils.sendMsg(sender, StringUtils.replace(
                            Utils.getMsgString(MessageEnum.COVIDS), "%player%", player.getName()
                    ));
                    core.getMultiVersion().sendTitle(player, Utils.colors(Utils.getMsgString(MessageEnum.COVIDT)), Utils.colors(Utils.getMsgString(MessageEnum.COVIDST)), 30, 40, 30);
                    /*player.sendTitle(Utils.colors(Utils.getMsgString(MessageEnum.COVIDT)), Utils.colors(Utils.getMsgString(MessageEnum.COVIDST)), 30, 40, 30);*/
                });
            }

        }, this.canSkip("covid19", targets, sender));

    }

}
