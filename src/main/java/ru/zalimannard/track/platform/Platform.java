package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;

import java.util.ArrayList;

/**
 * Interface for interacting with various platforms hosting tracks.
 */
public interface Platform {
    /**
     * Check that the video is from this platform.
     *
     * @param url the url to check
     * @return true if the prefix matches one of the platform prefixes, otherwise false
     */
    boolean isFromThisPlatform(String url);

    /**
     * Request in the platform search.
     *
     * @param request the request
     * @return found tracks. If nothing is found, an empty list is returned.
     */
    ArrayList<Track> search(String request);

    /**
     * Get tracks by link. It can be either a single track or a playlist.
     *
     * @param url download url
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
     * Get a link to the video thumbnail
     *
     * @param track the track to get a thumbnail for
     * @return the thumbnail url
     */
    String getThumbnailUrl(Track track);

    /**
     * Get a link to the video image
     *
     * @param track the track to get a image for
     * @return the image url
     */
    String getImageUrl(Track track);
}
