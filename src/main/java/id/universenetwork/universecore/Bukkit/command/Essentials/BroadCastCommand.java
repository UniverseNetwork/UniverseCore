package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArrayArgument;
import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BroadCastCommand extends UNCommand {

    @Getter
    private final UniverseCore core;

    public BroadCastCommand(UniverseCore core) {
        this.core = core;

        core.getManager().command(core.getManager().commandBuilder("broadcast", "ubc", "ubroadcast", "bc")
                .argument(StringArrayArgument.optional("message",
                        (context, s) -> context.getRawInput()), ArgumentDescription.of("message that you want to broadcast"))
                .handler(context -> {
                    final String[] a = context.getOrDefault("message", new String[0]);

                    if (Objects.requireNonNull(a).length == 0) {
                        if (context.getSender() instanceof HumanEntity) {
                            Player p = (Player) context.getSender();
                            p.performCommand("uni help broadcast");
                        } else {
                            Bukkit.dispatchCommand(context.getSender(), "uni help broadcast");
                        }
                    } else {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            System.out.println(" ");
                            System.out.println(CenterMessage.CenteredMessage("§e§lAnnouncement"));
                            System.out.println(" ");
                            System.out.println(CenterMessage.CenteredMessage(StringUtils.join(a, " ")));
                            System.out.println(" ");
                            all.sendMessage(" ");
                            CenterMessage.sendCentredMessage(all, "&e&lAnnouncement");
                            all.sendMessage(" ");
                            CenterMessage.sendCentredMessage(all, StringUtils.join(a, " "));
                            all.sendMessage(" ");
                        }
                    }
                }));
    }
}
