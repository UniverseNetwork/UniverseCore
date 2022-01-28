package id.universenetwork.universecore.Bukkit.manager.cooldown;

import lombok.Data;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
public class CanSkipCooldown {
    private final String key;
    private final long time;
    private final String permission;
    private final CommandSender sender;
    @Nullable
    private final List<String> warnings;
}
