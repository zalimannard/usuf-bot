package ru.zalimannard.command.commands.storage;

import java.util.ArrayList;
import java.util.List;

public class QueueEntity {
    private final String title;
    private final String description;
    private final List<TrackEntity> tracks;

    public QueueEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.tracks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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
}
