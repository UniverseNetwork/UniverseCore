package id.universenetwork.universecore.Bukkit.command.Essentials;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import id.universenetwork.universecore.Bukkit.manager.UNCommand;
import id.universenetwork.universecore.Bukkit.manager.file.CustomPressurePlateAction;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomPressureActionCommand extends UNCommand {

    @CommandMethod(value = "custompressureaction|cpa|ucustompressureaction|ucpa <id> effect <effect> <duration> <amplifier>", requiredSender = Player.class)
    public void CustomPressureActionEffect(final @NonNull CommandSender sender,
                                           final @NonNull @Argument("id") String id,
                                           final @NonNull @Argument(value = "effect", suggestions = "effectLists") String effect,
                                           final @Argument("duration") int duration,
                                           final @Argument("amplifier") int amplifier) {

        CustomPressurePlateAction file = new CustomPressurePlateAction();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (file.getListPlate().contains(player.getTargetBlock(null, 5).getType())) {
                if (!file.PathIsExist(id)) {
                    file.setPressurePlate(id, player.getTargetBlock(null, 5).getWorld().getName(), player.getTargetBlock(null, 5).getX(), player.getTargetBlock(null, 5).getY(), player.getTargetBlock(null, 5).getZ(), "Effect", effect, duration, amplifier);
                    Utils.sendMsg(sender, "&aSuccessfully add Action to PressurePlate with id &e" + id + "&a.");
                } else {
                    Utils.sendMsg(player, "&cId already exist. Please use another Id name!");
                }
            } else {
                Utils.sendMsg(player, "&cYour not looking at Pressure Plate!");
            }
        }
    }

    @CommandMethod("custompressureaction|cpa|ucustompressureaction|ucpa remove|delete [id]")
    public void CustomPressureActionRemove(final @NonNull CommandSender sender, final @NonNull @Argument(value = "id", suggestions = "listID") String id) {
        CustomPressurePlateAction file = new CustomPressurePlateAction();
        file.removeId(id);
        Utils.sendMsg(sender, "&aSuccessfully remove ID &e" + id + "&a!");
    }

    @Suggestions("effectLists")
    public List<String> effectLists(CommandContext<CommandSender> sender, String context) {
        return Arrays.stream(PotionEffectType.values()).map(PotionEffectType::getName).filter(s -> s.startsWith(context.toUpperCase())).collect(Collectors.toList());
    }

    @Suggestions("listID")
    public List<String> idList(CommandContext<CommandSender> sender, String context) {
        List<String> ret = new ArrayList<>();
        CustomPressurePlateAction file = new CustomPressurePlateAction();

        if (file.getSection("PressurePlate") != null) {
            for (String key : file.getSection("PressurePlate").getKeys(false)) {
                if (key.startsWith(context.toUpperCase())) {
                    ret.add(key);
                }
            }
        } else {
            return null;
        }
        return ret;
    }

}
