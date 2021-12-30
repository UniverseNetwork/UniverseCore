package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BroadCastCommand extends UNCommand {

    @CommandMethod("broadcast|bc|ubroadcast|ubc <message>")
    @CommandDescription("Broadcast message to all player!")
    @CommandPermission("universenetwork.broadcast")
    public void broadcastCommand(final @NonNull CommandSender sender,
                                 final @Argument(value = "message") @Greedy String message) {

        for (Player all : Bukkit.getOnlinePlayers()) {
            System.out.println(" ");
            System.out.println(CenterMessage.CenteredMessage("§e§lAnnouncement"));
            System.out.println(" ");
            System.out.println(message);
            System.out.println(" ");
            all.sendMessage(" ");
            CenterMessage.sendCentredMessage(all, "&e&lAnnouncement");
            all.sendMessage(" ");
            CenterMessage.sendCentredMessage(all, message);
            all.sendMessage(" ");
        }

    }
}
