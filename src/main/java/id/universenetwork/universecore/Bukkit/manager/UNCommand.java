package id.universenetwork.universecore.Bukkit.manager;

import id.universenetwork.universecore.Bukkit.command.*;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static id.universenetwork.universecore.Bukkit.utils.CenterMessage.sendCentredMessage;
import static id.universenetwork.universecore.Bukkit.utils.utils.colors;

public abstract class UNCommand extends Command {

    final String description;
    final String usage;
    private String permission;
    final int argsLength;
    final boolean onlyPlayer;
    final String[] aliases;

    public UNCommand(String name, String permission, String description, String usage, int argsLength, boolean onlyPlayer, String... aliases) {
        super(name, description, usage, Arrays.asList(aliases));
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.argsLength = argsLength;
        this.onlyPlayer = onlyPlayer;
        this.aliases = aliases;

        try {
            Field commandMapField = Objects.requireNonNull(Bukkit.getServer().getClass().getDeclaredField("commandMap"));
            commandMapField.setAccessible(true);

            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(name, this);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public UNCommand(String name, String permission, String usage, String description, int argsLength, boolean onlyPlayer) {
        this(name, permission, usage, description, argsLength, onlyPlayer, null);
    }

    public UNCommand(String name, String permission, String usage, String description, int argsLength) {
        this(name, permission, usage, description, argsLength, false);
    }

    public UNCommand(String name, String permission, String usage, String description) {
        this(name, permission, usage, description, 0);
    }

    public UNCommand(String name, String permission, String usage) {
        this(name, permission, usage, null);
    }

    public UNCommand(String name, String permission) {
        this(name, permission, null);
    }

    public void execute(Player player, String[] args) {}
    public void execute(CommandSender sender, String[] args) {}
    public abstract List<String> TabCompleter(CommandSender sender, String s, String[] args);

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, @Nonnull String[] args) {
        if (!sender.hasPermission(Objects.requireNonNull(getPermission()))) {
            sendCentredMessage(sender,"&b");
            sendCentredMessage(sender, MessageData.getInstance().getString(MessageEnum.NOPERM));
            sendCentredMessage(sender,"&b");
            return true;
        }

        if (args.length > argsLength) {
            sender.sendMessage(colors(MessageData.getInstance().getString(MessageEnum.TOOMANYARG)));
            sender.sendMessage(colors("&6Usage: &e" + usage));
            return true;
        }

        if (onlyPlayer) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(colors(MessageData.getInstance().getString(MessageEnum.ONLYPLAYER)));
                return true;
            }
            execute((Player) sender, args);
            return true;
        }

        execute(sender,args);
        return true;
    }

    @Override
    public List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args) {


        if (args.length > argsLength) {
            return Collections.emptyList();
        }

        if (!(sender.hasPermission(Objects.requireNonNull(getPermission())))) {
            return Collections.emptyList();
        }

        return TabCompleter(sender, alias, args);
    }

    @Override
    public List<String> getAliases() {
        if (aliases == null) return Collections.emptyList();
        List<String> alias = new java.util.ArrayList<>();
        Collections.addAll(alias, aliases);
        return alias;
    }

    @Override
    public String getDescription() {
        if (description == null) return "An UniverseCore command";
        return description;
    }

    @Override
    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public static void register() {
        new GamemodeCommand();
        // new GiveCommand();
        new KaboomCommand();
        new MainCommand();
        new PingCommand();
        new SantetCommand();
        new ToggleDropCommand();
        new WhereIsCommand();
    }
}
