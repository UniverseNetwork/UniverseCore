package id.universenetwork.universecore.Bukkit.utils;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class utils {

    public static String getPrefix() {
        return MessageData.getInstance().getString(MessageEnum.PREFIX);
    }

    public static String colors(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> colorsArray(List<String> lore) {

        for ( int i = 0 ; i < lore.size() ; i++ ) {
            lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        }
        return lore;
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

    public static void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
                Bukkit.getServer().getPluginManager().registerEvents(listener, UniverseCore.getInstance());
        }
    }

}
