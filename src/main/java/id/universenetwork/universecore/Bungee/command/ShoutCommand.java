package id.universenetwork.universecore.Bungee.command;

import id.universenetwork.universecore.Bungee.manager.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.lang.StringUtils;

public class ShoutCommand extends Command {

    private ConfigManager cfg;

    public ShoutCommand() {
        super("shout", "universenetwork.shout", "sh", "gc", "globalchat");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage("§cUsage: /" + this.getName() +  " <message>");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg).append(" ");
            }
            String message = sb.toString().trim();
            for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                if (all.hasPermission("universenetwork.shout")) {
                    String out = cfg.getString("Shout.default-format");
                    String SOut = cfg.getString("Shout.staff-format");
                    ProxiedPlayer p = (ProxiedPlayer) commandSender;

                    StringUtils.replace(out, "%message%", message);
                    StringUtils.replace(out, "%player%", commandSender.getName());
                    StringUtils.replace(out, "%server%", p.getServer().getInfo().getName());
                    StringUtils.replace(out, "&", "§");
                    if (p.hasPermission("universenetwork.shout.staff")) {
                        StringUtils.replace(SOut, "%message%", message);
                        StringUtils.replace(SOut, "%player%", commandSender.getName());
                        StringUtils.replace(SOut, "%server%", p.getServer().getInfo().getName());
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', SOut));
                    } else {
                        all.sendMessage(out);
                    }
                }
            }
        }

    }


}
