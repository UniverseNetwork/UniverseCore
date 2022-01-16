package id.universenetwork.multiversion.v1_12_R1;

import id.universenetwork.multiversion.MultiVersion;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class v1_12_R1 extends MultiVersion {

    public v1_12_R1(Plugin plugin) {
        super(plugin);
    }

    @Override
    public List<String> getListItemByVersion(String context) {
        return Arrays.stream(Material.values()).map(Material::name).filter(s -> s.startsWith(context.toUpperCase())).collect(Collectors.toList());
    }

    @Override
    public double getMaxHealth(Player player) {
        return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    }

    @Override
    public int getPing(Player player) {
        Class<?> CPClass;
        String serverName = Bukkit.getServer().getClass().getPackage().getName(),
                serverVersion = serverName.substring(serverName.lastIndexOf(".") + 1);
        try {
            CPClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
            Object CraftPlayer = CPClass.cast(player);
            Method getHandle = CraftPlayer.getClass().getMethod("getHandle");
            Object EntityPlayer = getHandle.invoke(CraftPlayer);
            Field ping = EntityPlayer.getClass().getDeclaredField("ping");
            return ping.getInt(EntityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title != null) {
            if (!title.isEmpty()) {
                IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
                PacketPlayOutTitle t = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, bc);
                PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(t);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
            }
        }
        if (subtitle != null) {
            IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle st = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, bc);
            PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(st);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
        }
    }

    @Override
    public List<String> getParticleList(String context) {
        return Arrays.stream(EnumParticle.values()).map(EnumParticle::name).filter(s -> s.startsWith(context.toUpperCase())).collect(Collectors.toList());
    }

}
