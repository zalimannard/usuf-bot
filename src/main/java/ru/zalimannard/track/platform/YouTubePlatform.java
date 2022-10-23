package ru.zalimannard.track.platform;

import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackInfo;

import java.util.ArrayList;

public class YouTubePlatform implements Platform{
    @Override
    public Boolean isFromThisPlatform(String url) {
        return null;
    }

    @Override
    public ArrayList<TrackInfo> search() {
        return null;
    }

    @Override
    public ArrayList<Track> getTracksByUrl() {
        return null;
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
