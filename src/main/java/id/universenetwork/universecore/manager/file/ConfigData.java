package id.universenetwork.universecore.manager.file;

import id.universenetwork.universecore.UniverseCore;
import id.universenetwork.universecore.manager.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigData {
    private static final ConfigData instance = new ConfigData();
    public static ConfigManager cfg = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "config.yml");

    public static ConfigData getInstance() {
        return instance;
    }

    public FileConfiguration getConfig() {
        return cfg.getConfig();
    }

    public void reload() {
        cfg.reloadConfig();
    }

    public void saveConfig() {
        cfg.saveConfig();
    }
}
