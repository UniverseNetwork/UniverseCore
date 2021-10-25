package id.universenetwork.universecore.utils;

import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class utils {

    public static String getPrefix() {
        return MessageData.getInstance().getString(Message.PREFIX);
    }

    public static String colors(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void addEffectToAllPlayer(PotionEffectType potionEffectType, int duration, int amplifier) {
        Bukkit.getOnlinePlayers().forEach(p -> p.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier)));
    }

    public static List<String> getOnlinePlayers(String partialName) {
        return filterStartingWith(partialName, Bukkit.getOnlinePlayers().stream().map(Player::getName));
    }

    public static List<String> filterStartingWith(String prefix, Stream<String> stream) {
        return stream.filter(s -> s != null && !s.isEmpty() && s.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

}
