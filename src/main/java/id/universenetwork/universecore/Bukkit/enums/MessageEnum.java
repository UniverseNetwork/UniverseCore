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
    PINGMSGO("ping-other-message"),
    GMC("gamemode-creative"),
    GMS("gamemode-survival"),
    GMA("gamemode-adventure"),
    GMSP("gamemode-spectator"),
    ALRDINGMPPLY("already-in-gamemode-perplayer"),
    ALRDINGMPTRG("already-in-gamemode-target"),
    INVALID_ITEM("invalid-item"),
    WLON("whitelist-on"),
    WLOFF("whitelist-off");

    private final String path;

    MessageEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
