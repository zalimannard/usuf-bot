package ru.zalimannard.track;

import ru.zalimannard.Time;
import ru.zalimannard.track.platform.Platform;
import ru.zalimannard.track.platform.YouTubePlatform;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * The type Track.
 */
public class Track {
    private final String title;
    private final String author;
    private final Time duration;
    private final String url;
    private final String requesterId;
    private File directory;
    private File trackFile;

    /**
     * Instantiates a new Track.
     *
     * @param title       the title
     * @param author      the author
     * @param duration    the duration
     * @param url         the url
     * @param requesterId the requester id
     */
    public Track(String title, String author, Time duration, String url, String requesterId) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.url = url;
        this.requesterId = requesterId;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        directory.delete();
        trackFile.delete();
    }

    /**
     * Gets requester id.
     *
     * @return the requester id
     */
    public String getRequesterId() {
        return requesterId;
    }

    /**
     * Gets track file.
     *
     * @return the track file. This file is downloaded beforehand. If downloading is not possible, null is returned
     * @throws IOException the io exception
     */
    public File getTrackFile() throws IOException {
        if (trackFile == null) {
            directory = Files.createTempDirectory("Video").toFile();
            TrackLoader trackLoader = new TrackLoader();
            trackFile = trackLoader.download(this, directory);
        }
        return trackFile;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Time getDuration() {
        return duration;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
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
                ", directory=" + directory +
                ", requesterId='" + requesterId + '\'' +
                ", trackFile=" + trackFile +
                '}';
    }
}