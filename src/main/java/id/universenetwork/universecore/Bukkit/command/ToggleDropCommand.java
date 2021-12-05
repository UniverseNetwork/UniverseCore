package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.data.ToggleDropData;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ToggleDropCommand extends UNCommand {

    public ToggleDropCommand() {
        super("toggledrop", "universenetwork.toggledrop",
                "/toggledrop", null, 0, true,
                "td", "utd", "utoggledrop");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ToggleDropData td = new ToggleDropData(p.getUniqueId());
            if (!td.hasID()) {
                sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.TDON)));
                td.setId(true);
            } else {
                sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.TDOFF)));
                td.removeID();
            }
        } else {
            sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.ONLYPLAYER)));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        return null;
    }
}
