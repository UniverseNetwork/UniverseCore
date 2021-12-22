package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import com.google.common.base.Joiner;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PingCommand extends UNCommand {

    Class<?> CPClass;

    String serverName = Bukkit.getServer().getClass().getPackage().getName(),
            serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1);

    @CommandMethod("Ping|uping [target]")
    public void commandPing(final @NonNull CommandSender sender,
                        final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        if (!utils.checkPermission(sender, "ping")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !utils.checkPermission(sender, "ping", true)) {
            return;
        }
        if (!(sender instanceof Player) || targets.doesNotContain((Player) sender) && targetName.equals("self")) {
            utils.sendMsg(sender, utils.getMsgString(MessageEnum.PINGCONSOLEMSG));
        }

        if (targetName.equals("self")) {
            targets.stream().findFirst().ifPresent(player -> {
                utils.sendMsg(sender, StringUtils.replace(
                        utils.getMsgString(MessageEnum.PINGMSG), "%ping%", String.valueOf(getPing(player))));
            });
        }

        if (others) {
            if (targets.size() == 1){
                targets.stream().findFirst().ifPresent(player -> {
                    utils.sendMsg(sender, StringUtils.replaceEach(
                            utils.getMsgString(MessageEnum.PINGMSGO), new String[]{"%player%", "%ping%"}, new String[]{player.getName(), String.valueOf(player.getPing())}));
                });
            } else if (targets.size() > 1) {
                List<String> players = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    players.add("&e" + player.getName() + "&8(&r" + player.getPing() + "&8)&r");
                }
                utils.sendMsg(sender, "&7Player ping List: " + StringUtils.join(players, ", "));
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender) && !targetName.equals("self")) {
                targets.stream().findFirst().ifPresent(player -> {
                    utils.sendMsg(sender, StringUtils.replaceEach(
                            utils.getMsgString(MessageEnum.PINGMSGO), new String[]{"%player%", "%ping%"}, new String[]{player.getName(), String.valueOf(player.getPing())}));
                });
            }
        }
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
