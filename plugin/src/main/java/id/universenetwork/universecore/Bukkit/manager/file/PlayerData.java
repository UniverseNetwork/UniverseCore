package id.universenetwork.universecore.Bukkit.manager.file;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.manager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerData {
    private static final PlayerData instance = new PlayerData();
    public static ConfigManager config = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "playerdata.yml");

    public static PlayerData getInstance() {
        return instance;
    }

    public FileConfiguration getConfig() {
        return config.getConfig();
    }

    public void reload() {
        config.reloadConfig();
    }

    public void saveConfig() {
        config.saveConfig();
    }

    public int getInt(String path) {
        return config.getConfig().getInt(path);
    }

    public double getDouble(String path) {
        return config.getConfig().getDouble(path);
    }

    public String getString(String path) {
        return config.getConfig().getString(path);
    }

    public ConfigurationSection getSection(String path) {
        if (Objects.isNull(getConfig().getConfigurationSection(path))) {
            return null;
        } else {
            return getConfig().getConfigurationSection(path);
        }
    }

    public void set(String path, Object value) {
        config.getConfig().set(path, value);
    }

    public void setPlayerData(Player player, String type, Object value) {
        String types = player.getUniqueId() + "." + type;

        this.set(types, value);
    }

    public void removePlayerData(Player player, String type) {
        String types = player.getUniqueId() + "." + type;

        this.set(types, null);
    }

    public boolean hasPlayerData(Player player, String type) {
        String types = player.getUniqueId() + "." + type;
        return getConfig().contains(types);
    }

    public Boolean getStatus(Player player, String type) {
        String types = player.getUniqueId() + "." + type;
        return this.getConfig().getBoolean(types);
    }

}
