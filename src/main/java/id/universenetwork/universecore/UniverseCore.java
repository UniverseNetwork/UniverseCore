package id.universenetwork.universecore;

import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.ConfigData;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class UniverseCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigData.cfg.saveDefaultConfig();
        MessageData.message.saveDefaultConfig();
        this.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void register() {
        String packageName = getClass().getPackage().getName();
        for (Class<? extends UNCommand> clazz : new Reflections(packageName + ".command").getSubTypesOf(UNCommand.class)) {
            try {
                UNCommand unCommand = clazz.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(getCommand(unCommand.getCommandInfo().name())).setExecutor(unCommand);
                Objects.requireNonNull(getCommand(unCommand.getCommandInfo().name())).setTabCompleter(unCommand);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
