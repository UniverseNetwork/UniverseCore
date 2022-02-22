package id.universenetwork.universecore.Bukkit.manager.helpme.gui;

import id.universenetwork.universecore.Bukkit.enums.GuiActionType;
import id.universenetwork.universecore.Bukkit.manager.GuiManager;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Objects;

public class HelpMeAcceptedGui extends GuiManager {

    private Player player;

    private final HashMap<Player, Call> callHashMap = new HashMap<>();

    public HelpMeAcceptedGui() {
        super(45, "&2HelpMe");
        this.setFilter(Material.BLACK_STAINED_GLASS_PANE);
        this.setItem(13, Material.PAPER, 1,
                "&2HelpMe of Player &7- &b" + player.getName(),
                "&6Message&7: &a" + core.getHelpMeData().getHelpList().get(core.getHelpMeData().getStaffInProgress().get(player.getUniqueId())));
        this.setItem(13, Material.PAPER, 1, "&2HelpMe", "&6Loading message... Please wait!");
        this.setItem(30, Material.GREEN_WOOL, 1, "&aAccept", "&7Accepted the help request.");
        this.setItem(32, Material.RED_WOOL, 1, "&cCancel", "&7Canceled the help request.");
    }

    @EventHandler
    public void onClickAction(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getHolder() instanceof HelpMeAcceptedGui) {
            this.setActions(30, e, event -> {
                this.confirm(p);
                this.closeGui(event.getWhoClicked());
            });
            this.setActions(30, e, GuiActionType.CLOSE);
            this.setActions(13, e, GuiActionType.CANCEL);
            this.setActions(32, e, (event -> {
                HelpMeConfirmationGui gui = new HelpMeConfirmationGui();
                gui.openGui(event.getWhoClicked(), gui.getUuid());
                event.setCancelled(true);
            }));
            for (int i = 0; i < this.getInventory().getSize(); i++) {
                if (Objects.requireNonNull(this.getInventory().getItem(i)).getType() == this.getFilterItem()) {
                    this.setActions(i, e, GuiActionType.CANCEL);
                }
            }
        }
    }

    public void setPlayer(HumanEntity player) {
        this.player = (Player) player;
    }

    public Player getPlayer() {
        return this.player;
    }

    // make callback
    public void callback(Call call, Player staff, Player playerHelp, boolean canSkip) {
        if (canSkip) {
            call.callback();
        }

        Utils.sendMsg(staff, "&bNew HelpMe request from &7" + playerHelp.getName());
        this.setPlayer(staff);
        this.openGui(staff, this.getUuid());
        this.callHashMap.put(staff, call);
    }

    public void callback(Call call, GuiCanSkip guiCanSkip) {
        this.callback(call, guiCanSkip.staff, guiCanSkip.playerHelp, guiCanSkip.canSkip);
    }

    public void confirm(Player player) {
        if (!this.callHashMap.containsKey(player)) {
            Utils.sendMsg(player, "&4Failed, &cYou don't have any help request.");
        } else {
            this.callHashMap.get(player).callback();
            this.callHashMap.remove(player);
        }
    }

    public GuiCanSkip guiCanSkip(Player staff, Player playerHelp) {
        return new GuiCanSkip(staff, playerHelp,false);
    }

    @Data
    protected static class GuiCanSkip {
        private final Player staff;
        private final Player playerHelp;
        private final boolean canSkip;
    }

    public interface Call {
        void callback();
    }

}
