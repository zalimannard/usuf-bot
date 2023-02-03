package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;

import java.util.ArrayList;

public interface Platform {

    boolean isFromThisPlatform(String url);

    ArrayList<Track> search(String request);

    ArrayList<Track> getTracksByUrl(String url, String requesterId);

    void download(Track track);

    String getThumbnailUrl(Track track);

    String getImageUrl(Track track);
}
