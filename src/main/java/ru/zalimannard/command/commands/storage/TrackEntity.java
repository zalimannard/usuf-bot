package ru.zalimannard.command.commands.storage;

public class TrackEntity {
    private final String title;
    private final String url;

    public TrackEntity(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
