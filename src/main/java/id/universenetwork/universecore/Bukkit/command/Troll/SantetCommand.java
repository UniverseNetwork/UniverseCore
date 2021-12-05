package id.universenetwork.universecore.Bukkit.command.Troll;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

import static id.universenetwork.universecore.Bukkit.utils.utils.colors;

public class SantetCommand extends UNCommand {

    public SantetCommand() {
        super("santet", "universenetwork.santet", "/santet (Player)", null,
                1, false,"usantet");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            s.sendMessage(colors("&6Usage: &e" + getUsage()));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (args[0].equalsIgnoreCase("*")) {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    s.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPLAYER));
                }
                s.sendMessage(MessageData.getInstance().getString(MessageEnum.SANTETA));
                utils.addEffectToAllPlayer(PotionEffectType.SLOW, 100, 1);
                Bukkit.getOnlinePlayers().forEach(all ->
                        all.sendMessage(MessageData.getInstance().getString(MessageEnum.SANTETT)));
            }
            if (t != null) {
                s.sendMessage(MessageData.getInstance().getString(MessageEnum.SANTETS).replaceAll("%player%", t.getDisplayName()));
                Objects.requireNonNull(t.getLocation().getWorld()).strikeLightning(t.getLocation());
                t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,5));
                t.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,100,5));
                t.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,5));
                t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,100,2));
                t.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,100,1));
                t.sendTitle(MessageData.getInstance().getString(MessageEnum.SANTETT), null, 20, 40, 20);
            } else s.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPLAYER));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission(getPermission())) {
                return utils.getOnlinePlayers(args[0]);
            }
        }

        return utils.getOnlinePlayers(args[0]);
    }
}
