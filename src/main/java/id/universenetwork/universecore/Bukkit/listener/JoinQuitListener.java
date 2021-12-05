package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.enums.ConfigEnum;
import id.universenetwork.universecore.Bukkit.manager.file.ConfigData;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (ConfigData.getInstance().getBoolean(ConfigEnum.JENABLE)) {
            if (ConfigData.getInstance().getBoolean(ConfigEnum.JCENTER)) {
                List<String> a = ConfigData.getInstance().getConfig().getStringList(ConfigEnum.JMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setJoinMessage(utils.colors(CenterMessage.CenteredMessage(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()}))));
            } else {
                List<String> a = ConfigData.getInstance().getConfig().getStringList(ConfigEnum.JMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setJoinMessage(utils.colors(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()})));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (ConfigData.getInstance().getBoolean(ConfigEnum.QENABLE)) {
            if (ConfigData.getInstance().getBoolean(ConfigEnum.QCENTER)) {
                List<String> a = ConfigData.getInstance().getConfig().getStringList(ConfigEnum.QMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setQuitMessage(utils.colors(CenterMessage.CenteredMessage(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()}))));
            } else {
                List<String> a = ConfigData.getInstance().getConfig().getStringList(ConfigEnum.QMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setQuitMessage(utils.colors(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()})));
            }
        }
    }

}
