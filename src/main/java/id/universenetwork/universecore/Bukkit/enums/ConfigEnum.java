package id.universenetwork.universecore.Bukkit.enums;

public enum ConfigEnum {

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
