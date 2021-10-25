package id.universenetwork.universecore.command;

import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.CommandInfo;
import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "toggledrop",
        permission = "universenetwork.toggledrop",
        onlyPlayer = true, usage = "/toggledrop")
public class ToggleDropCommand extends UNCommand {

    public static ArrayList<Player> td = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageData.getInstance().getString(Message.ONLYPLAYER));
        }

        if (!td.contains(p)) {
            td.add(p);
            sender.sendMessage(MessageData.getInstance().getString(Message.TDON));
        } else {
            td.remove(p);
            sender.sendMessage(MessageData.getInstance().getString(Message.TDOFF));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
