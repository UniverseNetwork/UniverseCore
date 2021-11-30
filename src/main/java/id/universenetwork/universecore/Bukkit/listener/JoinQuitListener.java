package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.enums.ConfigEnum;
import id.universenetwork.universecore.Bukkit.manager.file.ConfigData;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (ConfigData.getInstance().getBoolean(ConfigEnum.JENABLE)) {
            if (ConfigData.getInstance().getBoolean(ConfigEnum.JCENTER)) {
                for (String a : ConfigData.getInstance().getConfig().getStringList(ConfigEnum.JMSG.getPath())) {
                    e.setJoinMessage(CenterMessage.CenteredMessage((a).replace("%player%", e.getPlayer().getName())).replace("&", "§"));
                }
            } else {
                for (String a : ConfigData.getInstance().getConfig().getStringList(ConfigEnum.JMSG.getPath())) {
                    e.setJoinMessage((a).replace("%player%", e.getPlayer().getName()).replace("&", "§"));
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (ConfigData.getInstance().getBoolean(ConfigEnum.QENABLE)) {
            if (ConfigData.getInstance().getBoolean(ConfigEnum.QCENTER)) {
                for (String a : ConfigData.getInstance().getConfig().getStringList(ConfigEnum.QMSG.getPath())) {
                    e.setQuitMessage(CenterMessage.CenteredMessage((a).replace("%player%", e.getPlayer().getName())).replace("&", "§"));
                }
            } else {
                for (String a : ConfigData.getInstance().getConfig().getStringList(ConfigEnum.QMSG.getPath())) {
                    e.setQuitMessage((a).replace("%player%", e.getPlayer().getName()).replace("&", "§"));
                }
            }
        }
    }

}
