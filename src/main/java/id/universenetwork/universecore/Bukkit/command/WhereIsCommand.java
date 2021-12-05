package id.universenetwork.universecore.Bukkit.command;

import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageData;
import id.universenetwork.universecore.Bukkit.utils.utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;
import java.util.Objects;

import static id.universenetwork.universecore.Bukkit.utils.utils.colors;

public class WhereIsCommand extends UNCommand {

    public WhereIsCommand() {
        super("whereis",
                "universenetwork.whereis",
                "/whereis (player)",
                null, 1, true, "uwhereis", "wis", "uwis");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            s.sendMessage(colors("&6Usage: &e" + getUsage()));
        } else if (args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            if (t != null) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    List<String> a = MessageData.message.getConfig().getStringList(MessageEnum.WHEREISMSG.getPath());
                    String b = StringUtils.join(a, "\n");
                    String c = ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(b,
                            new String[]{"%player%", "%world%", "%x%", "%y%", "%z%", "%yaw%", "%pitch%"},
                            new String[]{t.getName(), Objects.requireNonNull(t.getLocation().getWorld()).getName(),
                                    String.valueOf(t.getLocation().getX()), String.valueOf(t.getLocation().getY()), String.valueOf(t.getLocation().getZ()),
                                    String.valueOf(t.getLocation().getYaw()), String.valueOf(t.getLocation().getPitch())}));
                    TextComponent d = new TextComponent(c);
                    d.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.valueOf(p.teleport(t, PlayerTeleportEvent.TeleportCause.PLUGIN))));
                    d.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(colors("&6Click to teleport to " + t.getName())).create()));
                    s.spigot().sendMessage(d);
                } else {
                    List<String> a = MessageData.message.getConfig().getStringList(MessageEnum.WHEREISMSG.getPath());
                    String b = StringUtils.join(a, "\n");
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(b,
                            new String[]{"%player%", "%world%", "%x%", "%y%", "%z%", "%yaw%", "%pitch%"},
                            new String[]{t.getName(), Objects.requireNonNull(t.getLocation().getWorld()).getName(),
                                    String.valueOf(t.getLocation().getX()), String.valueOf(t.getLocation().getY()), String.valueOf(t.getLocation().getZ()),
                                    String.valueOf(t.getLocation().getYaw()), String.valueOf(t.getLocation().getPitch())})));
                }
            } else s.sendMessage(MessageData.getInstance().getString(MessageEnum.NOPLAYER));
        }
    }

    @Override
    public List<String> TabCompleter(CommandSender sender, String s, String[] args) {
        if (args.length == 1) {
            return utils.getOnlinePlayers(args[0]);
        }
        return null;
    }
}
