package id.universenetwork.universecore.Bungee.manager;

import id.universenetwork.universecore.Bungee.UniverseCore;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static net.md_5.bungee.config.ConfigurationProvider.getProvider;

public class ConfigManager {

    final String configName = "bungeeconfig.yml";
    Configuration cfg;

    // Finds and Generates the config file
    public ConfigManager(boolean Reload) {
        if (Reload) System.out.println(" §eReloading Settings Manager...");
        else UniverseCore.getInstance().getLogger().info("§ePreparing Settings Manager...");
        try {
            if (!UniverseCore.getInstance().getDataFolder().exists()) UniverseCore.getInstance().getDataFolder().mkdirs();
            File configFile = new File(UniverseCore.getInstance().getDataFolder(), configName);
            if (!configFile.exists())
                try (InputStream in = UniverseCore.getInstance().getResourceAsStream(configName)) {
                    java.nio.file.Files.copy(in, configFile.toPath());
                }
            cfg = getProvider(YamlConfiguration.class).load(configFile);
            if (Reload) System.out.println(" §aSettings Manager have been reloaded");
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration", e);
        }
    }

    public void save() {
        try {
            File configFile = new File(UniverseCore.getInstance().getDataFolder(), configName);
            if (configFile.exists()) getProvider(YamlConfiguration.class).save(cfg, configFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save configuration", e);
        }
    }

    public Configuration getSection(String Path) {
        return cfg.getSection(Path);
    }

    public void set(String Path, Object Value) {
        cfg.set(Path, Value);
        save();
    }

    public Object get(String Path) {
        return cfg.get(Path);
    }

    public Object get(String Path, Object Default) {
        return cfg.get(Path, Default);
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrSetDefault(@NotNull String path, T value) {
        Object val = get(path);
        if (value.getClass().isInstance(val)) return (T) val;
        else {
            set(path, value);
            return value;
        }
    }

    public Object getDefault(String Path) {
        return cfg.getDefault(Path);
    }

    public String getString(String Path) {
        return cfg.getString(Path);
    }

    public String getString(String Path, String Default) {
        return cfg.getString(Path, Default);
    }

    public List<String> getStringList(String Path) {
        return cfg.getStringList(Path);
    }

    public boolean getBoolean(String Path) {
        return cfg.getBoolean(Path);
    }

    public boolean getBoolean(String Path, boolean Default) {
        return cfg.getBoolean(Path, Default);
    }

    public List<Boolean> getBooleanList(String Path) {
        return cfg.getBooleanList(Path);
    }

    public int getInt(String Path) {
        return cfg.getInt(Path);
    }

    public int getInt(String Path, int Default) {
        return cfg.getInt(Path, Default);
    }

    public int getInt(String path, int min, int max) {
        int val = getInt(path);
        if (val < min || val > max) set(path, val = (int) getDefault(path));
        return val;
    }

    public List<Integer> getIntList(String Path) {
        return cfg.getIntList(Path);
    }

    public double getDouble(String Path) {
        return cfg.getDouble(Path);
    }

    public double getDouble(String Path, double Default) {
        return cfg.getDouble(Path, Default);
    }

    public double getDouble(String path, double min, double max) {
        double val = getDouble(path);
        if (val < min || val > max) set(path, val = (double) getDefault(path));
        return val;
    }

    public List<Double> getDoubleList(String Path) {
        return cfg.getDoubleList(Path);
    }

    public long getLong(String Path) {
        return cfg.getLong(Path);
    }

    public long getLong(String Path, long Default) {
        return cfg.getLong(Path, Default);
    }

    public List<Long> getLongList(String Path) {
        return cfg.getLongList(Path);
    }

    public byte getByte(String Path) {
        return cfg.getByte(Path);
    }

    public byte getByte(String Path, byte Default) {
        return cfg.getByte(Path, Default);
    }

    public List<Byte> getByteList(String Path) {
        return cfg.getByteList(Path);
    }

    public float getFloat(String Path) {
        return cfg.getFloat(Path);
    }

    public float getFloat(String Path, float Default) {
        return cfg.getFloat(Path, Default);
    }

    public List<Float> getFloatList(String Path) {
        return cfg.getFloatList(Path);
    }

    public char getChar(String Path) {
        return cfg.getChar(Path);
    }

    public char getChar(String Path, char Default) {
        return cfg.getChar(Path, Default);
    }

    public List<Character> getCharList(String Path) {
        return cfg.getCharList(Path);
    }

    public short getShort(String Path) {
        return cfg.getShort(Path);
    }

    public short getShort(String Path, short Default) {
        return cfg.getShort(Path, Default);
    }

    public List<Short> getShortList(String Path) {
        return cfg.getShortList(Path);
    }

    public List<?> getList(String Path) {
        return cfg.getList(Path);
    }

    public List<?> getList(String Path, List<?> Default) {
        return cfg.getList(Path, Default);
    }
    
}
