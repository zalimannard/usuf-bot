package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;

import java.util.ArrayList;

/**
 * The interface Platform.
 */
public interface Platform {
    /**
     * Check that the video is from this platform.
     *
     * @param url the url
     * @return true if the video is from this platform, otherwise false
     */
    Boolean isFromThisPlatform(String url);

    /**
     * Requests videos from the platform
     *
     * @param request the request
     * @return trackInfo list
     */
    ArrayList<Track> search(String request);

    /**
     * Gets all tracks by the link. It can be either one or several.
     *
     * @param url the url
     * @return track list
     */
    ArrayList<Track> getTracksByUrl(String url);

    /**
     * Adds a file with the contents of the track to an already existing track.
     *
     * @param track to download
     */
    void download(Track track);

    /**
     * Gets a link to a small preview of the video
     *
     * @param track the track
     * @return the thumbnail url
     */
    String getThumbnailUrl(Track track);

    /**
     * Gets a link to the video preview
     *
     * @param track the track
     * @return the image url
     */
    String getImageUrl(Track track);
}
