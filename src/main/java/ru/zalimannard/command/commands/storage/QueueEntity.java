package ru.zalimannard.command.commands.storage;

import ru.zalimannard.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueueEntity {
    private final String title;
    private final String description;
    private final Duration duration;
    private final List<TrackEntity> tracks;

    public QueueEntity(String title, String description, Duration duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.tracks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Duration getDuration() {
        return duration;
    }

    public int size() {
        return tracks.size();
    }

    public void addTrackEntity(TrackEntity trackEntity) {
        tracks.add(trackEntity);
    }

    public TrackEntity getTrackEntity(int index) {
        return tracks.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueEntity that = (QueueEntity) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(tracks, that.tracks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tracks);
    }

    @Override
    public String toString() {
        return "QueueEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
