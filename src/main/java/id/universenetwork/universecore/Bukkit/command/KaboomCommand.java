package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static id.universenetwork.universecore.Bukkit.utils.utils.colors;

public class KaboomCommand extends UNCommand {

    public KaboomCommand() {
        super("kaboom", "universenetwork.kaboom", "/kaboom <player>", null, 1, false,
                "ukaboom", "boom", "uboom");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.setVelocity(p.getLocation().getDirection().setY(10));
            } else {
                sender.sendMessage(MessageData.getInstance().getString(MessageEnum.ONLYPLAYER));
                sender.sendMessage(colors("&eUsage: &6/kaboom (player)"));
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("universenetwork.kaboom.others")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    sender.sendMessage(MessageData.getInstance().getString(MessageEnum.KABOOMS).replaceAll("%player%", t.getDisplayName()));
                    t.setVelocity(t.getLocation().getDirection().setY(10));
                    t.getLocation().getWorld().strikeLightning(t.getLocation());
                    t.sendTitle(MessageData.getInstance().getString(MessageEnum.KABOOMT), null,20,40,20);
                } else sender.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPLAYER));
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {

        if (args.length == 1) {
            if (sender.hasPermission(getPermission())) {
                return utils.getOnlinePlayers(args[0]);
            }
            return null;
        }

        return null;
    }
}
