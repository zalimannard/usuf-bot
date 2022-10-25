package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;

import java.util.ArrayList;

/**
 * The type YouTube platform.
 */
public class YouTubePlatform implements Platform{
    @Override
    public Boolean isFromThisPlatform(String url) {
        return false;
    }

    @Override
    public ArrayList<Track> search(String request) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Track> getTracksByUrl(String url) {
        return new ArrayList<>();
    }

    @Override
    public void download(Track track) {

    }

    @Override
    public String getThumbnailUrl(Track track) {
        return null;
    }

    @Override
    public String getImageUrl(Track track) {
        return null;
    }
}
