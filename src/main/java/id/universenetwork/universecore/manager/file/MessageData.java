package id.universenetwork.universecore.manager.file;

import id.universenetwork.universecore.UniverseCore;
import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

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

    public static String getString(Message e) {
        return ChatColor.translateAlternateColorCodes('&', e.getPath());
    }
}
