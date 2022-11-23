package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;

import java.util.ArrayList;

/**
 * Interface for interacting with various platforms hosting tracks.
 */
public interface Platform {
    /**
     * @return true if the prefix matches one of the platform prefixes, otherwise false
     */
    boolean isFromThisPlatform(String url);

    /**
     * @return found tracks. If nothing is found, an empty list is returned.
     */
    ArrayList<Track> search(String request);

    /**
     * Get tracks by link. It can be either a single track or a playlist.
     *
     * @return the list of tracks received by the link. If nothing is received, an empty list is returned
     */
    ArrayList<Track> getTracksByUrl(String url, String requesterId);

    /**
     * Download the file of the specified track.
     *
     * @param track to download the file for
     */
    void download(Track track);

    /**
     * Get a url to the video thumbnail
     */
    String getThumbnailUrl(Track track);

    /**
     * Get a url to the video image
     */
    String getImageUrl(Track track);
}
