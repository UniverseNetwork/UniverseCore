package id.universenetwork.universecore.Bukkit.enums;

public enum ConfigEnum {

    USECONFIRMATION("Settings.use-confirmation"),
    MAXPLAYERCONFIRM("Settings.max-player-to-execute-confirmation"),
    JENABLE("JoinMessage.Enabled"),
    JCENTER("JoinMessage.Centered"),
    JMSG("JoinMessage.Message"),
    QENABLE("QuitMessage.Enabled"),
    QCENTER("QuitMessage.Centered"),
    QMSG("QuitMessage.Message");

    private final String path;

    ConfigEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

}
