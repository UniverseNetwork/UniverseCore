package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static id.universenetwork.universecore.Bukkit.utils.utils.colors;

public class WhereIsCommand extends UNCommand {

    public WhereIsCommand() {
        super("whereis",
                "universenetwork.whereis",
                "/whereis (player)",
                null, 1, true, "uwhereis", "wis", "uwis");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            s.sendMessage(colors("&6Usage: &e" + getUsage()));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (t != null) {
                for (String a : MessageData.message.getConfig().getStringList(MessageEnum.WHEREISMSG.getPath())) {
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', a).replaceAll("%player%", t.getName())
                            .replaceAll("%world%", t.getLocation().getWorld().getName())
                            .replaceAll("%x%", String.valueOf(t.getLocation().getX()))
                            .replaceAll("%y%", String.valueOf(t.getLocation().getY()))
                            .replaceAll("%z%", String.valueOf(t.getLocation().getZ()))
                            .replaceAll("%yaw%", String.valueOf(t.getLocation().getYaw()))
                            .replaceAll("%pitch%", String.valueOf(t.getLocation().getPitch())));
                    StringUtils.replace(a, "%player%", t.getName());
                    StringUtils.replace(a, "%world%", t.getLocation().getWorld().getName());
                    StringUtils.replace(a, "%x%", String.valueOf(t.getLocation().getX()));
                    StringUtils.replace(a, "%y%", String.valueOf(t.getLocation().getY()));
                    StringUtils.replace(a, "%z%", String.valueOf(t.getLocation().getZ()));
                    StringUtils.replace(a, "%yaw%", String.valueOf(t.getLocation().getYaw()));
                    StringUtils.replace(a, "%pitch%", String.valueOf(t.getLocation().getPitch()));
                }
            } else s.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPLAYER));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        return null;
    }
}
