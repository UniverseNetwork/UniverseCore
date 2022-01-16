package id.universenetwork.multiversion.v1_17_R1;

import id.universenetwork.multiversion.MultiVersion;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class v1_17_R1 extends MultiVersion {

    public v1_17_R1(Plugin plugin) {
        super(plugin);
    }

    @Override
    public List<String> getListItemByVersion(String context) {
        return Arrays.stream(Material.values()).map(Material::name).filter(s -> s.startsWith(context.toUpperCase())).collect(Collectors.toList());
    }

    @Override
    public double getMaxHealth(Player player) {
        return Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
    }

    @Override
    public int getPing(Player player) {
        return player.getPing();
    }

    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title == null ? " " : title, subtitle == null ? " " : subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public List<String> getParticleList(String context) {
        return Arrays.stream(Particle.values()).map(Particle::name).filter(s -> s.startsWith(context.toUpperCase())).collect(Collectors.toList());
    }

}
