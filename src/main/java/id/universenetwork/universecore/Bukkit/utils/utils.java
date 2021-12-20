package id.universenetwork.universecore.Bukkit.utils;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class utils {

    private final UniverseCore core;

    static {
        core = UniverseCore.getInstance();
    }

    /**
     * @return
     */
    public String getPrefix() {
        return MessageData.getInstance().getString(MessageEnum.PREFIX);
    }

    /**
     * @param sender
     * @param msg
     */
    public void sendMsg(CommandSender sender, String msg) {
        sender.sendMessage(colors(msg));
    }

    /**
     * @param sender
     * @param msg
     */
    public void sendSpigotMsg(CommandSender sender, BaseComponent msg) {
        sender.spigot().sendMessage(msg);
    }

    public void sendTitle(Player player, @Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(utils.colors(title), utils.colors(subtitle), fadeIn, stay, fadeOut);
    }

    /**
     * @param message
     * @return
     */
    public String getMsgString(MessageEnum message) {
        return MessageData.getInstance().getString(message);
    }

    /**
     * @param message
     * @return
     */
    public List<String> getMsgStringList(MessageEnum message) {
        return MessageData.message.getConfig().getStringList(message.getPath());
    }

    /**
     * @param msg
     * @return
     */
    public String colors(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * @param lore
     * @return
     */
    public List<String> colorsArray(List<String> lore) {

        for ( int i = 0 ; i < lore.size() ; i++ ) {
            lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        }
        return lore;
    }

    /**
     * @param sender
     * @param permission
     * @return
     */
    public boolean checkPermission(CommandSender sender, String permission) {
        return utils.checkPermission(sender, permission, false);
    }

    /**
     * @param sender
     * @param permission
     * @param others
     * @return
     */
    public boolean checkPermission(CommandSender sender, String permission, boolean others) {
        permission = "universenetwork." + permission.toLowerCase();

        if (others) {
            permission += ".others";
        }

        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPERM));
        }
        return false;
    }

    /**
     * @param potionEffectType
     * @param duration
     * @param amplifier
     */
    public void addEffectToAllPlayer(PotionEffectType potionEffectType, int duration, int amplifier) {
        Bukkit.getOnlinePlayers().forEach(p -> p.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier)));
    }

    /**
     * @param partialName name
     * @return return
     */
    public List<String> getOnlinePlayers(String partialName) {
        return filterStartingWith(partialName, Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName));
    }

    /**
     * @param prefix prefix
     * @param stream stream
     * @return List<String> filtered by prefix and stream to list
     */
    public List<String> filterStartingWith(String prefix, Stream<String> stream) {
        return stream.filter(s -> s != null && !s.isEmpty() && s.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * @param listeners listener
     */
    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
                Bukkit.getServer().getPluginManager().registerEvents(listener, UniverseCore.getInstance());
        }
    }

    /**
     * @param seconds seconds
     * @return String of time format from milliseconds to days,hours,minutes,seconds from input parameter (int) (ex: 1 days 15 hours 32 minutes 32 seconds) or (ex: 01 seconds)
     * @throws IllegalArgumentException if seconds is less than 0
     */
    public String getTimeFormat(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds must be greater than 0");
        }

        int days = seconds / 86400;
        int hours = (seconds % 86400) / 3600;
        int minutes = (seconds % 3600) / 60;
        int secondsLeft = seconds % 60;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append(" days ");
        }

        if (hours > 0) {
            sb.append(hours).append(" hours ");
        }

        if (minutes > 0) {
            sb.append(minutes).append(" minutes ");
        }

        if (secondsLeft > 0) {
            sb.append(secondsLeft).append(" seconds");
        }

        return sb.toString();
    }

    public String getTimeFormats(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds must be greater than 0");
        }

        int days = seconds / 86400;
        int hours = (seconds % 86400) / 3600;
        int minutes = ((seconds % 86400) % 3600) / 60;
        int seconds2 = ((seconds % 86400) % 3600) % 60;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append(" days ");
        }

        if (hours > 0) {
            sb.append(hours).append(" hours ");
        }

        if (minutes > 0) {
            sb.append(minutes).append(" minutes ");
        }

        if (seconds2 > 0) {
            sb.append(seconds2).append(" seconds");
        }

        return sb.toString();
    }

}
