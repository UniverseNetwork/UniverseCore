package id.universenetwork.universecore.Bukkit.listener;

import id.universenetwork.universecore.Bukkit.enums.SuggestionEnum;
import id.universenetwork.universecore.Bukkit.manager.file.SuggestionBlocker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Iterator;

public class SuggestionListener implements Listener {

    @EventHandler
    public void onSuggestionProcess(PlayerCommandSendEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("universenetwork.suggestion-bypass")) {
            if (SuggestionBlocker.getInstance().getBoolean(SuggestionEnum.FEATURE)) {
                event.getCommands().clear();
                event.getCommands().addAll(SuggestionBlocker.getInstance().getStringList(SuggestionEnum.COMMANDLIST));
                Iterator<String> group = SuggestionBlocker.getInstance().getConfigSection(SuggestionEnum.PERGROUPLIST).iterator();

                if (SuggestionBlocker.getInstance().getBoolean(SuggestionEnum.PERGFEATURE)) {
                    if (group.hasNext()) {
                        String groups = group.next();
                        if (p.hasPermission("universenetwork.suggestion-group." + groups)) {
                            event.getCommands().addAll(SuggestionBlocker.getInstance().getFile().getStringList(SuggestionEnum.PERGROUPLIST.getPath() + "." + groups));
                        }
                    }
                }
            }
        }
    }

}
