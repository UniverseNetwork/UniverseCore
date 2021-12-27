package id.universenetwork.universecore.Bukkit.enums;

public enum SuggestionEnum {

    FEATURE("Settings.enable"),
    PERGFEATURE("Settings.per-group-suggestion"),
    COMMANDLIST("Settings.command-list"),
    PERGROUPLIST("Settings.group");

    private final String path;

    SuggestionEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
