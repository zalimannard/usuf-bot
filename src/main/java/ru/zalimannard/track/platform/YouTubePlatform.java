package ru.zalimannard.track.platform;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestSearchResult;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.search.SearchResultVideoDetails;
import com.github.kiulian.downloader.model.search.field.SortField;
import com.github.kiulian.downloader.model.search.field.TypeField;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import ru.zalimannard.Time;
import ru.zalimannard.track.Track;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The type YouTube platform.
 */
public class YouTubePlatform implements Platform {
    private final YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
    private final ArrayList<String> prefixes = new ArrayList<>(Arrays.asList(
            "https://www.youtube.com/",
            "https://youtu.be/",
            "https://youtube.com/"
    ));
    private final ArrayList<String> secondLevelPrefixes = new ArrayList<>(Arrays.asList(
            "watch?v=",
            "playlist?list="
    ));
    private final ArrayList<String> postfixes = new ArrayList<>(Arrays.asList(
            "&t=",
            "&list=",
            "&ab_channel=",
            "?t=",
            "?list=",
            "?ab_channel="
    ));

    public YouTubePlatform() {
    }

    @Override
    public Boolean isFromThisPlatform(String url) {
        if (url == null) {
            return false;
        }
        for (String prefix : prefixes) {
            if (url.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Track> search(String request) {
        ArrayList<Track> response = new ArrayList<>();

        if (request == null) {
            return response;
        }
        Boolean isOnlySpace = true;
        for (Integer i = 0; i < request.length(); ++i) {
            if (request.charAt(i) != ' ') {
                isOnlySpace = false;
            }
        }
        if (isOnlySpace) {
            return response;
        }

        RequestSearchResult requestToYoutube = new RequestSearchResult(request)
                .type(TypeField.VIDEO)
                .sortBy(SortField.RELEVANCE);
        List<SearchResultVideoDetails> searchResultVideoDetails =
                youtubeDownloader.search(requestToYoutube).data().videos();

        for (SearchResultVideoDetails searchResultVideoDetail : searchResultVideoDetails) {
            response.add(new Track(
                    searchResultVideoDetail.title(),
                    searchResultVideoDetail.author(),
                    new Time(Long.valueOf(searchResultVideoDetails.get(0).lengthSeconds())),
                    "https://www.youtube.com/watch?v=" + searchResultVideoDetails.get(0).videoId(),
                    ""
            ));
        }

        return response;
    }

    @Override
    public ArrayList<Track> getTracksByUrl(String url, String requesterId) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> videoIds = new ArrayList<>();

        if (url == null) {
            return tracks;
        }
        if (!isFromThisPlatform(url)) {
            return tracks;
        }

        String id = urlToId(url);
        if (isPlaylist(url)) {
            System.out.println(id);
            RequestPlaylistInfo requestPlaylistInfo = new RequestPlaylistInfo(id);
            Response<PlaylistInfo> response = youtubeDownloader.getPlaylistInfo(requestPlaylistInfo);
            PlaylistInfo playlistInfo = response.data();
            List<PlaylistVideoDetails> videos;
            try {
                 videos = playlistInfo.videos();
            } catch (NullPointerException e) {
                return tracks;
            }
            for (PlaylistVideoDetails video : videos) {
                videoIds.add(video.videoId());
            }
        } else {
            videoIds.add(id);
        }

        for (String videoId : videoIds) {
            if (isDownloadable(videoId)) {
                VideoDetails videoDetails = getVideoInfo(videoId).details();
                tracks.add(new Track(
                        videoDetails.title(),
                        videoDetails.author(),
                        new Time(Long.valueOf(videoDetails.lengthSeconds())),
                        "https://www.youtube.com/watch?v=" + videoId,
                        requesterId
                ));
            }
        }

        return tracks;
    }

    @Override
    public File download(Track track, File directory) {
        if (isFromThisPlatform(track.getUrl())) {
            VideoInfo videoInfo;
            videoInfo = getVideoInfo(urlToId(track.getUrl()));
            Format format;
            try {
                format = videoInfo.bestAudioFormat();
            } catch (NullPointerException e) {
                return null;
            }
            if (format == null) {
                format = videoInfo.bestVideoWithAudioFormat();
            }
            if (format != null) {
                RequestVideoFileDownload requestFile = new RequestVideoFileDownload(format)
                        .saveTo(directory)
                        .renameTo("video");
                return youtubeDownloader.downloadVideoFile(requestFile).data();
            }
        }
        return null;
    }

    @Override
    public String getThumbnailUrl(Track track) {
        return "https://i.ytimg.com/vi/" + urlToId(track.getUrl()) + "/default.jpg";
    }

    @Override
    public String getImageUrl(Track track) {
        return "https://i.ytimg.com/vi/" + urlToId(track.getUrl()) + "/hqdefault.jpg";
    }

    private String urlToId(String url) {
        for (String prefix : prefixes) {
            if (url.contains(prefix)) {
                url = url.substring(prefix.length());
            }
        }
        for (String prefix : secondLevelPrefixes) {
            if (url.contains(prefix)) {
                url = url.substring(prefix.length());
            }
        }
        for (String postfix : postfixes) {
            if (url.contains(postfix)) {
                url = url.substring(0, url.indexOf(postfix));
            }
        }
        return url;
    }

    public Boolean isDownloadable(String videoId) {
        try {
            VideoDetails videoDetails = getVideoInfo(urlToId("https://www.youtube.com/watch?v=" + videoId)).details();
            return videoDetails.isDownloadable() && !videoDetails.isLive();
        } catch (Exception e) {
            return false;
        }
    }

    private VideoInfo getVideoInfo(String videoId) {
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo(videoId);
        Response<VideoInfo> response = youtubeDownloader.getVideoInfo(requestVideoInfo);
        return response.data();
    }

    public Boolean isPlaylist(String url) {
        return url.contains("playlist");
    }
}
