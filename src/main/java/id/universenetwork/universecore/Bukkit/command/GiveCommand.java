package id.universenetwork.universecore.Bukkit.command;

import com.google.common.collect.ImmutableList;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GiveCommand extends UNCommand {

    private static final List<String> materials;
    static {
        ArrayList<String> materialList = new ArrayList<String>();
        for (Material material : Material.values()) {
            materialList.add(material.name());
        }
        Collections.sort(materialList);
        materials = ImmutableList.copyOf(materialList);
    }

    public GiveCommand() {
        super("give","universenetwork.give",null,
                "/give <player> [item] <amount>",3,false,"ugive");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(utils.colors("&6Usage: &e" + getUsage()));
        } else if (args.length == 1) {
            sender.sendMessage(utils.colors("&6Usage: &e" + getUsage()));
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);

            Material material = Material.matchMaterial(args[1]);

            if (args[0].equalsIgnoreCase("*")) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!(all == null)) {
                        if (material == null) {
                            sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.INVALID_ITEM)));
                        } else {
                            all.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(args[1].toUpperCase())), 1));
                        }
                    } else {
                        if (args[0].equalsIgnoreCase("*")) {
                            return;
                        } else {
                            sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.NOPLAYER)));
                        }
                    }
                }
            }

            if (!(target == null)) {
                if (args[0].equalsIgnoreCase("*")) {
                    return;
                }
                if (material == null) {
                    sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.INVALID_ITEM)));
                } else {
                    target.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(args[1].toUpperCase())), 1));
                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {
                    return;
                } else {
                    sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.NOPLAYER)));
                }
            }
        } else if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[0]);

            Material material = Material.matchMaterial(args[1]);

            if (args[0].equalsIgnoreCase("*")) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!(all == null)) {
                        if (material == null) {
                            sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.INVALID_ITEM)));
                        } else {
                            all.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(args[1].toUpperCase())), Integer.parseInt(args[2])));
                        }
                    } else {
                        if (args[0].equalsIgnoreCase("*")) {
                            return;
                        } else {
                            sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.NOPLAYER)));
                        }
                    }
                }
            }

            if (!(target == null)) {
                if (material == null) {
                    sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.INVALID_ITEM)));
                } else {
                    target.getInventory().addItem(new ItemStack(Objects.requireNonNull(Material.getMaterial(args[1].toUpperCase())), Integer.parseInt(args[2])));
                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {
                    return;
                } else {
                    sender.sendMessage(utils.colors(MessageData.getInstance().getString(MessageEnum.NOPLAYER)));
                }
            }
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> players = utils.getOnlinePlayers(args[0]);
            players.add("*");
            return players;
        } else if (args.length == 2) {
            List<String> items = Arrays.stream(Material.values()).map(Material::name).filter(s1 -> s1.startsWith(args[1].toUpperCase())).collect(java.util.stream.Collectors.toList());
            return items;
        }
        // bukkit Item tab complete
        /*else if (args.length == 2) {
            final String arg = args[1];
            final List<String> materials = GiveCommand.materials;
            List<String> completion = new ArrayList<String>();

            final int size = materials.size();
            int i = Collections.binarySearch(materials, arg, String.CASE_INSENSITIVE_ORDER);

            if (i < 0) {
                // Insertion (start) index
                i = -1 - i;
            }

            for ( ; i < size; i++) {
                String material = materials.get(i);
                if (StringUtil.startsWithIgnoreCase(material, arg)) {
                    completion.add(material);
                } else {
                    break;
                }
            }
            return completion;
        }*/ else if (args.length == 3) {
            return new ArrayList<>(Arrays.asList("1", "8", "16", "32", "64"));
        }

        return null;
    }
}
