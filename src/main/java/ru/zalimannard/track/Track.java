package ru.zalimannard.track;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.zalimannard.Duration;

import java.io.File;
import java.util.Objects;

/**
 * The class responsible for storing track data. It is used instead of AudioTrack from lavaplayer, because downloading
 * tracks is implemented. AudioTrack doesn't work well enough.
 */
public class Track {
    private final String title;
    private final String author;
    private final Duration duration;
    private final String url;
    private final String requesterId;
    private File trackFile;
    private static final Logger LOGGER = LogManager.getRootLogger();

    public Track(String title, String author, Duration duration, String url,
                 String requesterId) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.url = url;
        this.requesterId = requesterId;
        LOGGER.trace("Object " + toString() + " has been created");
    }

    public boolean isDownloaded() {
        return trackFile != null;
    }

    /**
     * Get the video/audio file of the downloaded track.
     *
     * @return the track file. This file is downloaded beforehand. If downloading is not possible, null is returned
     */
    public File getTrackFile() {
        if (!isDownloaded()) {
            TrackLoader trackLoader = new TrackLoader();
            trackLoader.download(this);
        }
        if (trackFile != null) {
            LOGGER.debug("The track " + toString() + " file has been downloaded");
        } else {
            LOGGER.warn("The track " + toString() + " file hasn't been downloaded");
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