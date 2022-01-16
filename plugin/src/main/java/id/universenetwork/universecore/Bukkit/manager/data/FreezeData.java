package id.universenetwork.universecore.Bukkit.manager.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class FreezeData {

    private static Map<UUID, Boolean> data = new HashMap<>();
    private final UUID uuid;

    public FreezeData(UUID uuid) {
        this.uuid = uuid;
    }

    public void setID(UUID key, Boolean value) {
        data.put(key, value);
    }

    public void removeID(UUID key) {
        data.remove(key);
    }

    public Boolean getID() {
        return data.get(uuid);
    }

    public Boolean getID(UUID key) {
        return data.get(key);
    }

    public Boolean hasID() {
        return data.containsKey(uuid);
    }

    public Boolean checkID(UUID uuid) {
        return data.containsKey(uuid);
    }

}
