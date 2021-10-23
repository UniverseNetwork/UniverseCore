package id.universenetwork.universecore.enums;

public enum Message {

    PREFIX("prefix");

    private final String path;

    Message(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
