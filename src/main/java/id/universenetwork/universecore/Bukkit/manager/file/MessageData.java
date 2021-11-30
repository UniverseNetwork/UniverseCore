package id.universenetwork.universecore.Bukkit.manager.file;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class MessageData {
    private static final MessageData instance = new MessageData();
    public static ConfigManager message = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "message.yml");

    public static MessageData getInstance() {
        return instance;
    }

    public FileConfiguration getMessage() {
        return message.getConfig();
    }

    public void reload() {
        message.reloadConfig();
    }

    public void saveConfig() {
        message.saveConfig();
    }

    public String getString(MessageEnum e) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(message.getConfig().getString(e.getPath())));
    }

    public String getStringList(MessageEnum e) {
        StringBuilder sb = new StringBuilder();
        for (String a : MessageData.message.getConfig().getStringList(e.getPath())) {
            return ChatColor.translateAlternateColorCodes('&', a);
        }
        return sb.toString();
    }
}
