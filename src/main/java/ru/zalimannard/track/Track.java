package ru.zalimannard.track;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * The type Track.
 */
public class Track {
    private final File directory;
    private final TrackInfo trackInfo;
    private final String requesterId;
    private File trackFile;

    /**
     * Instantiates a new Track.
     *
     * @param trackInfo   the track info
     * @param requesterId the requester id
     * @throws IOException the io exception
     */
    public Track(TrackInfo trackInfo, String requesterId) throws IOException {
        this.directory = Files.createTempDirectory("Video").toFile();;
        this.trackInfo = trackInfo;
        this.requesterId = requesterId;
    }

    /**
     * Gets track info.
     *
     * @return the track info
     */
    public TrackInfo getTrackInfo() {
        return trackInfo;
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
     */
    public File getTrackFile() {
        return trackFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(getTrackInfo(), track.getTrackInfo()) && Objects.equals(getRequesterId(), track.getRequesterId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrackInfo(), getRequesterId());
    }

    @Override
    public String toString() {
        return "Track{" +
                "directory=" + directory +
                ", trackInfo=" + trackInfo +
                ", requesterId='" + requesterId + '\'' +
                ", trackFile=" + trackFile +
                '}';
    }
}