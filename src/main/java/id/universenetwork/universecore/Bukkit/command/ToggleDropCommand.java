package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ToggleDropCommand extends UNCommand {

    private ArrayList<Player> td = new ArrayList<>();

    public ToggleDropCommand() {
        super("toggledrop", "universenetwork.toggledrop",
                "/toggledrop", null, 0, true,
                "td", "utd", "utoggledrop");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageData.getInstance().getString(MessageEnum.ONLYPLAYER));
        }

        if (!td.contains(p)) {
            td.add(p);
            sender.sendMessage(MessageData.getInstance().getString(MessageEnum.TDON));
        } else {
            td.remove(p);
            sender.sendMessage(MessageData.getInstance().getString(MessageEnum.TDOFF));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        return null;
    }

    public boolean contains(Player p){
        return td.contains(p);
    }
}
