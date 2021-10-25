package id.universenetwork.universecore.manager;

import id.universenetwork.universecore.enums.Message;
import id.universenetwork.universecore.manager.file.MessageData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static id.universenetwork.universecore.utils.CenterMessage.sendCentredMessage;
import static id.universenetwork.universecore.utils.utils.colors;

public abstract class UNCommand implements CommandExecutor, TabCompleter {

    private final CommandInfo commandInfo;

    public UNCommand() {
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "Commands must be have command annotations");
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    public void execute(Player player, String[] args) {}
    public void execute(CommandSender sender, String[] args) {}
    public abstract List<String> TabCompleter(CommandSender sender, Command command, String s, String[] args);


    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (!commandInfo.permission().isEmpty()) {
            if (!sender.hasPermission(commandInfo.permission())) {
                sendCentredMessage(sender,"&b");
                sendCentredMessage(sender, MessageData.getInstance().getString(Message.NOPERM));
                sendCentredMessage(sender,"&b");
                return true;
            }
        }

        int arg = commandInfo.argsLength();

        if (args.length > arg) {
            sender.sendMessage(colors(MessageData.getInstance().getString(Message.TOOMANYARG)));
            sender.sendMessage(colors("&6Usage: &e" + commandInfo.usage()));
            return true;
        }

        if (commandInfo.onlyPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(colors(MessageData.getInstance().getString(Message.ONLYPLAYER)));
                return true;
            }
            execute((Player) sender, args);
            return true;
        }

        execute(sender,args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {

        int argsLength = commandInfo.argsLength();

        if (args.length > argsLength) {
            return Collections.emptyList();
        }

        TabCompleter(sender, command, alias, args);
        return null;
    }
}
