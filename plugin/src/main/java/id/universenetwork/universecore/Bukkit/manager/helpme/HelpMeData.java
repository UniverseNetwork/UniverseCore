package id.universenetwork.universecore.Bukkit.manager.helpme;

import id.universenetwork.universecore.Bukkit.UniverseCore;
import lombok.Data;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Data
public class HelpMeData {

    protected UniverseCore core;

    private HashMap<UUID, String> helpList;
    private HashMap<UUID, UUID> staffInProgress;
    private HashMap<UUID, Player> staffOnline;

    public HelpMeData(UniverseCore core) {
        this.core = core;
        this.helpList = new HashMap<>();
        this.staffInProgress = new HashMap<>();
        this.staffOnline = new HashMap<>();
    }

    public void setHelpList(UUID uuid, String message) {
        this.helpList.put(uuid, message);
    }

    public void setStaffInProgress(UUID playerUUID, UUID staffUUID) {
        this.staffInProgress.put(playerUUID, staffUUID);
    }

    public void setStaffOnline(UUID uuid, Player player) {
        this.staffOnline.put(uuid, player);
    }

    public void removeHelpList(UUID uuid) {
        this.helpList.remove(uuid);
    }

    public void removeStaffInProgress(UUID uuid) {
        this.staffInProgress.remove(uuid);
    }

    public void removeOnlineStaff(UUID uuid) {
        this.staffOnline.remove(uuid);
    }

    public boolean isInHelpList(UUID uuid) {
        return this.helpList.containsKey(uuid);
    }

    public boolean isInStaffInProgress(UUID uuid) {
        return this.staffInProgress.containsKey(uuid);
    }

    public boolean isStaffOnline(UUID uuid) {
        return this.staffOnline.containsKey(uuid);
    }
}
