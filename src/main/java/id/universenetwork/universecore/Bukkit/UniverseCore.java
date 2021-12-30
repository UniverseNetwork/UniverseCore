package id.universenetwork.universecore.Bukkit;

import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.common.reflect.ClassPath;
import id.universenetwork.universecore.Bukkit.listener.JoinQuitListener;
import id.universenetwork.universecore.Bukkit.listener.SuggestionListener;
import id.universenetwork.universecore.Bukkit.listener.ToggleDropListener;
import id.universenetwork.universecore.Bukkit.manager.ConfirmationManager;
import id.universenetwork.universecore.Bukkit.manager.file.Config;
import id.universenetwork.universecore.Bukkit.manager.file.MessageFile;
import id.universenetwork.universecore.Bukkit.manager.file.SuggestionBlocker;
import id.universenetwork.universecore.Bukkit.utils.utils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

import static net.kyori.adventure.text.Component.text;

@Getter
public final class UniverseCore extends JavaPlugin {

    @Getter
    private AnnotationParser<CommandSender> annotationParser;
    @Getter
    private PaperCommandManager<CommandSender> manager;
    @Getter
    private MinecraftHelp<CommandSender> minecraftHelp;

    @Setter
    private ConfirmationManager confirmationManager;

    private static UniverseCore instance;

    public static UniverseCore getInstance() {
        return UniverseCore.instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        long start = System.currentTimeMillis();
        instance = this;
        this.register();
        this.confirmationManager = new ConfirmationManager(this);
        System.out.println("\nUniverseCore Has been enabled!\n" +
                "source: https://github.com/UniverseNetwork/UniverseCore\n" +
                "website: https://universenetwork.id/");
        this.onEnableMessage();
        System.out.println("Took " + (System.currentTimeMillis() - start) + "ms to enable!");
        System.out.println();
        System.out.println();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveAllConfig();
        this.onDisableMessage();
    }

    public void saveAllConfig() {
        Config.getInstance().saveConfig();
        MessageFile.getInstance().saveConfig();
        SuggestionBlocker.getInstance().saveConfig();
    }

    @SneakyThrows
    public void register() {
        Config.cfg.saveDefaultConfig();
        MessageFile.message.saveDefaultConfig();
        SuggestionBlocker.message.saveDefaultConfig();

        Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction = CommandExecutionCoordinator.simpleCoordinator();
        Function<CommandSender, CommandSender> mapperFunction = Function.identity();

        this.manager = new PaperCommandManager<>(this, executionCoordinatorFunction, mapperFunction, mapperFunction);

        Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
                CommandMeta.simple().with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description")).build();

        this.annotationParser = new AnnotationParser<>(this.manager,
                CommandSender.class, commandMetaFunction);

        BukkitAudiences bukkitAudiences = BukkitAudiences.create(this);

        this.minecraftHelp = new MinecraftHelp<>("/universe help",
                bukkitAudiences::sender,
                this.manager);

        if (this.manager.queryCapability(CloudBukkitCapabilities.BRIGADIER)) {
            this.manager.registerBrigadier();
        }

        if (this.manager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            this.manager.registerAsynchronousCompletions();
        }

        new MinecraftExceptionHandler<CommandSender>()
                .withInvalidSyntaxHandler()
                .withInvalidSenderHandler()
                .withNoPermissionHandler()
                .withArgumentParsingHandler()
                .withCommandExecutionHandler()
                .withDecorator(
                        component -> text()
                                .append(text("[", NamedTextColor.DARK_GRAY))
                                .append(text("Universe", NamedTextColor.GOLD))
                                .append(text("] ", NamedTextColor.DARK_GRAY))
                                .append(component).build()
                ).apply(this.manager, bukkitAudiences::sender);

        this.minecraftHelp.setHelpColors(MinecraftHelp.HelpColors.of(
                TextColor.color(5592405),
                TextColor.color(16777045),
                TextColor.color(11184810),
                TextColor.color(5635925),
                TextColor.color(5592405)));

        this.commandRegister();
        utils.registerListener(
                new JoinQuitListener(),
                new ToggleDropListener(),
                new SuggestionListener()
                );
    }

    @SuppressWarnings("UnstableApiUsage")
    public void commandRegister() {
        this.getLogger().info("Loading and registering commands...");
        try {
            ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("id.universenetwork.universecore.Bukkit.command")) {
                if (classInfo.getName().endsWith("WhitelistCommand") || classInfo.getName().contains(".test")) {
                    continue;
                }

                try {
                    Class<?> commandClass = Class.forName(classInfo.getName());
                    this.parseAnnotationCommands(commandClass.newInstance());
                    this.getLogger().info("Registered command: " + commandClass.getSimpleName() + " #" + manager.getCommands().size());
                } catch (Exception e) {
                    this.getLogger().severe("Failed loading command class: " + classInfo.getName());
                    e.printStackTrace();
                }
            }
            this.getLogger().info("Finish! " + manager.getCommands().size() + " Commands has been registered.");
        } catch (IOException e) {
            this.getLogger().severe("Failed loading command classes!");
            e.printStackTrace();
        }
    }

    private void parseAnnotationCommands(Object... clazz) {
        Arrays.stream(clazz).forEach(this.annotationParser::parse);
    }

    public void onEnableMessage() {
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("§8╔══════════════════════════════════════════════════════════════════╗");
        Bukkit.getLogger().info("§8║                                                                  ║");
        Bukkit.getLogger().info("§8║                        §bUniverse§eNetwork                           §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                         §aWelcome back!                            §8║");
        Bukkit.getLogger().info("§8║                     §aPlugin has been enabled                      §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7Server IP§8: §6play.universenetwork.id                §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                          §7Version: §b" + this.getDescription().getVersion() + "                            §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                      §7Author: " + this.getDescription().getAuthors() + "                          §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7https://github.com/UniverseNetwork                §8║");
        Bukkit.getLogger().info("§8╚══════════════════════════════════════════════════════════════════╝");
        Bukkit.getLogger().info("");
    }

    public void onDisableMessage() {
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("§8╔══════════════════════════════════════════════════════════════════╗");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                        §bUniverse§eNetwork                           §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                           §cGoodbye!                               §8║");
        Bukkit.getLogger().info("§8║                     §cDisabling the plugin....                     §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7Server IP§8: §6play.universenetwork.id                §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                          §7Version: §b" + this.getDescription().getVersion() + "                            §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                      §7Author: " + this.getDescription().getAuthors() + "                          §8║");
        Bukkit.getLogger().info("§8║                                                                  §8║");
        Bukkit.getLogger().info("§8║                §7https://github.com/UniverseNetwork                §8║");
        Bukkit.getLogger().info("§8╚══════════════════════════════════════════════════════════════════╝");
        Bukkit.getLogger().info("");
    }

}
