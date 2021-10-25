package id.universenetwork.universecore.command;

import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.CommandInfo;
import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.MessageData;
import id.universenetwork.universecore.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static id.universenetwork.universecore.utils.utils.colors;

@CommandInfo(name = "santet", argsLength = 1,
        permission = "universenetwork.santet",
        onlyPlayer = false, usage = "/santet (Player)")
public class SantetCommand extends UNCommand {

    @Override
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            s.sendMessage(colors("&6Usage: &e" + getCommandInfo().usage()));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (args[0].equalsIgnoreCase("*")) {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    s.sendMessage(MessageData.getInstance().getString(Message.NOPLAYER));
                }
                s.sendMessage(MessageData.getInstance().getString(Message.SANTETA));
                utils.addEffectToAllPlayer(PotionEffectType.SLOW, 100, 1);
                Bukkit.getOnlinePlayers().forEach(all ->
                        all.sendMessage(MessageData.getInstance().getString(Message.SANTETT)));
            }
            if (t != null) {
                s.sendMessage(MessageData.getInstance().getString(Message.SANTETS).replaceAll("%player%", t.getDisplayName()));
                t.getLocation().getWorld().strikeLightning(t.getLocation());
                t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,5));
                t.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,100,5));
                t.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,5));
                t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,2));
                t.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,100,1));
                t.sendTitle(MessageData.getInstance().getString(Message.SANTETT), null, 20, 40, 20);
            } else s.sendMessage(MessageData.getInstance().getString(Message.NOPLAYER));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission(getCommandInfo().permission())) {
                return new ArrayList<>(Arrays.asList("*"));
            }
        }

        return utils.getOnlinePlayers(args[0]);
    }
}
