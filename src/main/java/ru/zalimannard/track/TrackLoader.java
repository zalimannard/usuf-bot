package ru.zalimannard.track;

import ru.zalimannard.track.platform.Platform;
import ru.zalimannard.track.platform.YouTubePlatform;

import java.util.ArrayList;

public class TrackLoader {
    private ArrayList<Platform> platforms = new ArrayList<>();

    public TrackLoader() {
        platforms.add(new YouTubePlatform());
    }

    public void download(Track track) {
        for (Platform platform : platforms) {
            if (platform.isFromThisPlatform(track.getUrl())) {
                platform.download(track);
            }
        }
    }

    public ArrayList<Track> getTracksByUrl(String url, String requesterId) {
        for (Platform platform : platforms) {
            if (platform.isFromThisPlatform(url)) {
                return platform.getTracksByUrl(url, requesterId);
            }
        }
        return new ArrayList<>();
    }
}
