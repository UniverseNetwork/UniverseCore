package id.universenetwork.universecore.Bukkit.enums;

public enum MessageEnum {

    PREFIX("prefix"),
    NOPERM("no-perm"),
    ONLYPLAYER("only-player"),
    TOOMANYARG("too-many-args"),
    RELOAD("reload"),
    NOPLAYER("player-not-found"),
    SANTETT("santet-title"),
    SANTETS("santet-success"),
    SANTETA("santet-all-player"),
    TDON("toggledrop-on"),
    TDOFF("toggledrop-off"),
    KABOOMT("kaboom-title"),
    KABOOMS("kaboom-success"),
    WHEREISMSG("whereis-message"),
    PINGMSG("ping-message"),
    PINGCONSOLEMSG("ping-console-message"),
    PINGMSGO("ping-other-message");

    private final String path;

    MessageEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
