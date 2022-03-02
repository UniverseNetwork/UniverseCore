package id.universenetwork.universecore.Bukkit.manager;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.enums.GuiActionType;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GuiManager implements Listener, InventoryHolder {

    private final Map<UUID, GuiManager> guiByID;
    private final Map<UUID, UUID> openedGuiByPlayers;
    private final Map<Integer, GuiActionType> actions;

    private final UUID uuid;
    private final Inventory inventory;

    private Material filterItem;

    protected UniverseCore core = UniverseCore.getInstance();

    public GuiManager(int invSize, String name) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(this, invSize, Utils.colors(name));
        actions = new HashMap<>();
        openedGuiByPlayers = new HashMap<>();
        guiByID = new HashMap<>();
        guiByID.put(getUuid(), this);
    }

    public GuiManager() {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(this, 9, "§c§lError page");
        actions = new HashMap<>();
        openedGuiByPlayers = new HashMap<>();
        guiByID = new HashMap<>();
        guiByID.put(getUuid(), this);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void openGui(@NotNull HumanEntity player, UUID gui) {
        player.openInventory(inventory);
        openedGuiByPlayers.put(player.getUniqueId(), gui);
    }

    public void openGui(@NotNull Player player, UUID gui) {
        player.openInventory(inventory);
        openedGuiByPlayers.put(player.getUniqueId(), gui);
    }

    public void closeGui(@NotNull HumanEntity player) {
        player.closeInventory();
        openedGuiByPlayers.remove(player.getUniqueId());
    }

    public List<HumanEntity> getOpenedGuiPlayer() {
        return new ArrayList<>(inventory.getViewers());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return (Player) inventory.getViewers().get(0);
    }

    public Material getFilterItem() {
        return filterItem;
    }

    public Map<UUID, GuiManager> getGuiByID() {
        return guiByID;
    }

    public Map<Integer, GuiActionType> getActions() {
        return actions;
    }

    public Map<UUID, UUID> getOpenedGui() {
        return openedGuiByPlayers;
    }

    public boolean isOpenedGui(Player player) {
        return openedGuiByPlayers.containsKey(player.getUniqueId());
    }

    public ItemStack checkSlot(int slot) {
        return inventory.getItem(slot);
    }

    public void setItem(int Slot, Material item, int amount, String name, @Nullable String... lore) {
        ItemStack itemStack = new ItemStack(item, amount);
        ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta);
        meta.setDisplayName(Utils.colors(name));
        if (lore != null) {
            meta.setLore(Utils.colorsArray(Arrays.asList(lore)));
        }
        itemStack.setItemMeta(meta);
        inventory.setItem(Slot, itemStack);
    }

    public void setFilter(Material material) {
        this.filterItem = material;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || Objects.requireNonNull(inventory.getItem(i)).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(material, 1));
            }
        }
    }

    public void setActions(int slot, @NotNull InventoryClickEvent event, @NotNull GuiActionType actionType) {
        this.actions.put(slot, actionType);
        if (actionType.equals(GuiActionType.CANCEL)) {
            if (event.getSlot() == slot) event.setCancelled(true);
        } else if (actionType.equals(GuiActionType.CLOSE)) {
            if (event.getSlot() == slot) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(UniverseCore.getInstance(), () -> closeGui(event.getWhoClicked()), 1L);
            }
        } else {
            event.setCancelled(true);
        }
    }

    public void setActions(int slot, @NotNull InventoryClickEvent event, CustomAction action) {
        this.actions.put(slot, GuiActionType.CUSTOM);
        if (event.getSlot() == slot) {
            action.onClick(event);
        }
    }

    public interface CustomAction {
        void onClick(InventoryClickEvent event);
    }

    public Inventory clone() {
        try {
            Inventory inv = (Inventory) super.clone();
            for (int i = 0; i < inventory.getSize(); i++) {
                inv.setItem(i, inventory.getItem(i).clone());
            }
            return inv;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

}
