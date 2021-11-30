package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GamemodeCommand extends UNCommand {

    public GamemodeCommand() {
        super("gamemode", "universenetwork.gamemode", "/gamemode <creative|survival|adventure|spectator>", null,
                2, false, "ugamemode", "gm", "ugm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("creative")) {
                    p.setGameMode(org.bukkit.GameMode.CREATIVE);
                } else if (args[0].equalsIgnoreCase("survival")) {
                    p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                } else if (args[0].equalsIgnoreCase("adventure")) {
                    p.setGameMode(org.bukkit.GameMode.ADVENTURE);
                } else if (args[0].equalsIgnoreCase("spectator")) {
                    p.setGameMode(org.bukkit.GameMode.SPECTATOR);
                } else {
                    sender.sendMessage("§cInvalid gamemode!");
                }
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        return null;
    }
}
