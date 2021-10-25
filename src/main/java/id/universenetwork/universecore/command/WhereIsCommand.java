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

@CommandInfo(name = "whereis",
        argsLength = 1, permission = "universenetwork.whereis",
        onlyPlayer = false, usage = "/whereis (player)")
public class WhereIsCommand extends UNCommand {

    @Override
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            s.sendMessage(colors("&6Usage: &e" + getCommandInfo().usage()));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (t != null) {
                s.sendMessage(MessageData.getInstance().getStringList(Message.WHEREISMSG)
                        .replaceAll("%player%", t.getName())
                        .replaceAll("%world%", t.getLocation().getWorld().getName())
                        .replaceAll("%x%", String.valueOf(t.getLocation().getX()))
                        .replaceAll("%y%", String.valueOf(t.getLocation().getY()))
                        .replaceAll("%z%", String.valueOf(t.getLocation().getZ()))
                        .replaceAll("%yaw%", String.valueOf(t.getLocation().getYaw()))
                        .replaceAll("%pitch%", String.valueOf(t.getLocation().getPitch())));
            } else s.sendMessage(MessageData.getInstance().getString(Message.NOPLAYER));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
