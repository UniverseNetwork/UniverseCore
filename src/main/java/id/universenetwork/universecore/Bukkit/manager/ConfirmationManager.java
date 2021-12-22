package id.universenetwork.universecore.Bukkit.manager;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.manager.callbacks.CanSkipCallback;
import id.universenetwork.universecore.Bukkit.utils.utils;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ConfirmationManager {

    private final UniverseCore core;
    private final Map<Player, Callable> confirmMap = new HashMap<>();


    /**
     * Requests a confirmation before executing something
     *
     * @param callable the code to execute if confirmed
     * @param sender   the executor
     * @param skip     the required condition to skip the confirmation
     * @param warnings warnings to be sent to the player
     */
    public void requestConfirm(Callable callable, CommandSender sender, boolean skip, @Nullable List<String> warnings) {
        if (skip || sender instanceof ConsoleCommandSender) {
            callable.call();
            return;
        }

        Player player = (Player) sender;
        if (warnings != null && !warnings.isEmpty()) {
            warnings.forEach(player::sendMessage);
        }

        utils.sendMsg(player, utils.getPrefix() + "&7Type &e/uni confirm &7to confirm your action.");
        this.confirmMap.put(player, callable);
    }

    /**
     * @see ConfirmationManager#requestConfirm(Callable, CommandSender, boolean, List)
     */
    public void requestConfirm(Callable callable, CommandSender sender, boolean skip) {
        this.requestConfirm(callable, sender, skip, null);
    }

    /**
     * @see ConfirmationManager#requestConfirm(Callable, CommandSender, boolean, List)
     */
    public void requestConfirm(Callable callable, CommandSender sender, @Nullable List<String> warnings) {
        this.requestConfirm(callable, sender, false, warnings);
    }

    /**
     * @see ConfirmationManager#requestConfirm(Callable, CommandSender, boolean, List)
     */
    public void requestConfirm(Callable callable, CommandSender sender) {
        this.requestConfirm(callable, sender, false, null);
    }

    /**
     * @see ConfirmationManager#requestConfirm(Callable, CommandSender, boolean, List)
     */
    public void requestConfirm(Callable callable, CanSkipCallback skipCallback) {
        this.requestConfirm(callable, skipCallback.getSender(), skipCallback.isCanSkip(), skipCallback.getReason());
    }

    /**
     * Confirms the execution of pending code.
     *
     * @param p the player
     */
    public void confirm(Player p) {
        if (!this.confirmMap.containsKey(p)) {
            utils.sendMsg(p, utils.getPrefix() + "&cYou don't have any pending action!");
        } else {
            utils.sendMsg(p, utils.getPrefix() + "&aAction confirmed.");
            this.confirmMap.get(p).call();
        }
    }

    public void deleteConfirmation(Player p) {
        if (!this.confirmMap.containsKey(p)) {
            utils.sendMsg(p, utils.getPrefix() + "&cYou don't have any pending action!");
            return;
        }

        utils.sendMsg(p, utils.getPrefix() + "&cAction declined.");
        this.confirmMap.remove(p);
    }

    public interface Callable {
        void call();
    }

}
