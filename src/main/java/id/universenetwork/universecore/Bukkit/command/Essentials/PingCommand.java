package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageFile;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PingCommand extends UNCommand {

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
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.ADDSPECIFYPLAYER));
        }

        if (targetName.equals("self")) {
            targets.stream().findFirst().ifPresent(player -> {
                List<String> a = MessageFile.message.getConfig().getStringList(MessageEnum.PINGMSG.getPath());
                String b = StringUtils.join(a, "\n");
                utils.sendMsg(sender, StringUtils.replaceEach(
                        b,
                        new String[]{"%ping%", "%status%"},
                        new String[]{getPing(player.getPing()), getStatus(player.getPing())}
                ));
            });
        }


        if (others) {
            if (targets.size() == 1) {
                targets.stream().findFirst().ifPresent(player -> {
                    List<String> a = MessageFile.message.getConfig().getStringList(MessageEnum.PINGMSGO.getPath());
                    String b = StringUtils.join(a, "\n");
                    utils.sendMsg(sender, StringUtils.replaceEach(
                            b,
                            new String[]{"%player%", "%ping%", "%status%"},
                            new String[]{player.getName(), getPing(player.getPing()), getStatus(player.getPing())}));
                });
            } else if (targets.size() > 1) {
                if (!utils.checkPermission(sender, "ping.all")) {
                    return;
                }
                List<String> players = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    players.add("&e" + player.getName() + "&8(&r" + getPing(player.getPing()) + "&8)&r");
                }
                utils.sendMsg(sender, "&7Player ping List: " + StringUtils.join(players, ", "));
            }
        } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender) && !targetName.equals("self")) {
            targets.stream().findFirst().ifPresent(player -> {
                List<String> a = MessageFile.message.getConfig().getStringList(MessageEnum.PINGMSGO.getPath());
                String b = StringUtils.join(a, "\n");
                utils.sendMsg(sender, StringUtils.replaceEach(
                        b,
                        new String[]{"%player%", "%ping%", "%status%"},
                        new String[]{player.getName(), getPing(player.getPing()), getStatus(player.getPing())}));
            });
        }
    }

    public String getPing(int ping) {
        String msg;
        if (ping <= 0) {
            msg = "&b" + ping;
        } else if (ping <= 30) {
            msg = "&a" + ping;
        } else if (ping <= 110) {
            msg = "&2" + ping;
        } else if (ping <= 200) {
            msg = "&e" + ping;
        } else if (ping <= 500) {
            msg = "&c" + ping;
        } else {
            msg = "&4" + ping;
        }
        return msg;
    }

    public String getStatus(int ping) {
        String msg;
        if (ping <= 0) {
            msg = "&bLoading your ping! (Please wait).";
        } else if (ping <= 30) {
            msg = "&2Awesome Ping!";
        } else if (ping <= 110) {
            msg = "&aNice Ping!";
        } else if (ping <= 200) {
            msg = "&eAverage ping";
        } else if (ping <= 500) {
            msg = "&cBad Ping :(";
        } else {
            msg = "&4Your Ping is too Bad :(";
        }
        return msg;
    }

}
