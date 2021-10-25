package id.universenetwork.universecore;

import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.ConfigData;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class UniverseCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.register();
        Bukkit.getLogger().info("\nUniverseCore Has been enabled!\n" +
                "source: https://github.com/UniverseNetwork/UniverseCore\n" +
                "website: https://universenetwork.id/");
        this.onEnableMessage();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveAllConfig();
        this.onDisableMessage();
    }

    public void saveAllConfig() {
        ConfigData.getInstance().saveConfig();
        MessageData.getInstance().saveConfig();
    }

    public void register() {
        ConfigData.cfg.saveDefaultConfig();
        MessageData.message.saveDefaultConfig();

        String packageName = getClass().getPackage().getName();

        for (Class<? extends UNCommand> clazz : new Reflections(packageName + ".command").getSubTypesOf(UNCommand.class)) {
            try {
                UNCommand unCommand = clazz.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(getCommand(unCommand.getCommandInfo().name())).setExecutor(unCommand);
                Objects.requireNonNull(getCommand(unCommand.getCommandInfo().name())).setTabCompleter(unCommand);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        for (Class<? extends UNCommand> clazz : new Reflections(packageName + ".listener").getSubTypesOf(UNCommand.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener,this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void onEnableMessage() {
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("§8╔══════════════════════════════════════════════════════════════════╗");
        Bukkit.getLogger().info("§8║                                                                  ║");
        Bukkit.getLogger().info("§8║                        §bUniverse§eNetwork                     §8║");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                         §aWelcome back!                        §8║");
        Bukkit.getLogger().info("§8║                     §aPlugin has been enabled                  §8║");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                §7Server IP§8: §6play.universenetwork.id        §8║");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                §7https://github.com/UniverseNetwork            §8║");
        Bukkit.getLogger().info("§8╚══════════════════════════════════════════════════════════════════╝");
        Bukkit.getLogger().info("");
    }

    public void onDisableMessage() {
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("§8╔══════════════════════════════════════════════════════════════════╗");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                        §bUniverse§eNetwork                     §8║");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                           §cGoodbye!                           §8║");
        Bukkit.getLogger().info("§8║                     §cDisabling the plugin....                 §8║");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                §7Server IP§8: §6play.universenetwork.id        §8║");
        Bukkit.getLogger().info("§8║                                                                §8║");
        Bukkit.getLogger().info("§8║                §7https://github.com/UniverseNetwork            §8║");
        Bukkit.getLogger().info("§8╚══════════════════════════════════════════════════════════════════╝");
        Bukkit.getLogger().info("");
    }

}
