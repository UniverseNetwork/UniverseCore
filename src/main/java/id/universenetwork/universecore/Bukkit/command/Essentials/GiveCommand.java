package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GiveCommand extends UNCommand {

    @CommandMethod("give|ugive <target> <item> [amount]")
    public void commandGive(final @NonNull CommandSender sender,
                        final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
                        final @NonNull @Argument(value = "item", defaultValue = "dirt") Material material,
                        final @NonNull @Argument(value = "amount", defaultValue = "1") Integer amount) {
        if (!utils.checkPermission(sender, "give")) {
            return;
        }

        ItemStack item = new ItemStack(material, amount);

        TargetsCallback targets = this.getTargets(sender, targetName);

        core.getConfirmationManager().requestConfirm(() -> {
            targets.forEach(player -> {
                player.getInventory().addItem(item);
                player.updateInventory();
                utils.sendMsg(player, utils.getPrefix() + "&7You have been given &a" + amount + " &e" + item.getType().toString() + "&7.");
            });

            if (targets.size() > 1) {
                utils.sendMsg(sender, utils.getPrefix() + "&7You have been given &a" + amount + " &e" + item.getType().toString() + " &7to &6" + targets.size() + " players&7.");
            } else if ((!(sender instanceof Player)) || targets.doesNotContain((Player) sender) && !targetName.equals("self")) {
                targets.stream().findFirst().ifPresent(player ->
                        utils.sendMsg(sender, utils.getPrefix() + "&7You have been given &a" + amount + " &e" + item.getType().toString() + " &7to &6" + player.getName() + " players&7."));
            }
        }, this.canSkip("give item", targets, sender));
    }

    @CommandMethod("item|uitem|i <item> [amount]")
    public void commandItem(final @NonNull Player player,
                            final @NonNull @Argument("item") Material material,
                            final @NonNull @Argument(value = "amount", defaultValue = "1") Integer amount) {
        if (!utils.checkPermission(player, "give")) {
            return;
        }

        ItemStack item = new ItemStack(material, amount);

        player.getInventory().addItem(item);
        player.updateInventory();
        utils.sendMsg(player, utils.getPrefix() + "&7You have been given &a" + amount + " &e" + item.getType().toString() + "&7.");
    }
}
