package id.universenetwork.universecore.Bukkit.listener;

import com.google.common.base.Joiner;
import id.universenetwork.universecore.Bukkit.enums.ConfigEnum;
import id.universenetwork.universecore.Bukkit.manager.file.Config;
import id.universenetwork.universecore.Bukkit.manager.file.PlayerData;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;
import java.util.List;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
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
        PlayerData playerData = new PlayerData();
        Iterator<String> list = playerData.getSection(player.getUniqueId().toString()).getKeys(false).iterator();
        if (list.hasNext()) {
            String key = list.next();
            if (playerData.hasPlayerData(player, key)) {
                switch (key) {
                    case "godMode": {
                        if (playerData.getConfig().getBoolean(player.getUniqueId() + ".godMode")) {
                            player.setInvulnerable(true);
                        }
                    }
                    case "flyMode": {
                        if (playerData.getConfig().getBoolean(player.getUniqueId() + ".flyMode")) {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        }
                    }
                }
                Utils.sendMsg(player, "&7Your &e" + Joiner.on(", ").join(playerData.getSection(player.getUniqueId().toString()).getKeys(false).toArray()) + " &7is &aON");
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
