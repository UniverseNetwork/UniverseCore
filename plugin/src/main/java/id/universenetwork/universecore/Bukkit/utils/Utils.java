package id.universenetwork.universecore.Bukkit.utils;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.file.MessageFile;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Utils {

    private final UniverseCore core;

    @Getter
    private final String nmsVersion;

    static {
        core = UniverseCore.getInstance();
        nmsVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
    }

    /**
     * @return Prefix message
     */
    public String getPrefix() {
        return MessageFile.getInstance().getString(MessageEnum.PREFIX);
    }

    /**
     * @param sender the receiver
     * @param msg    message to send
     */
    public void sendMsg(@NotNull CommandSender sender, String msg) {
        sender.sendMessage(colors(msg));
    }

    /**
     * @param sender    the sender
     * @param msg       the message
     */
    public void sendSpigotMsg(@NotNull CommandSender sender, BaseComponent msg) {
        sender.spigot().sendMessage(msg);
    }

    /**
     * @param message   the message from MessageEnum
     * @return          MessageFile
     */
    public String getMsgString(MessageEnum message) {
        return MessageFile.getInstance().getString(message);
    }

    /**
     * @param message   the message from MessageEnum
     * @return          MessageFile
     */
    public List<String> getMsgStringList(@NotNull MessageEnum message) {
        return MessageFile.message.getConfig().getStringList(message.getPath());
    }

    /**
     * @param text   the message
     * @return      ChatColor#translateAlternateColorCodes();
     */
    public String colors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * @param lore  the list
     * @return      lore
     */
    public List<String> colorsArray(@NotNull List<String> lore) {

        for ( int i = 0 ; i < lore.size() ; i++ ) {
            lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        }
        return lore;
    }

    /**
     * @param sender        the sender
     * @param permission    the permission
     * @return              Utils#checkPermission(sender, permission, false);
     */
    public boolean checkPermission(CommandSender sender, String permission) {
        return Utils.checkPermission(sender, permission, false);
    }

    /**
     * @param sender        the sender
     * @param permission    the permission
     * @param others        boolean others or not
     * @return              false
     */
    public boolean checkPermission(CommandSender sender, String permission, boolean others) {
        permission = "universenetwork." + permission.toLowerCase();

        if (others) {
            permission += ".others";
        }

        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage(MessageFile.getInstance().getString(MessageEnum.NOPERM));
        }
        return false;
    }

    /**
     * @param potionEffectType potion type
     * @param duration duration of potion
     * @param amplifier level of the potion
     */
    public void addEffectToAllPlayer(PotionEffectType potionEffectType, int duration, int amplifier) {
        Bukkit.getOnlinePlayers().forEach(p -> p.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier)));
    }

    /**
     * @param partialName name
     * @return filterStartingWith
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
