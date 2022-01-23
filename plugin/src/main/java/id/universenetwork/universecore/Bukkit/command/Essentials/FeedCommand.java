package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Flag;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class FeedCommand extends UNCommand {

    @CommandMethod("feed|ufeed [target]")
    public void feedCMD(final @NonNull CommandSender sender,
                        final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
                        final @Nullable @Flag(value = "silent", aliases = "s") Boolean silent) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "feed", true)) return;

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                feedPlayer(player);
                if (silent == null || !silent) Utils.sendMsg(player, Utils.getPrefix() + Utils.getMsgString(MessageEnum.FEEDM));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> {
                        Utils.sendMsg(sender, Utils.getPrefix() +
                                StringUtils.replace(Utils.getMsgString(MessageEnum.FEEDS),
                                        "%player%", player.getName()));
                    });
                } else {
                    Utils.sendMsg(sender, Utils.getPrefix() + StringUtils.replace(Utils.getMsgString(MessageEnum.FEEDSA),
                            "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> {
                    Utils.sendMsg(sender, Utils.getPrefix() +
                            StringUtils.replace(Utils.getMsgString(MessageEnum.FEEDS),
                                    "%player%", player.getName()));
                });
            }
        }, this.canSkip("feed all", targets, sender));
    }

    private void feedPlayer(Player player) {
        int amount = 30;

        final FoodLevelChangeEvent fl = new FoodLevelChangeEvent(player, amount);

        player.setFoodLevel(Math.min(fl.getFoodLevel(), amount));
        player.setSaturation(10f);
        player.setExhaustion(0f);
    }

}
