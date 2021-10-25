package id.universenetwork.universecore.command;

import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.CommandInfo;
import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@CommandInfo(name = "ping", argsLength = 1,
        permission = "universenetwork.ping",
        onlyPlayer = false, usage = "/ping <player>")
public class PingCommand extends UNCommand {

    Class<?> CPClass;

    String serverName = Bukkit.getServer().getClass().getPackage().getName(),
            serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1);

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                sender.sendMessage(MessageData.getInstance().getString(Message.PINGMSG)
                        .replaceAll("%ping%", String.valueOf(getPing(p))));
            } else sender.sendMessage(MessageData.getInstance().getString(Message.PINGCONSOLEMSG));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (sender.hasPermission("universenetwork.ping.others")) {
                if (t != null) {
                    sender.sendMessage(MessageData.getInstance().getString(Message.PINGMSGO)
                            .replaceAll("%ping%", String.valueOf(getPing(t)))
                            .replaceAll("%player%", t.getName()));
                } else sender.sendMessage(MessageData.getInstance().getString(Message.NOPLAYER));
            } else sender.sendMessage(MessageData.getInstance().getString(Message.NOPERM));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }

    public int getPing(Player p) {
        try {
            CPClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
            Object CraftPlayer = CPClass.cast(p);

            Method getHandle = CraftPlayer.getClass().getMethod("getHandle");
            Object EntityPlayer = getHandle.invoke(CraftPlayer);

            Field ping = EntityPlayer.getClass().getDeclaredField("ping");

            return ping.getInt(EntityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
