package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bungee.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class PingCommand extends UNCommand {

    Class<?> CPClass;

    String serverName = Bukkit.getServer().getClass().getPackage().getName(),
            serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1);

    public PingCommand() {
        super("ping", "universenetwork.ping", "/ping <player>",
                null, 1, false, "uping");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                sender.sendMessage(MessageData.getInstance().getString(MessageEnum.PINGMSG)
                        .replaceAll("%ping%", String.valueOf(getPing(p))));
            } else sender.sendMessage(MessageData.getInstance().getString(MessageEnum.PINGCONSOLEMSG));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (sender.hasPermission("universenetwork.ping.others")) {
                if (t != null) {
                    sender.sendMessage(MessageData.getInstance().getString(MessageEnum.PINGMSGO)
                            .replaceAll("%ping%", String.valueOf(getPing(t)))
                            .replaceAll("%player%", t.getName()));
                } else sender.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPLAYER));
            } else sender.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPERM));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {

        if (args.length == 1) {
            return utils.getOnlinePlayers(args[0]);
        }

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
