package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.enums.ConfigEnum;
import id.universenetwork.universecore.Bukkit.manager.file.Config;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Config.getInstance().getBoolean(ConfigEnum.JENABLE)) {
            if (Config.getInstance().getBoolean(ConfigEnum.JCENTER)) {
                List<String> a = Config.getInstance().getConfig().getStringList(ConfigEnum.JMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setJoinMessage(Utils.colors(CenterMessage.CenteredMessage(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()}))));
            } else {
                List<String> a = Config.getInstance().getConfig().getStringList(ConfigEnum.JMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setJoinMessage(Utils.colors(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()})));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (Config.getInstance().getBoolean(ConfigEnum.QENABLE)) {
            if (Config.getInstance().getBoolean(ConfigEnum.QCENTER)) {
                List<String> a = Config.getInstance().getConfig().getStringList(ConfigEnum.QMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setQuitMessage(Utils.colors(CenterMessage.CenteredMessage(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()}))));
            } else {
                List<String> a = Config.getInstance().getConfig().getStringList(ConfigEnum.QMSG.getPath());
                String b = StringUtils.join(a, "\n");
                e.setQuitMessage(Utils.colors(StringUtils.replaceEach(b,
                        new String[]{"%player%"},
                        new String[]{e.getPlayer().getName()})));
            }
        }
    }

}
