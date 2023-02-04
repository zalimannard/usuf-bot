package ru.zalimannard.command.commands.storage;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackEntity track = (TrackEntity) o;
        return Objects.equals(title, track.title) && Objects.equals(url, track.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    @Override
    public String toString() {
        return "TrackEntity{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
