package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.ProxiedBy;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerInfoCommand extends UNCommand {

    @CommandMethod("playerinfo|pi|uplayerinfo|upi [target]")
    public void commandPlayerInfo(
            final @NonNull CommandSender sender,
            final @NonNull @Argument(value = "target", defaultValue = "self", suggestions = "players") String targetName) {

        if (!utils.checkPermission(sender, "playerinfo")) return;

        TargetsCallback targets = this.getTargets(sender, targetName);

        if (targets.size() > 1) {
            utils.sendMsg(sender, utils.getPrefix() + utils.getMsgString(MessageEnum.ONEPLAYER));
            return;
        }

        UUID uuid = Objects.requireNonNull(Bukkit.getPlayer(targets.toString())).getUniqueId();
        long timeNow = System.currentTimeMillis();
        long firstPlayed = (timeNow - Bukkit.getOfflinePlayer(uuid).getFirstPlayed()) / 1000L;
        long lastPlayed = (timeNow - Bukkit.getOfflinePlayer(uuid).getLastPlayed()) / 1000L;

        targets.stream().findFirst().ifPresent(player -> {
            List<String> a = utils.getMsgStringList(MessageEnum.PIMSG);
            String b = StringUtils.join(a, "\n");
            utils.sendMsg(sender,
                    StringUtils.replaceEach(
                            b,
                            new String[]{"%player%", "%uuid%", "%ip%", "%first_played%", "%last_played%", "%ping%", "%gamemode%", "%isop%",
                                    "%isfly%", "%allowfly%", "%isinvulnerable%", "%flyspeed%", "%walkspeed%"},
                            new String[]{player.getName(),
                                    player.getUniqueId().toString(),
                                    Objects.requireNonNull(player.getAddress()).getHostName(),
                                    utils.getTimeFormat((int) lastPlayed),
                                    utils.getTimeFormats((int) firstPlayed),
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
