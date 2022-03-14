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
import com.google.common.base.Joiner;
import com.google.common.reflect.ClassPath;
import id.universenetwork.multiversion.MultiVersion;
import id.universenetwork.universecore.Bukkit.enums.MessageEnum;
import id.universenetwork.universecore.Bukkit.listener.SuggestionListener;
import id.universenetwork.universecore.Bukkit.manager.confirmation.ConfirmationManager;
import id.universenetwork.universecore.Bukkit.manager.cooldown.CooldownManager;
import id.universenetwork.universecore.Bukkit.manager.file.*;
import id.universenetwork.universecore.Bukkit.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private CooldownManager cooldownManager;
    private MultiVersion multiVersion;

    private static UniverseCore instance;

    public static UniverseCore getInstance() {
        return UniverseCore.instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        long start = System.currentTimeMillis();
        instance = this;
        this.loadMultiVersion();
        this.register();
        this.confirmationManager = new ConfirmationManager(this);
        this.cooldownManager = new CooldownManager(this);
        this.cooldownManager.smartDeleteCooldowns();
        this.onEnableMessage();
        this.getLogger().info("Took " + (System.currentTimeMillis() - start) + "ms to enable!");
        this.getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.onDisableMessage();
    }

    public void saveAllConfig() {
        Config.getInstance().saveConfig();
        MessageFile.getInstance().saveConfig();
        SuggestionBlocker.getInstance().saveConfig();
        PlayerData.getInstance().saveConfig();
        CustomPressurePlateAction.getInstance().saveConfig();
    }

    @SneakyThrows
    public void register() {
        Config.cfg.saveDefaultConfig();
        MessageFile.message.saveDefaultConfig();
        SuggestionBlocker.message.saveDefaultConfig();
        PlayerData.config.saveDefaultConfig();
        CustomPressurePlateAction.file.saveDefaultConfig();

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
                .withNoPermissionHandler()
                .withDecorator(component -> text()
                        .append(text(Utils.colors(Utils.getMsgString(MessageEnum.NOPERM)))).build())
                .apply(this.manager, bukkitAudiences::sender);

        new MinecraftExceptionHandler<CommandSender>()
                .withInvalidSyntaxHandler()
                .withInvalidSenderHandler()
                .withNoPermissionHandler()
                .withArgumentParsingHandler()
                .withCommandExecutionHandler()
                .withDecorator(
                        component -> text()
                                .append(text(Utils.colors(Utils.getPrefix())))
                                .append(component).build()
                ).apply(this.manager, bukkitAudiences::sender);

        this.minecraftHelp.setHelpColors(MinecraftHelp.HelpColors.of(
                TextColor.color(5592405),
                TextColor.color(16777045),
                TextColor.color(11184810),
                TextColor.color(5635925),
                TextColor.color(5592405)));

        /*DependencyHelper helper = new DependencyHelper(UniverseCore.class.getClassLoader());
        File dir = new File("plugins/UniverseCore/libs");
        Map<String, String> depencies = new HashMap<>();
        depencies.put("H2-2.1.210.jar", "https://repo1.maven.org/maven2/com/h2database/h2/2.1.210/h2-2.1.210.jar");
        depencies.put("HikariCP-3.3.0.jar", "https://repo1.maven.org/maven2/com/zaxxer/HikariCP/3.3.0/HikariCP-3.3.0.jar");
        try {
            helper.download(depencies, dir.toPath());
            helper.loadDir(dir.toPath());
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[UniverseCore] Dependencies loaded!");*/

        this.commandRegister();
        this.registerListener("id.universenetwork.universecore.Bukkit.manager.helpme.gui", "HelpMeAcceptedGui");
        this.registerListener("id.universenetwork.universecore.Bukkit.listener", null);
        /*Utils.registerListener(new GuiListener());*/
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

    @SuppressWarnings("UnstableApiUsage")
    public void registerListener(String path, @Nullable String blackListClass) {
        List<String> registered = new ArrayList<>();
        this.getLogger().info("Registering listeners...");
        try {
            ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(path)) {
                if (blackListClass != null && classInfo.getName().contains(blackListClass)) {
                    continue;
                }

                try {
                    Class<?> listenerClass = Class.forName(classInfo.getName());
                    Bukkit.getServer().getPluginManager().registerEvents((Listener) listenerClass.newInstance(), this);
                    registered.add(listenerClass.getName());
                } catch (Exception e) {
                    this.getLogger().severe("Failed loading Listener class: " + classInfo.getName());
                    e.printStackTrace();
                }
            }
            this.getLogger().info("Finish! " + registered.size() + " Listeners has been registered.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAnnotationCommands(Object... clazz) {
        Arrays.stream(clazz).forEach(this.annotationParser::parse);
    }

    private void loadMultiVersion() {
        this.getLogger().info("Checking for the server version....");

        String version = Utils.getNmsVersion();
        this.getLogger().info("Detected server version");
        this.getLogger().info("Your server is using version: " + version);
        try {
            Class<?> support;
            switch (version) {
                case "v1_8_R3": {
                    support = Class.forName("id.universenetwork.multiversion.v1_8_R3.v1_8_R3");
                    this.getLogger().info("Loaded multi version v1_8_R3");
                    break;
                }
                case "v1_9_R1":
                case "v1_9_R2":
                case "v1_10_R1":
                case "v1_11_R1":
                case "v1_12_R2": {
                    support = Class.forName("id.universenetwork.multiversion.v1_12_R1.v1_12_R1");
                    this.getLogger().info("Loaded multi version v1_12_R1");
                    break;
                }
                case "v1_13_R1": {
                    Bukkit.getPluginManager().registerEvents(new SuggestionListener(), this);
                    support = Class.forName("id.universenetwork.multiversion.v1_13_R2.v1_13_R2");
                    this.getLogger().info("Loaded multi version v1_13_R2");
                    break;
                }
                case "v1_14_R1": {
                    Bukkit.getPluginManager().registerEvents(new SuggestionListener(), this);
                    support = Class.forName("id.universenetwork.multiversion.v1_14_R1.v1_14_R1");
                    this.getLogger().info("Loaded multi version v1_14_R1");
                    break;
                }
                case "v1_15_R1": {
                    Bukkit.getPluginManager().registerEvents(new SuggestionListener(), this);
                    support = Class.forName("id.universenetwork.multiversion.v1_15_R1.v1_15_R1");
                    this.getLogger().info("Loaded multi version v1_15_R1");
                    break;
                }
                case "v1_16_R3": {
                    Bukkit.getPluginManager().registerEvents(new SuggestionListener(), this);
                    support = Class.forName("id.universenetwork.multiversion.v1_16_R3.v1_16_R3");
                    this.getLogger().info("Loaded multi version v1_16_R3");
                    break;
                }
                case "v1_17_R1": {
                    Bukkit.getPluginManager().registerEvents(new SuggestionListener(), this);
                    support = Class.forName("id.universenetwork.multiversion.v1_17_R1.v1_17_R1");
                    this.getLogger().info("Loaded multi version v1_17_R1");
                    break;
                }
                case "v1_18_R1": {
                    Bukkit.getPluginManager().registerEvents(new SuggestionListener(), this);
                    support = Class.forName("id.universenetwork.multiversion.v1_18_R1.v1_18_R1");
                    this.getLogger().info("Loaded multi version v1_18_R1");
                    break;
                }
                default: {
                    this.getLogger().info("Unsupported server version!");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
            }

            multiVersion = (MultiVersion) support.getConstructor(Class.forName("org.bukkit.plugin.Plugin")).newInstance(this);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void onEnableMessage() {
        Bukkit.getLogger().info(Utils.colors( "\n"+"\n" +
                "&b██    ██ &e███   ██ &a █████   █████  ██████  ███████  &7Server Version: &6" + Bukkit.getServer().getVersion() + "\n" +
                "&b██    ██ &e████  ██ &a██   ██ ██   ██ ██   ██ ██       &7Version: &6 " + this.getDescription().getVersion() + "\n" +
                "&b██    ██ &e██ ██ ██ &a██      ██   ██ ██████  █████    &7Author: &e" + Joiner.on(", ").join(this.getDescription().getAuthors()) + "\n" +
                "&b██    ██ &e██  ████ &a██   ██ ██   ██ ██   ██ ██       &aHas been Enable!" + "\n" +
                "&b ██████  &e██   ███ &a █████   █████  ██   ██ ███████  " +
                "\n"));
    }

    public void onDisableMessage() {
        Bukkit.getLogger().info(Utils.colors( "\n"+"\n" +
                "&b██    ██ &e███   ██ &a █████   █████  ██████  ███████  &7Server Version: &6" + Bukkit.getServer().getVersion() + "\n" +
                "&b██    ██ &e████  ██ &a██   ██ ██   ██ ██   ██ ██       &7Version: &6" + this.getDescription().getVersion() + "\n" +
                "&b██    ██ &e██ ██ ██ &a██      ██   ██ ██████  █████    &7Author: &e" + Joiner.on(", ").join(this.getDescription().getAuthors()) + "\n" +
                "&b██    ██ &e██  ████ &a██   ██ ██   ██ ██   ██ ██       &cHas been Disable!" + "\n" +
                "&b ██████  &e██   ███ &a █████   █████  ██   ██ ███████  " +
                "\n"));
    }
}
