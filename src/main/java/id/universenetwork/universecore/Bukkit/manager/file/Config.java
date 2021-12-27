package id.universenetwork.universecore.Bukkit.manager.file;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.ConfigEnum;
import id.universenetwork.universecore.Bukkit.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Config {
    private static final Config instance = new Config();
    public static ConfigManager cfg = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "config.yml");

    public static Config getInstance() {
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

    public String getString(ConfigEnum e) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(cfg.getConfig().getString(e.getPath())));
    }

    public boolean getBoolean(ConfigEnum e) {
        return cfg.getConfig().getBoolean(e.getPath());
    }
}
