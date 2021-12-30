package id.universenetwork.universecore.Bukkit.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.Config;
import id.universenetwork.universecore.Bukkit.manager.file.MessageFile;
import id.universenetwork.universecore.Bukkit.manager.file.SuggestionBlocker;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static id.universenetwork.universecore.Bukkit.utils.CenterMessage.sendCentredMessage;

public class MainCommand extends UNCommand {

    @CommandMethod("universecore|universe|uni|un|unc help [query]")
    @CommandDescription("Help menu")
    private void commandHelp(
            final @NonNull CommandSender sender,
            final @Argument(value = "query", suggestions = "commandList") @Greedy String query
    ) {
        core.getMinecraftHelp().queryCommands(query == null ? "" : query, sender);
    }

    @CommandMethod("universecore|universe|uni|un|unc [value]")
    public void commandUniverse(
            final @NonNull CommandSender sender,
            final @Nullable @Argument(value = "value", suggestions = "help") String value) {

        if (value == null) {
            sendCentredMessage(sender, "&a");
            sendCentredMessage(sender, "&bUniverse&eCore");
            sendCentredMessage(sender, "&aMade by &erajaopak");
            sendCentredMessage(sender, "&a");
        } else {
            switch (value) {
                case "reload": {
                    if (!utils.checkPermission(sender, "reload")) return;
                    long millis = System.currentTimeMillis();
                    try {
                        /*core.saveAllConfig();*/
                        Config.getInstance().reload();
                        MessageFile.getInstance().reload();
                        SuggestionBlocker.getInstance().reload();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendCentredMessage(sender, "&a");
                    sendCentredMessage(sender, MessageFile.getInstance().getString(MessageEnum.RELOAD) + " &7(Took &e" + (System.currentTimeMillis() - millis) + "ms&7)");
                    sendCentredMessage(sender, "&a");
                    break;
                }
                case "info": {
                    if (!utils.checkPermission(sender, "info")) return;

                    sendCentredMessage(sender, "&a");
                    sendCentredMessage(sender, "&bUniverse&eCore");
                    sendCentredMessage(sender, "&aMade by &e" + getAuthor());
                    sendCentredMessage(sender, "&a");
                    break;
                }
            }
        }
    }

    @CommandMethod("universecore|universe|uni|un|unc confirm|yes|accept")
    public void commandConfirm(final @NonNull Player player) {
        core.getConfirmationManager().confirm(player);
    }

    @CommandMethod("universecore|universe|uni|un|unc cancel|no|deny")
    public void commandDeclined(final @NonNull Player sender) {
        core.getConfirmationManager().deleteConfirmation(sender);
    }

    @Suggestions("help")
    public List<String> help(CommandContext<CommandSender> sender, String context) {
        return Stream.of("reload", "help", "info")
                .filter(s -> s.toLowerCase().startsWith(context.toLowerCase())).collect(Collectors.toList());
    }

    @Suggestions("commandList")
    public List<String> commandList(CommandContext<CommandSender> sender, String context) {
        return core.getMinecraftHelp().getCommandManager().suggest(sender.getSender(), context.toLowerCase());
    }
}
