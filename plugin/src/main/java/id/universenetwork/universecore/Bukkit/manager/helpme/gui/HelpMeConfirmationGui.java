package id.universenetwork.universecore.Bukkit.manager.helpme.gui;

import id.universenetwork.universecore.Bukkit.enums.GuiActionType;
import id.universenetwork.universecore.Bukkit.manager.GuiManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class HelpMeConfirmationGui extends GuiManager {

    public HelpMeConfirmationGui() {
        super(27, "&6Beneran mau di cancel?");
        this.setFilter(Material.BLACK_STAINED_GLASS_PANE);
        this.setItem(12, Material.GREEN_WOOL, 1, "&aYes", (String[]) null);
        this.setItem(14, Material.RED_WOOL, 1, "&cNo", (String[]) null);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof HelpMeConfirmationGui) {
            this.setActions(12, e, GuiActionType.CLOSE);
            this.setActions(14, e, event -> {
                HelpMeAcceptedGui gui = new HelpMeAcceptedGui();
                gui.openGui(e.getWhoClicked(), gui.getUuid());
            });
            for (int i = 0; i < this.getInventory().getSize(); i++) {
                if (Objects.requireNonNull(this.getInventory().getItem(i)).getType() == this.getFilterItem()) {
                    this.setActions(i, e, GuiActionType.CANCEL);
                }
            }
        }
    }

}
