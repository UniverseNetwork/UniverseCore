package id.universenetwork.universecore.Bungee;

import id.universenetwork.universecore.Bungee.manager.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.Bukkit;

public class UniverseCore extends Plugin {

    private static UniverseCore Instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(false);
        onEnableMessage();
    }

    @Override
    public void onDisable() {
        onDisableMessage();
    }

     public static UniverseCore getInstance() {
        return Instance;
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
