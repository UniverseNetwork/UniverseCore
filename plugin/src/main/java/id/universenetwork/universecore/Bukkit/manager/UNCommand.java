package id.universenetwork.universecore.Bukkit.manager;

import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.ConfigEnum;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.callbacks.CanSkipCallback;
import id.universenetwork.universecore.Bukkit.manager.file.Config;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class UNCommand {

    protected UniverseCore core = UniverseCore.getInstance();

    public List<String> getAuthor() {
        return core.getDescription().getAuthors();
    }

    protected TargetsCallback getTargets(CommandSender sender, @Nullable String arg) {
        TargetsCallback callback = new TargetsCallback();
        if (sender instanceof Player) {
            if (arg == null) {
                callback.add((Player) sender);
                return callback;
            }

            switch (arg.toLowerCase()) {
                case "self": {
                    callback.add((Player) sender);
                    return callback;
                }
                case "*":
                case "@a":
                case "@all": {
                    callback.addAll(Bukkit.getOnlinePlayers());
                    return callback;
                }
            }

            Player targetName = Bukkit.getPlayer(arg);
            if (targetName == null) {
                Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.NOPLAYER));
                return callback;
            }

            callback.add(targetName);
            return callback;
        }

        if (arg == null) {
            Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.ADDSPECIFYPLAYER));
            callback.setNotify(true);
            return callback;
        }

        switch (arg.toLowerCase()) {
            case "*":
            case "@a":
            case "@all": {
                callback.addAll(Bukkit.getOnlinePlayers());
                return callback;
            }
        }

        Player targetName = Bukkit.getPlayer(arg);
        if (targetName == null) {
            Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.NOPLAYER));
            return callback;
        }

        callback.add(targetName);
        return callback;
    }

    @SuppressWarnings("deprecation")
    protected OfflineTargetsCallback getOfflineTargets(CommandSender sender, @Nullable String arg) {
        OfflineTargetsCallback callback = new OfflineTargetsCallback();
        if (sender instanceof Player) {
            if (arg == null) {
                callback.add((Player) sender);
                return callback;
            }

            switch (arg.toLowerCase()) {
                case "self": {
                    callback.add((Player) sender);
                    return callback;
                }
                case "*":
                case "@a":
                case "@all": {
                    callback.addAll(Bukkit.getOnlinePlayers());
                    return callback;
                }
            }

            OfflinePlayer targetName = Bukkit.getOfflinePlayer(arg);
            if (targetName == null) {
                Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.NOPLAYER));
                return callback;
            }

            callback.add(targetName);
            return callback;
        }

        if (arg == null) {
            Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.ADDSPECIFYPLAYER));
            callback.setNotify(true);
            return callback;
        }

        switch (arg.toLowerCase()) {
            case "*":
            case "@a":
            case "@all": {
                callback.addAll(Bukkit.getOnlinePlayers());
                return callback;
            }
        }

        OfflinePlayer targetName = Bukkit.getOfflinePlayer(arg);
        if (targetName == null) {
            Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.NOPLAYER));
            return callback;
        }

        callback.add(targetName);
        return callback;
    }

    public CanSkipCallback canSkip(String action, TargetsCallback targetsCallback, CommandSender sender) {
        if (!Config.getInstance().getBoolean(ConfigEnum.USECONFIRMATION)) {
            return new CanSkipCallback(sender, true, null);
        }

        if (targetsCallback.size() == 1) {
            Player target = targetsCallback.getTargets().stream().findFirst().orElse(null);
            if (target != null && target.equals(sender)) {
                return new CanSkipCallback(sender, true, null);
            }
        }

        if (targetsCallback.size() >= Config.cfg.getInt(ConfigEnum.MAXPLAYERCONFIRM.getPath())) {
            return new CanSkipCallback(sender, false, Collections.singletonList(
                    Utils.colors(Utils.getPrefix() + "&7Are you sure want to execute &e" + action + " &7on &a" + targetsCallback.size() + " &7players?")
            ));
        }

        boolean playerSender = sender instanceof Player;

        World world = null;
        for (Player target : targetsCallback.getTargets()) {
            if (world != null) {
                if (world != target.getWorld()) {
                    return new CanSkipCallback(sender, false, Arrays.asList(
                           Utils.colors( Utils.getPrefix() + "&7Are you sure want to execute &e" + action + " &7on &a" + targetsCallback.size() + " &7players?"),
                            Utils.colors(Utils.getPrefix() + "&7Some player are scattered across different world.")
                    ));
                }

                if (playerSender) {
                    if (((Player) sender).getWorld() == target.getWorld() && ((Player) sender).getLocation().distanceSquared(target.getLocation()) >= 250) {
                        return new CanSkipCallback(sender, false, Arrays.asList(
                                Utils.colors(Utils.getPrefix() + "&7Are you sure want to execute &e" + action + " &7on &a" + targetsCallback.size() + " &7players?"),
                                Utils.colors(Utils.getPrefix() + "&7Some player are really far from you.")
                        ));
                    }
                }
                continue;
            }
            world = target.getWorld();
        }

        return new CanSkipCallback(sender, true, null);
    }

    @Suggestions("players")
    public List<String> player(CommandContext<CommandSender> sender, String context) {
        List<String> players = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));

        players.add("*");
        players.add("@a");
        players.add("@all");
        return players.stream().filter(s -> s.startsWith(context)).collect(Collectors.toList());
    }

    @Suggestions("onePlayers")
    public List<String> onePlayer(CommandContext<CommandSender> sender, String context) {
        return Utils.getOnlinePlayers(context);
    }

    @Suggestions("toggles")
    public List<String> toggle(CommandContext<CommandSender> sender, String context) {
        return Stream.of("on", "off", "toggle").filter(s -> s.toLowerCase().startsWith(context.toLowerCase())).collect(Collectors.toList());
    }

    @Data
    protected static class TargetsCallback {
        private boolean notify = false;
        private Set<Player> targets = new HashSet<>();

        public void add(Player player) {
            this.targets.add(player);
        }

        public void addAll(Collection<? extends Player> players) {
            this.targets.addAll(players);
        }

        public int size() {
            return this.targets.size();
        }

        public boolean isEmpty() {
            return this.targets.isEmpty();
        }

        public boolean notifyIfEmpty() {
            return this.isEmpty() && !this.isNotify();
        }

        public boolean doesNotContain(Player player) {
            return !this.targets.contains(player);
        }

        public Stream<Player> stream() {
            return StreamSupport.stream(Spliterators.spliterator(targets, 0), false);
        }

        public void forEach(Consumer<? super Player> action) {
            for (Player target : targets) {
                action.accept(target);
            }
        }
    }

    @Data
    protected static class OfflineTargetsCallback {
        private boolean notify = false;
        private Set<OfflinePlayer> targets = new HashSet<>();

        public void add(OfflinePlayer player) {
            this.targets.add(player);
        }

        public void addAll(Collection<? extends OfflinePlayer> players) {
            this.targets.addAll(players);
        }

        public int size() {
            return this.targets.size();
        }

        public boolean isEmpty() {
            return this.targets.isEmpty();
        }

        public boolean notifyIfEmpty() {
            return this.isEmpty() && !this.isNotify();
        }

        public boolean doesNotContain(Player player) {
            return !this.targets.contains(player);
        }

        public Stream<OfflinePlayer> stream() {
            return StreamSupport.stream(Spliterators.spliterator(targets, 0), false);
        }

        public void forEach(Consumer<? super OfflinePlayer> action) {
            for (OfflinePlayer target : targets) {
                action.accept(target);
            }
        }
    }

}
