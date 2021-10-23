package id.universenetwork.universecore.manager;

import id.universenetwork.universecore.UniverseCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {
    
    private final UniverseCore core;
    private FileConfiguration dataConfig = null;
    private File dataConfigFile = null;
    private final String name;

    public ConfigManager(UniverseCore core, String name) {
        this.core = core;
        this.name = name;
        this.saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.dataConfigFile == null) {
            this.dataConfigFile = new File(this.core.getDataFolder(), this.name);
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.dataConfigFile);
        InputStream defConfigStream = this.core.getResource(this.name);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            this.dataConfig.setDefaults(defConfig);
        }

    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            this.reloadConfig();
        }

        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig != null && this.dataConfigFile != null) {
            try {
                this.getConfig().save(this.dataConfigFile);
            } catch (IOException e) {
                this.core.getLogger().log(Level.SEVERE, "Could not save config to " + this.dataConfigFile, e);
            }

        }
    }

    public void saveDefaultConfig() {
        if (this.dataConfigFile == null) {
            this.dataConfigFile = new File(this.core.getDataFolder(), this.name);
        }

        if (!this.dataConfigFile.exists()) {
            this.core.saveResource(this.name, false);
        }

    }
}
