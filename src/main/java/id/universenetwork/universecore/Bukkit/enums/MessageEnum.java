package id.universenetwork.universecore.Bukkit.enums;

public enum MessageEnum {

    PREFIX("prefix"),
    NOPERM("no-perm"),
    ONLYPLAYER("only-player"),
    TOOMANYARG("too-many-args"),
    RELOAD("reload"),
    NOPLAYER("player-not-found"),
    ADDSPECIFYPLAYER("add-specify-player"),
    ONEPLAYER("check-only-one-player"),
    SANTETT("santet-title"),
    SANTETS("santet-success"),
    SANTETA("santet-all-player"),
    TDON("toggledrop-on"),
    TDONT("toggledrop-on-target"),
    TDOFF("toggledrop-off"),
    TDOFFT("toggledrop-off-target"),
    KABOOMT("kaboom-title"),
    KABOOMS("kaboom-success"),
    KABOOMA("kaboom-all-player"),
    WHEREISMSG("whereis-message"),
    PINGMSG("ping-message"),
    PINGMSGO("ping-other-message"),
    GMCHANGE("gamemode-change"),
    GMCHANGEOTHERS("gamemode-change-other"),
    WLON("whitelist-on"),
    WLOFF("whitelist-off"),
    WLMSG("whitelist-message"),
    PIMSG("playerinfo-message");

    private final String path;

    MessageEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
