package ru.zalimannard.track;

import ru.zalimannard.Duration;

import java.io.File;
import java.util.Objects;

public class Track {
    private final String title;
    private final String author;
    private final Duration duration;
    private final String url;
    private final String requesterId;
    private File trackFile;

    public Track(String title, String author, Duration duration, String url,
                 String requesterId) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.url = url;
        this.requesterId = requesterId;
    }

    public Track(Track track, String requesterId) {
        this.title = track.title;
        this.author = track.author;
        this.duration = track.duration;
        this.url = track.url;
        this.requesterId = requesterId;
    }

    public boolean isDownloaded() {
        return trackFile != null;
    }

    public File getTrackFile() {
        if (!isDownloaded()) {
            TrackLoader trackLoader = new TrackLoader();
            trackLoader.download(this);
        }
        return trackFile;
    }

    public void setTrackFile(File trackFile) {
        this.trackFile = trackFile;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public String getRequesterId() {
        return requesterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(getTitle(), track.getTitle()) && Objects.equals(getAuthor(), track.getAuthor()) && Objects.equals(getDuration(), track.getDuration()) && Objects.equals(getUrl(), track.getUrl()) && Objects.equals(getRequesterId(), track.getRequesterId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), getDuration(), getUrl(), getRequesterId());
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", duration=" + duration +
                ", url='" + url + '\'' +
                ", requesterId='" + requesterId + '\'' +
                ", trackFile=" + trackFile +
                '}';
    }
}