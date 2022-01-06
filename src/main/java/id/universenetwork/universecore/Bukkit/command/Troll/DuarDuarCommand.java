package id.universenetwork.universecore.Bukkit.command.Troll;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DuarDuarCommand extends UNCommand {

    @CommandMethod("duarduar|uduarduar|dd|udd [target] [amount] [delay]")
    @CommandPermission("universenetwork.duarduar")
    public void duarduarCommand(final @NonNull CommandSender sender,
                                final @NonNull @Argument(value = "amount", defaultValue = "1", suggestions = "amountL",description = "amount of lightning to be spawned") Integer amount,
                                final @NonNull @Argument(value = "delay", defaultValue = "1", description = "delay after 1 lightning spawn") Long delay,
                                final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players", description = "the players") String targetName) {

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !utils.checkPermission(sender, "duarduar", true)) return;

        if (amount > 10) {
            sender.sendMessage("§cMaximal 10!");
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                for (int i = 0; i < amount; i++) {
                    Bukkit.getScheduler().runTaskLater(core, () -> {
                        randomLocation(player);
                        player.sendTitle(utils.colors("&cDuar Duar"), null, 10, 10, 10);
                    }, i * (delay * 20));
                }
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> utils.sendMsg(sender, utils.getPrefix() +
                            StringUtils.replace(utils.getMsgString(MessageEnum.DUARS),
                                    "%player%", player.getName())));
                } else {
                    utils.sendMsg(sender, utils.getPrefix() + StringUtils.replace(utils.getMsgString(MessageEnum.DUARA),
                            "%player%", String.valueOf(targets.size())));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> utils.sendMsg(sender, utils.getPrefix() +
                        StringUtils.replace(utils.getMsgString(MessageEnum.DUARS),
                                "%player%", player.getName())));
            }
        }, this.canSkip("duar duar", targets, sender));

    }

    @Suggestions("amountL")
    public List<String> amountOfLightningList(CommandContext<CommandSender> sender, String context) {
        return Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").filter(s -> s.startsWith(context)).collect(Collectors.toList());
    }

    public void randomLocation(@NotNull Player player) {
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        Random random = new Random();

        x = x + (random.nextInt(10) - 5);
        z = z + (random.nextInt(10) - 5);

        player.getWorld().strikeLightning(new Location(player.getWorld(), x, y, z));

    }

}
