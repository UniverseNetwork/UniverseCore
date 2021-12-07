package id.universenetwork.universecore.Bukkit.command.Essentials;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeCommand extends UNCommand {

    public GamemodeCommand() {
        super("gamemode", "universenetwork.gamemode", "/gamemode [creative|survival|adventure|spectator] <player>", null,
                2, false, "ugamemode", "gm", "ugm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("creative")) {
                    if (p.hasPermission("universenetwork.gamemode.creative")) {
                        if (p.getGameMode() == GameMode.CREATIVE) {
                            p.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.ALRDINGMPPLY))
                                    .replace("%gamemode%", String.valueOf(p.getGameMode())));
                        } else {
                            String a = MessageData.getInstance().getString(MessageEnum.GMCHANGE);
                            p.sendMessage(utils.colors(StringUtils.replaceEach(a,
                                    new String[]{"%gamemode%", "%gamemode_change%"}, new String[]{String.valueOf(p.getGameMode()), "CREATIVE"})));
                            p.setGameMode(org.bukkit.GameMode.CREATIVE);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("survival")) {
                    if (p.hasPermission("universenetwork.gamemode.survival")) {
                        if (p.getGameMode() == GameMode.SURVIVAL) {
                            p.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.ALRDINGMPPLY))
                                    .replace("%gamemode%", String.valueOf(p.getGameMode())));
                        } else {
                            String a = MessageData.getInstance().getString(MessageEnum.GMCHANGE);
                            p.sendMessage(utils.colors(StringUtils.replaceEach(a,
                                    new String[]{"%gamemode%", "%gamemode_change%"}, new String[]{String.valueOf(p.getGameMode()), "SURVIVAL"})));
                            p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("adventure")) {
                    if (p.hasPermission("universenetwork.gamemode.adventure")) {
                        if (p.getGameMode() == GameMode.ADVENTURE) {
                            p.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.ALRDINGMPPLY))
                                    .replace("%gamemode%", String.valueOf(p.getGameMode())));
                        } else {
                            String a = MessageData.getInstance().getString(MessageEnum.GMCHANGE);
                            p.sendMessage(utils.colors(StringUtils.replaceEach(a,
                                    new String[]{"%gamemode%", "%gamemode_change%"}, new String[]{String.valueOf(p.getGameMode()), "ADVENTURE"})));
                            p.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("spectator")) {
                    if (p.hasPermission("universenetwork.gamemode.spectator")) {
                        if (p.getGameMode() == GameMode.SPECTATOR) {
                            p.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.ALRDINGMPPLY))
                                    .replace("%gamemode%", String.valueOf(p.getGameMode())));
                        } else {
                            String a = MessageData.getInstance().getString(MessageEnum.GMCHANGE);
                            p.sendMessage(utils.colors(StringUtils.replaceEach(a,
                                    new String[]{"%gamemode%", "%gamemode_change%"}, new String[]{String.valueOf(p.getGameMode()), "CREATIVE"})));
                            p.setGameMode(org.bukkit.GameMode.SPECTATOR);
                        }
                    }
                } else {
                    sender.sendMessage("§cInvalid gamemode!");
                }
            } else {
                sender.sendMessage("&6Usage: &e" + getUsage());
            }
        } else if (args.length == 2) {
            Player t = Bukkit.getPlayerExact(args[1]);
            if (!(t == null)) {
                if (args[0].equalsIgnoreCase("creative")) {
                    if (sender.hasPermission("universenetwork.gamemode.creative.others")) {
                        if (t.getGameMode() == GameMode.CREATIVE) {
                            sender.sendMessage(utils.colors(StringUtils.replaceEach(MessageData.getInstance().getString(MessageEnum.ALRDINGMPTRG),
                                    new String[]{"%player%", "%gamemode%"},
                                    new String[]{t.getName(), String.valueOf(t.getGameMode())})));
                        } else {
                            sender.sendMessage(utils.colors("&7Change &6" + t.getName() + " &7gamemode from &e" + t.getGameMode() + " &7to &aCREATIVE"));
                            t.setGameMode(org.bukkit.GameMode.CREATIVE);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("survival")) {
                    if (sender.hasPermission("universenetwork.gamemode.survival.others")) {
                        if (t.getGameMode() == GameMode.SURVIVAL) {
                            sender.sendMessage(utils.colors(StringUtils.replaceEach(MessageData.getInstance().getString(MessageEnum.ALRDINGMPTRG),
                                    new String[]{"%player%", "%gamemode%"},
                                    new String[]{t.getName(), String.valueOf(t.getGameMode())})));
                        } else {
                            sender.sendMessage(utils.colors("&7Change &6" + t.getName() + " &7gamemode from &e" + t.getGameMode() + " &7to &aSURVIVAL"));
                            t.setGameMode(org.bukkit.GameMode.SURVIVAL);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("adventure")) {
                    if (sender.hasPermission("universenetwork.gamemode.adventure.others")) {
                        if (t.getGameMode() == GameMode.ADVENTURE) {
                            sender.sendMessage(utils.colors(StringUtils.replaceEach(MessageData.getInstance().getString(MessageEnum.ALRDINGMPTRG),
                                    new String[]{"%player%", "%gamemode%"},
                                    new String[]{t.getName(), String.valueOf(t.getGameMode())})));
                        } else {
                            sender.sendMessage(utils.colors("&7Change &6" + t.getName() + " &7gamemode from &e" + t.getGameMode() + " &7to &aADVENTURE"));
                            t.setGameMode(org.bukkit.GameMode.ADVENTURE);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("spectator")) {
                    if (sender.hasPermission("universenetwork.gamemode.spectator.others")) {
                        if (t.getGameMode() == GameMode.SPECTATOR) {
                            sender.sendMessage(utils.colors(StringUtils.replaceEach(MessageData.getInstance().getString(MessageEnum.ALRDINGMPTRG),
                                    new String[]{"%player%", "%gamemode%"},
                                    new String[]{t.getName(), String.valueOf(t.getGameMode())})));
                        } else {
                            sender.sendMessage(utils.colors("&7Change &6" + t.getName() + " &7gamemode from &e" + t.getGameMode() + " &7to &aSPECTATOR"));
                            t.setGameMode(org.bukkit.GameMode.SPECTATOR);
                        }
                    }
                } else {
                    sender.sendMessage("§cInvalid gamemode!");
                }
            } else {
                sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.NOPLAYER)));
            }
        } else {
            sender.sendMessage(utils.colors("&6Usage: &e" + getUsage()));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String string, String[] args) {

        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("universenetwork.gamemode.*")){
                list.addAll(Arrays.asList("creative", "survival", "adventure", "spectator"));
            } else if (sender.hasPermission("universenetwork.gamemode.creative") ||
                    sender.hasPermission("universenetwork.gamemode.creative.others")) {
                list.add("creative");
            } else if (sender.hasPermission("universenetwork.gamemode.survival") ||
                    sender.hasPermission("universenetwork.gamemode.survival.others")) {
                list.add("survival");
            } else if (sender.hasPermission("universenetwork.gamemode.adventure") ||
                    sender.hasPermission("universenetwork.gamemode.adventure.others")) {
                list.add("adventure");
            } else if (sender.hasPermission("universenetwork.gamemode.spectator") ||
                    sender.hasPermission("universenetwork.gamemode.spectator.others")) {
                list.add("spectator");
            }
        }
        return list;
    }
}
