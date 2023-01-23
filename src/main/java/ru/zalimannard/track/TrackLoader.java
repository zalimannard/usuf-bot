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

    public ArrayList<Track> getTracks(String url, String requesterId) {
        for (Platform platform : platforms) {
            if (platform.isFromThisPlatform(url)) {
                return platform.getTracksByUrl(url, requesterId);
            }
        }
        return new ArrayList<>();
    }

    public String getThumbnailUrl(Track track) {
        for (Platform platform : platforms) {
            if (platform.isFromThisPlatform(track.getUrl())) {
                return platform.getThumbnailUrl(track);
            }
        }
        return "https://via.placeholder.com/2/FF0000/000000";
    }
}
