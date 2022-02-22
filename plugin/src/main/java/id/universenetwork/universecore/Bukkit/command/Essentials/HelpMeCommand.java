package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.helpme.gui.HelpMeAcceptedGui;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class HelpMeCommand extends UNCommand {

    @Getter
    Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    @CommandMethod(value = "helpme [message]", requiredSender = Player.class)
    public void helpmeCMD(@NonNull Player sender,
                          @NonNull @Argument(value = "message") @Greedy String helpMsg) {

        this.player = sender;

        core.getHelpMeData().setHelpList(sender.getUniqueId(), helpMsg);
        setPlayer(sender);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("universenetwork.staff.helpme")) {
                Utils.sendMsg(sender, helpMsg);
                HelpMeAcceptedGui gui = new HelpMeAcceptedGui();
                gui.callback(() -> {
                    gui.openGui(player, gui.getUuid());
                }, gui.guiCanSkip(player, sender));
                gui.openGui(player, gui.getUuid());
                core.getHelpMeData().setStaffInProgress(sender.getUniqueId(), player.getUniqueId());
            }
        }

    }

    @CommandMethod(value = "helpmeguitest", requiredSender = Player.class)
    public void guitest(@NonNull Player player) {
        HelpMeAcceptedGui gui = new HelpMeAcceptedGui();
        gui.openGui(player, gui.getUuid());
    }

    @CommandMethod(value = "helpmetest [message]", requiredSender = Player.class)
    public void cmdtest(@NonNull Player player, @Argument(value = "message") @Greedy String message) {
        core.getHelpMeData().setHelpList(player.getUniqueId(), message);
        Utils.sendMsg(player, "&aSuccess!");
    }

    @CommandMethod(value = "helpmetest1", requiredSender = Player.class)
    public void cmdtest1(@NonNull Player player) {
        Utils.sendMsg(player, core.getHelpMeData().getHelpList().get(player.getUniqueId()));
    }

}
