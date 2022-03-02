package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.MessageFile;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import net.kyori.text.TextComponent;
import net.kyori.text.adapter.bukkit.TextAdapter;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

public class WhereIsCommand extends UNCommand {

    @CommandMethod("whereis|wis|uwhereis|uwis [target]")
    public void commandWhereIs(final @NonNull CommandSender sender,
                        final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "onePlayers") String targetName) {
        if (!Utils.checkPermission(sender, "whereis")) return;

        TargetsCallback targets = this.getTargets(sender, targetName);

        if (targets.size() > 1) {
            Utils.sendMsg(sender, Utils.getPrefix() + Utils.getMsgString(MessageEnum.ONEPLAYER));
            return;
        }

        targets.stream().findFirst().ifPresent(player -> {
            List<String> a = MessageFile.message.getConfig().getStringList(MessageEnum.WHEREISMSG.getPath());
            String b = StringUtils.join(Utils.colorsArray(a), "\n");
            if (sender instanceof Player) {
                String c = Utils.colors(StringUtils.replaceEach(b,
                        new String[]{"%player%", "%world%", "%x%", "%y%", "%z%", "%yaw%", "%pitch%"},
                        new String[]{player.getName(), Objects.requireNonNull(player.getLocation().getWorld()).getName(),
                                String.valueOf(player.getLocation().getX()), String.valueOf(player.getLocation().getY()), String.valueOf(player.getLocation().getZ()),
                                String.valueOf(player.getLocation().getYaw()), String.valueOf(player.getLocation().getPitch())}));
                TextComponent hover = TextComponent.builder().content(c)
                        .hoverEvent(HoverEvent.showText(TextComponent.of(Utils.colors("&aClick to teleport!"))))
                        .clickEvent(ClickEvent.runCommand("/tp " + player.getName())).build();
                TextAdapter.sendMessage(sender, hover);
            } else {
                Utils.sendMsg(sender, StringUtils.replaceEach(b,
                        new String[]{"%player%", "%world%", "%x%", "%y%", "%z%", "%yaw%", "%pitch%"},
                        new String[]{player.getName(), Objects.requireNonNull(player.getLocation().getWorld()).getName(),
                                String.valueOf(player.getLocation().getX()), String.valueOf(player.getLocation().getY()), String.valueOf(player.getLocation().getZ()),
                                String.valueOf(player.getLocation().getYaw()), String.valueOf(player.getLocation().getPitch())}));
            }
        });
    }
}
