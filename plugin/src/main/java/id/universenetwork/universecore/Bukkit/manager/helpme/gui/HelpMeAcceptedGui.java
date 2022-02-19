package id.universenetwork.universecore.Bukkit.manager.helpme.gui;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.command.Essentials.HelpMeCommand;
import id.universenetwork.universecore.Bukkit.enums.GuiActionType;
import id.universenetwork.universecore.Bukkit.manager.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class HelpMeAcceptedGui extends GuiManager {

    public HelpMeAcceptedGui() {
        super(45, "&2HelpMe");
        this.setFilter(Material.BLACK_STAINED_GLASS_PANE);
        HelpMeCommand command = new HelpMeCommand();
        this.setItem(13, Material.PAPER, 1, "&2HelpMe", "&6Loading message... Please wait!");
        this.setItem(30, Material.GREEN_WOOL, 1, "&aAccept", "&7Accepted the help request.");
        this.setItem(32, Material.RED_WOOL, 1, "&cCancel", "&7Canceled the help request.");
    }

    @EventHandler
    public void onClickAction(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getHolder() instanceof HelpMeAcceptedGui) {
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

}
