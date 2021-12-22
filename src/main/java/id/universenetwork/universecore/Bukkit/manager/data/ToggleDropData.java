package id.universenetwork.universecore.Bukkit.manager.data;

import lombok.Data;
import lombok.experimental.UtilityClass;
import org.bukkit.Utility;
import org.bukkit.entity.Player;

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

    public void setId(UUID uuid, Boolean id) {
        td.put(uuid, id);
    }

    public Boolean getID(UUID uuid) {
        return td.get(uuid);
    }

    public Boolean hasID() {
        return td.containsKey(uuid);
    }

    public Boolean checkID(UUID uuid) {
        return td.containsKey(uuid);
    }

    public void removeID(UUID uuid) {
        td.remove(uuid);
    }
}
