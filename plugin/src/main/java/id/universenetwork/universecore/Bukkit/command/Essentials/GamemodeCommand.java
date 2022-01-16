package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.Flag;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamemodeCommand extends UNCommand {

    @CommandMethod("gamemode|gm|ugm|ugamemode <gamemode> [target]")
    public void gamemode(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "gamemode", suggestions = "gamemodes") String mode,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
            final @Flag(value = "silent", aliases = "s") Boolean silent) {

        if (!Utils.checkPermission(sender, "gamemode")) {
            return;
        }

        GameMode gameMode = this.getGamemode(mode);

        if (gameMode == null) {
            Utils.sendMsg(sender, Utils.getPrefix() + "&cInvalid GameMode!");
        }

        if (!Utils.checkPermission(sender, "gamemode." + gameMode)) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "gamemode" + gameMode, true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setGameMode(Objects.requireNonNull(gameMode));
                if (silent == null || !silent)
                    Utils.sendMsg(player, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGE),
                        new String[]{"%gamemode%"}, new String[]{String.valueOf(player.getGameMode())}));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{Objects.requireNonNull(gameMode).toString(), player.getName()})));
                } else {
                    Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{Objects.requireNonNull(gameMode).toString(), String.valueOf(targets.size())}));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                        new String[]{"%gamemode%", "%player%"}, new String[]{Objects.requireNonNull(gameMode).toString(), player.getName()})));
            }
        }, this.canSkip("gamemode change", targets, sender));
    }

    @CommandMethod("gmc|ugmc [target]")
    public void gamemodeCreative(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
            final @Flag(value = "silent", aliases = "s") Boolean silent
    ) {

        if (!Utils.checkPermission(sender, "gamemode.creative")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "gamemode.creative", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setGameMode(GameMode.CREATIVE);
                if (silent == null || !silent)
                    Utils.sendMsg(player, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGE),
                        new String[]{"%gamemode%"}, new String[]{String.valueOf(player.getGameMode())}));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
                } else {
                    Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{"CREATIVE", String.valueOf(targets.size())}));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                        new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
            }
        }, this.canSkip("gamemode change", targets, sender));
    }

    @CommandMethod("gms|ugms [target]")
    public void gamemodeSurvival(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
            final @Flag(value = "silent", aliases = "s") Boolean silent
    ) {

        if (!Utils.checkPermission(sender, "gamemode.survival")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "gamemode.survival", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setGameMode(GameMode.SURVIVAL);
                if (silent == null || !silent)
                    Utils.sendMsg(player, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGE),
                        new String[]{"%gamemode%"}, new String[]{String.valueOf(player.getGameMode())}));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
                } else {
                    Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{"SURVIVAL", String.valueOf(targets.size())}));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                        new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
            }
        }, this.canSkip("gamemode change", targets, sender));
    }

    @CommandMethod("gma|ugma [target]")
    public void gamemodeAdventure(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
            final @Flag(value = "silent", aliases = "s") Boolean silent
    ) {

        if (!Utils.checkPermission(sender, "gamemode.adventure")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "gamemode.adventure", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setGameMode(GameMode.ADVENTURE);
                if (silent == null || !silent)
                    Utils.sendMsg(player, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGE),
                        new String[]{"%gamemode%"}, new String[]{String.valueOf(player.getGameMode())}));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
                } else {
                    Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{"ADVENTURE", String.valueOf(targets.size())}));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                        new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
            }
        }, this.canSkip("gamemode change", targets, sender));
    }

    @CommandMethod("gmsp|ugmsp [target]")
    public void gamemodeSpectator(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName,
            final @Flag(value = "silent", aliases = "s") Boolean silent
    ) {

        if (!Utils.checkPermission(sender, "gamemode.spectator")) {
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        boolean others = targets.size() > 1 || (sender instanceof Player && targets.doesNotContain((Player) sender));
        if (others && !Utils.checkPermission(sender, "gamemode.spectator", true)) {
            return;
        }

        core.getConfirmationManager().requestConfirm(() -> {
            targets.stream().forEach(player -> {
                player.setGameMode(GameMode.SPECTATOR);
                if (silent == null || !silent)
                    Utils.sendMsg(player, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGE),
                        new String[]{"%gamemode%"}, new String[]{String.valueOf(player.getGameMode())}));
            });

            if (others) {
                if (targets.size() == 1) {
                    targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
                } else {
                    Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                            new String[]{"%gamemode%", "%player%"}, new String[]{"SPECTATOR", String.valueOf(targets.size())}));
                }
            } else if (!(sender instanceof Player) || targets.doesNotContain((Player) sender)) {
                targets.stream().findFirst().ifPresent(player -> Utils.sendMsg(sender, StringUtils.replaceEach(Utils.getMsgString(MessageEnum.GMCHANGEOTHERS),
                        new String[]{"%gamemode%", "%player%"}, new String[]{player.getGameMode().toString(), player.getName()})));
            }
        }, this.canSkip("gamemode change", targets, sender));
    }

    @Suggestions("gamemodes")
    public List<String> TabCompleter(CommandContext<CommandSender> sender, String context) {
        return Stream.of("creative", "ctv", "c", "1",
                "survival", "sv", "s", "2",
                "adventure", "adv", "a", "3",
                "spectator", "spec", "sp", "4").filter(s -> s.toLowerCase().startsWith(context.toLowerCase())).collect(Collectors.toList());
    }

    private GameMode getGamemode(String mode) {
        switch (mode.toLowerCase()) {
            case "creative":
            case "ctv":
            case "c":
            case "1": {
                return GameMode.CREATIVE;
            }

            case "survival":
            case "sv":
            case "s":
            case "2": {
                return GameMode.SURVIVAL;
            }

            case "adventure":
            case "adv":
            case "a":
            case "3": {
                return GameMode.ADVENTURE;
            }

            case "spectator":
            case "spec":
            case "sp":
            case "4": {
                return GameMode.SPECTATOR;
            }

        }
        return null;
    }
}
