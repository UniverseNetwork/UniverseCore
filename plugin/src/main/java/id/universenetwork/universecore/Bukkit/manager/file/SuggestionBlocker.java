package id.universenetwork.universecore.Bukkit.manager.file;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.SuggestionEnum;
import id.universenetwork.universecore.Bukkit.manager.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SuggestionBlocker {
    private static final SuggestionBlocker instance = new SuggestionBlocker();
    public static ConfigManager message = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "suggestion-blocker.yml");

    public static SuggestionBlocker getInstance() {
        return instance;
    }

    public FileConfiguration getFile() {
        return message.getConfig();
    }

    public void reload() {
        message.reloadConfig();
    }

    public void saveConfig() {
        message.saveConfig();
    }

    public String getString(SuggestionEnum path) {
        return message.getConfig().getString(path.getPath());
    }

    public List<String> getStringList(SuggestionEnum path) {
        return message.getConfig().getStringList(path.getPath());
    }

    public boolean getBoolean(SuggestionEnum path) {
        return message.getConfig().getBoolean(path.getPath());
    }

    public Set<String> getConfigSection(SuggestionEnum path) {
        return Objects.requireNonNull(message.getConfig().getConfigurationSection(path.getPath())).getKeys(false);
    }

}
