package id.universenetwork.universecore.Bukkit.manager.file;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.manager.ConfigManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomPressurePlateAction {
    private static final CustomPressurePlateAction instance = new CustomPressurePlateAction();
    public static ConfigManager file = new ConfigManager(UniverseCore.getPlugin(UniverseCore.class), "CustomPressurePlateAction.yml");

    List<Material> listPlate = Arrays.asList(
            Material.STONE_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
            Material.OAK_PRESSURE_PLATE,
            Material.SPRUCE_PRESSURE_PLATE,
            Material.BIRCH_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,
            Material.ACACIA_PRESSURE_PLATE,
            Material.DARK_OAK_PRESSURE_PLATE,
            Material.CRIMSON_PRESSURE_PLATE,
            Material.WARPED_PRESSURE_PLATE);

    public List<Material> getListPlate() {
        return listPlate;
    }

    public static CustomPressurePlateAction getInstance() {
        return instance;
    }

    public FileConfiguration getConfig() {
        return file.getConfig();
    }

    public void reload() {
        file.reloadConfig();
    }

    public void saveConfig() {
        file.saveConfig();
    }

    public void set(String path, Object value) {
        file.getConfig().set(path, value);
        this.saveConfig();
    }

    public void removeId(String path) {
        set("PressurePlate." + path, null);
        this.saveConfig();
    }

    public ConfigurationSection getSection(String path) {
        if (getConfig().getConfigurationSection(path) == null) {
            return null;
        } else {
            return getConfig().getConfigurationSection(path);
        }
    }

    public void setPressurePlate(String id, String world, double x, double y, double z, String action, String effect, int duration, int amplifier) {
        this.set("PressurePlate." + id + ".world", world);
        this.set("PressurePlate." + id + ".x", x);
        this.set("PressurePlate." + id + ".y", y);
        this.set("PressurePlate." + id + ".z", z);
        this.set("PressurePlate." + id + ".action", action);
        this.set("PressurePlate." + id + ".PotionEffect." + effect, effect);
        this.set("PressurePlate." + id + ".PotionEffect." + effect + ".duration", duration);
        this.set("PressurePlate." + id + ".PotionEffect." + effect + ".amplifier", amplifier);
    }

    public boolean PathIsExist(String path) {
        return file.getConfig().contains("PressurePlate." + path);
    }
}
