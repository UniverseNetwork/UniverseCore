package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerInfoCommand extends UNCommand {

    @CommandMethod("playerinfo|pi|uplayerinfo|upi [target]")
    public void commandPlayerInfo(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target",
                    defaultValue = "❤🗑🖇📎",
                    suggestions = "onePlayers") String targetName) {

        if (!utils.checkPermission(sender, "playerinfo")) return;

        if (targetName.equals("❤🗑🖇📎")) {
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.NOPLAYER));
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.ADDSPECIFYPLAYER));
            return;
        }

        TargetsCallback targets = this.getTargets(sender, targetName);

        if (targets.size() > 1) {
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.ONEPLAYER));
            return;
        }

        Player target = Bukkit.getPlayer(targetName);
        UUID uuid = null;
        if (target != null) {
            uuid = target.getUniqueId();
        } else {
            targets.notifyIfEmpty();
        }

        long timeNow = System.currentTimeMillis();
        long firstPlayed;
        long onlineTime;
        if (uuid == null) {
            targets.notifyIfEmpty();
            return;
        } else {
            firstPlayed = (timeNow - Bukkit.getOfflinePlayer(uuid).getFirstPlayed()) / 1000L;
            onlineTime = (timeNow - Bukkit.getOfflinePlayer(uuid).getLastPlayed()) / 1000L;
        }

        long finalFirstPlayed = firstPlayed;
        long finaleOnlineTime = onlineTime;

        targets.stream().findFirst().ifPresent(player -> {
            List<String> a = utils.getMsgStringList(MessageEnum.PIMSG);
            String b = StringUtils.join(a, "\n");
            utils.sendMsg(sender,
                    StringUtils.replaceEach(
                            b,
                            new String[]{"%player%", "%uuid%", "%ip%", "%first_played%", "%online_time%", "%ping%", "%gamemode%", "%isop%",
                                    "%isfly%", "%allowfly%", "%isinvulnerable%", "%flyspeed%", "%walkspeed%"},
                            new String[]{player.getName(),
                                    player.getUniqueId().toString(),
                                    String.valueOf(Objects.requireNonNull(player.getAddress()).getAddress()),
                                    utils.getTimeFormat((int) finalFirstPlayed),
                                    utils.getTimeFormats((int) finaleOnlineTime),
                                    String.valueOf(player.getPing()),
                                    player.getGameMode().toString(),
                                    player.isOp() ? "&aYes" : "&cNo",
                                    player.isFlying() ? "&aYes" : "&cNo",
                                    player.getAllowFlight() ? "&aYes" : "&cNo",
                                    player.isInvulnerable() ? "&aYes" : "&cNo",
                                    String.valueOf(player.getFlySpeed()),
                                    String.valueOf(player.getWalkSpeed())}
                    ));
        });
    }
}
