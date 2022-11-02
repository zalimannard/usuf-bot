package ru.zalimannard.track;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.zalimannard.Time;

import java.io.File;
import java.util.Objects;

/**
 * The class responsible for storing track data. It is used instead of AudioTrack from lavaplayer, because downloading
 * tracks is implemented. AudioTrack doesn't work well enough.
 */
public class Track {
    private final String title;
    private final String author;
    private final Time duration;
    private final String url;
    private final String requesterId;
    private File trackFile;
    private static final Logger LOGGER = LogManager.getRootLogger();

    /**
     * Create an instance of the Track class with all informative parameters.
     *
     * @param title       the title of the track
     * @param author      the title of the track
     * @param duration    the duration of the track
     * @param url         the url of the track
     * @param requesterId id of the requester who ordered the track
     */
    public Track(String title, String author, Time duration, String url,
                 String requesterId) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.url = url;
        this.requesterId = requesterId;
        LOGGER.trace("Object " + toString() + " has been created");
    }

    /**
     * Check if the track file is downloaded.
     *
     * @return true if the file is downloaded. Otherwise false
     */
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

    /**
     * Set a file for the current track
     *
     * @param trackFile the file for the current track
     */
    public void setTrackFile(File trackFile) {
        this.trackFile = trackFile;
    }

    /**
     * Get title of the track.
     *
     * @return the title of the track
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get author of the track.
     *
     * @return the author of the track
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get duration of the track.
     *
     * @return the duration of the track
     */
    public Time getDuration() {
        return duration;
    }

    /**
     * Get url of the track.
     *
     * @return the url of the track
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get id of the requester who ordered the track.
     *
     * @return id of the requester who ordered the track
     */
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