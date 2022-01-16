package id.universenetwork.multiversion;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Getter
public abstract class MultiVersion {

    private final Plugin plugin;

    public MultiVersion(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract int getPing(Player player);

    public abstract double getMaxHealth(Player player);

    public abstract void sendTitle(Player player, String Title, String Subtitle, int fadeIn, int stay, int fadeOut);

    public abstract List<String> getListItemByVersion(String context);

    public abstract List<String> getParticleList(String context);

}
