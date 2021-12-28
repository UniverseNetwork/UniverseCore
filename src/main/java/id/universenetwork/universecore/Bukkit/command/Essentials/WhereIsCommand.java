package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageFile;
import id.universenetwork.universecore.Bukkit.utils.utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

public class WhereIsCommand extends UNCommand {

    @CommandMethod("whereis|wis|uwhereis|uwis [target]")
    public void commandWhereIs(final @NonNull CommandSender sender,
                        final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {
        if (!utils.checkPermission(sender, "whereis")) return;

        TargetsCallback targets = this.getTargets(sender, targetName);

        if (targets.size() > 1) {
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.ONEPLAYER));
            return;
        }

        targets.stream().findFirst().ifPresent(player -> {
            if (sender instanceof Player) {
                List<String> a = MessageFile.message.getConfig().getStringList(MessageEnum.WHEREISMSG.getPath());
                String b = StringUtils.join(a, "\n");
                String c = utils.colors(StringUtils.replaceEach(b,
                        new String[]{"%player%", "%world%", "%x%", "%y%", "%z%", "%yaw%", "%pitch%"},
                        new String[]{player.getName(), Objects.requireNonNull(player.getLocation().getWorld()).getName(),
                                String.valueOf(player.getLocation().getX()), String.valueOf(player.getLocation().getY()), String.valueOf(player.getLocation().getZ()),
                                String.valueOf(player.getLocation().getYaw()), String.valueOf(player.getLocation().getPitch())}));
                TextComponent msg = new TextComponent(c);
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));
                msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(utils.colors("&aClick to teleport!")).create()));
                utils.sendSpigotMsg(sender, msg);
            } else {
                List<String> a = MessageFile.message.getConfig().getStringList(MessageEnum.WHEREISMSG.getPath());
                String b = StringUtils.join(a, "\n");
                utils.sendMsg(sender, StringUtils.replaceEach(b,
                        new String[]{"%player%", "%world%", "%x%", "%y%", "%z%", "%yaw%", "%pitch%"},
                        new String[]{player.getName(), Objects.requireNonNull(player.getLocation().getWorld()).getName(),
                                String.valueOf(player.getLocation().getX()), String.valueOf(player.getLocation().getY()), String.valueOf(player.getLocation().getZ()),
                                String.valueOf(player.getLocation().getYaw()), String.valueOf(player.getLocation().getPitch())}));
            }
        });
    }
}
