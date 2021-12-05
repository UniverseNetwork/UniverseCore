package id.universenetwork.universecore.Bukkit.manager.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class ToggleDropData {

    private static Map<UUID, Boolean> td = new HashMap<>();
    private final UUID uuid;

    public ToggleDropData(UUID uuid) {
        this.uuid = uuid;
    }

    public void setId(Boolean id) {
        td.put(uuid, id);
    }

    public Boolean getID() {
        return td.get(uuid);
    }

    public Boolean hasID() {
        if (td.containsKey(uuid))
            return true;
        return false;
    }

    public void removeID() {
        td.remove(uuid);
    }

    public void endTask() {
        if (getID() == true)
            return;

        removeID();
    }

    public static boolean hasFakeID(UUID uuid) {
        if (td.containsKey(uuid))
            if (td.get(uuid) == true)
                return true;
        return false;
    }
}
