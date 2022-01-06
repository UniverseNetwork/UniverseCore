package id.universenetwork.universecore.Bukkit.enums;

public enum MessageEnum {

    // System Message
    PREFIX("prefix"),
    NOPERM("no-perm"),
    ONLYPLAYER("only-player"),
    TOOMANYARG("too-many-args"),
    RELOAD("reload"),
    NOPLAYER("player-not-found"),
    ADDSPECIFYPLAYER("add-specify-player"),
    ONEPLAYER("check-only-one-player"),

    // Troll Command Message Enum
    SANTETT("santet-title"),
    SANTETS("santet-success"),
    SANTETA("santet-all-player"),
    KABOOMT("kaboom-title"),
    KABOOMS("kaboom-success"),
    KABOOMA("kaboom-all-player"),
    DUART("duar-title"),
    DUARS("duar-success"),
    DUARA("duar-all-player"),
    COVIDT("covid-title"),
    COVIDTA("covid-title-all"),
    COVIDST("covid-subtitle"),
    COVIDS("covid-success"),
    COVIDSA("covid-all-player"),

    // Essentials Command Message Enum
    TDON("toggledrop-on"),
    TDONT("toggledrop-on-target"),
    TDOFF("toggledrop-off"),
    TDOFFT("toggledrop-off-target"),
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
