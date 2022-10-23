package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackInfo;

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
     * @return trackInfo list
     */
    ArrayList<TrackInfo> search();

    /**
     * Gets all tracks by the link. It can be either one or several.
     *
     * @return track list
     */
    ArrayList<Track> getTracksByUrl();

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
