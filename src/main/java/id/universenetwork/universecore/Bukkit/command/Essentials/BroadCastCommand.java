package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.standard.StringArrayArgument;
import id.universenetwork.universecore.Bukkit.UniverseCore;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.utils.CenterMessage;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadCastCommand extends UNCommand {

    @Getter
    private final UniverseCore core;

    public BroadCastCommand(UniverseCore core) {
        this.core = core;

        core.getManager().command(core.getManager().commandBuilder("broadcast")
                .argument(StringArrayArgument.optional("args",
                        (context, s) -> context.getRawInput()), ArgumentDescription.of("test"))
                .handler(context -> {
                    final String[] a = context.getOrDefault("args", new String[0]);
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
                }));

    }
}
