package id.universenetwork.universecore.Bukkit.manager.file;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class MessageFile {
    private static final MessageFile instance = new MessageFile();
    public static ConfigManager message = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "message.yml");

    public static MessageFile getInstance() {
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
        String sb = "";
        for (String a : MessageFile.message.getConfig().getStringList(e.getPath())) {
            return ChatColor.translateAlternateColorCodes('&', a);
        }
        return sb;
    }
}
