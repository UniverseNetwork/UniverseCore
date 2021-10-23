package id.universenetwork.universecore.command;

import id.universenetwork.universecore.manager.CommandInfo;
import id.universenetwork.universecore.manager.UNCommand;
import id.universenetwork.universecore.manager.file.ConfigData;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static id.universenetwork.universecore.utils.CenterMessage.sendCentredMessage;

@CommandInfo(name = "universe", argsLength = 1, onlyPlayer = false, usage = "/universe")
public class MainCommand extends UNCommand {

    @Override
    public void execute(CommandSender s, String[] args) {
        sendCentredMessage(s,"&a");
        sendCentredMessage(s,"&bUniverse&eCore");
        sendCentredMessage(s,"&aMade by &erajaopak");
        sendCentredMessage(s,"&a");

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (s.hasPermission("universenetwork.reload")) {
                    ConfigData.getInstance().reload();
                    MessageData.getInstance().reload();
                    sendCentredMessage(s,"&a");
                    sendCentredMessage(s,"&aSuccessfully reloading plugin!");
                    sendCentredMessage(s,"&a");
                }
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("universenetwork.reload")) {
                List<String> arg1 = new ArrayList<>(Collections.singletonList("reload"));
                return arg1;
            }
        }
        return Collections.emptyList();
    }
}
