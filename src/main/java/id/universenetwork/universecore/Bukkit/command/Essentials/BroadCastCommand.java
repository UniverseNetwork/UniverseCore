package id.universenetwork.universecore.Bukkit.command.Essentials;

import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class BroadCastCommand extends UNCommand {

    public BroadCastCommand() {
        super("broadcast", "universenetwork.broadcast", "/broadcast <message>", null,
                -1, false, "ubroadcast", "bc", "ubc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i]).append(" ");
            }
            String a = builder.toString();
            for (Player all : Bukkit.getOnlinePlayers()) {
                System.out.println(" ");
                System.out.println(CenterMessage.CenteredMessage("§e§lAnnouncement"));
                System.out.println(" ");
                System.out.println(CenterMessage.CenteredMessage(a));
                System.out.println(" ");
                all.sendMessage(" ");
                CenterMessage.sendCentredMessage(all, "&e&lAnnouncement");
                all.sendMessage(" ");
                CenterMessage.sendCentredMessage(all, a);
                all.sendMessage(" ");
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        return Collections.emptyList();
    }
}
