package id.universenetwork.universecore.Bukkit;

import id.universenetwork.universecore.Bukkit.listener.JoinQuitListener;
import id.universenetwork.universecore.Bukkit.listener.ToggleDropListener;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.ConfigData;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class UniverseCore extends JavaPlugin {

    private static UniverseCore instance;

    public static UniverseCore getInstance() {
        return UniverseCore.instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        long start = System.currentTimeMillis();
        UniverseCore.instance = this;
        this.register();
        System.out.println("\nUniverseCore Has been enabled!\n" +
                "source: https://github.com/UniverseNetwork/UniverseCore\n" +
                "website: https://universenetwork.id/");
        this.onEnableMessage();
        System.out.println("Took " + (System.currentTimeMillis() - start) + "ms to enable!");
        System.out.println();
        System.out.println();
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

        UNCommand.register();
        utils.registerListener(new JoinQuitListener(),
                new ToggleDropListener());
    }

    public void onEnableMessage() {
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("§8╔══════════════════════════════════════════════════════════════════╗");
        Bukkit.getLogger().info("§8║                                                                  ║");
        Bukkit.getLogger().info("§8║                        §bUniverse§eNetwork                           §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                         §aWelcome back!                            §8║");
        Bukkit.getLogger().info("§8║                     §aPlugin has been enabled                      §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7Server IP§8: §6play.universenetwork.id                §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                          §7Version: §b" + this.getDescription().getVersion() + "                            §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                      §7Author: " + this.getDescription().getAuthors() + "                          §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7https://github.com/UniverseNetwork                §8║");
        Bukkit.getLogger().info("§8╚══════════════════════════════════════════════════════════════════╝");
        Bukkit.getLogger().info("");
    }

    public void onDisableMessage() {
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("§8╔══════════════════════════════════════════════════════════════════╗");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                        §bUniverse§eNetwork                           §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                           §cGoodbye!                               §8║");
        Bukkit.getLogger().info("§8║                     §cDisabling the plugin....                     §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7Server IP§8: §6play.universenetwork.id                §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                          §7Version: §b" + this.getDescription().getVersion() + "                            §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                      §7Author: " + this.getDescription().getAuthors() + "                          §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7https://github.com/UniverseNetwork                §8║");
        Bukkit.getLogger().info("§8╚══════════════════════════════════════════════════════════════════╝");
        Bukkit.getLogger().info("");
    }

}
