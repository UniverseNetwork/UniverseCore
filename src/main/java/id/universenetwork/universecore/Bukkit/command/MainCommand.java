package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.ConfigData;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static id.universenetwork.universecore.Bukkit.utils.CenterMessage.sendCentredMessage;

public class MainCommand extends UNCommand {

    public MainCommand() {
        super("universecore", null, "/universecore", null, 1, false,
                "uni", "universe", "unc");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            sendCentredMessage(s,"&a");
            sendCentredMessage(s,"&bUniverse&eCore");
            sendCentredMessage(s,"&aMade by &erajaopak");
            sendCentredMessage(s,"&a");
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (s.hasPermission("universenetwork.reload")) {
                    long millis = System.currentTimeMillis();
                    try {
                        ConfigData.getInstance().reload();
                        MessageData.getInstance().reload();
                        Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("UniverseCore")).onDisable();
                        Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("UniverseCore")).onEnable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendCentredMessage(s,"&a");
                    sendCentredMessage(s,MessageData.getInstance().getString(MessageEnum.RELOAD) + " (Took " + (System.currentTimeMillis() - millis) + "ms)");
                    sendCentredMessage(s,"&a");
                }
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("universenetwork.reload")) {
                return new ArrayList<>(Collections.singletonList("reload"));
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
