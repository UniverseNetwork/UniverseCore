package id.universenetwork.universecore.command;

import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.CommandInfo;
import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static id.universenetwork.universecore.utils.utils.colors;

@CommandInfo(name = "kaboom",
        argsLength = 1, permission = "universenetwork.kaboom",
        onlyPlayer = false, usage = "/kaboom")
public class KaboomCommand extends UNCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.setVelocity(p.getLocation().getDirection().setY(10));
            } else {
                sender.sendMessage(MessageData.getInstance().getString(Message.ONLYPLAYER));
                sender.sendMessage(colors("&eUsage: &6/kaboom (player)"));
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("universenetwork.kaboom.others")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    sender.sendMessage(MessageData.getInstance().getString(Message.KABOOMS).replaceAll("%player%", t.getDisplayName()));
                    t.setVelocity(t.getLocation().getDirection().setY(10));
                    t.getLocation().getWorld().strikeLightning(t.getLocation());
                    t.sendMessage(MessageData.getInstance().getString(Message.KABOOMT));
                } else sender.sendMessage(MessageData.getInstance().getString(Message.NOPLAYER));
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args) {

        return null;
    }
}
