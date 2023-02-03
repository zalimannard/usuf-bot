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
import ru.zalimannard.Duration;
import ru.zalimannard.track.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YouTubePlatform implements Platform {
    private final static ArrayList<String> PROTOCOLS = new ArrayList<>(Arrays.asList(
            "https://",
            "http://"
    ));
    private final static ArrayList<String> ADDRESSES = new ArrayList<>(Arrays.asList(
            "www.youtube.com/watch?v=",
            "youtube.com/watch?v=",
            "www.youtube.com/playlist?list=",
            "youtube.com/playlist?list=",
            "youtu.be/"
            ));
    private final static ArrayList<String> PARAMETER_SEPARATOR = new ArrayList<>(Arrays.asList(
            "&",
            "?"
    ));
    private final YoutubeDownloader youtubeDownloader = new YoutubeDownloader();

    public YouTubePlatform() {
    }

    @Override
    public boolean isFromThisPlatform(String url) {
        if (url == null) {
            return false;
        }
        for (String protocol : PROTOCOLS) {
            if (url.startsWith(protocol)) {
                url = url.substring(protocol.length());
            }
        }
        for (String address : ADDRESSES) {
            if (url.startsWith(address)) {
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
        if (request.trim().length() == 0) {
            return response;
        }

        RequestSearchResult requestToYoutube = new RequestSearchResult(request)
                .type(TypeField.VIDEO)
                .sortBy(SortField.RELEVANCE)
                .maxRetries(5);
        List<SearchResultVideoDetails> searchResultVideoDetails =
                youtubeDownloader.search(requestToYoutube).data().videos();

        for (SearchResultVideoDetails searchResultVideoDetail : searchResultVideoDetails) {
            response.add(new Track(
                    searchResultVideoDetail.title(),
                    searchResultVideoDetail.author(),
                    new Duration(Long.valueOf(searchResultVideoDetails.get(0).lengthSeconds() * 1000)),
                    "https://www.youtube.com/watch?v=" + searchResultVideoDetails.get(0).videoId(),
                    ""
            ));
        }

        return response;
    }

    @Override
    public ArrayList<Track> getTracksByUrl(String url, String requesterId) {
        ArrayList<Track> tracks = new ArrayList<>();
        if (url == null) {
            return tracks;
        }
        if (!isFromThisPlatform(url)) {
            return tracks;
        }

        String id = urlToId(url);
        ArrayList<String> videoIds = new ArrayList<>();
        if (isPlaylist(url)) {
            RequestPlaylistInfo requestPlaylistInfo = new RequestPlaylistInfo(id);
            // Иногда он почему-то выбрасывает исключение. 5 попыток сильно минимизируют шанс этого
            Response<PlaylistInfo> response;
            PlaylistInfo playlistInfo;
            List<PlaylistVideoDetails> videos;
            int nAttempt = 5;
            for (int attempt = 1; attempt <= nAttempt; ++attempt) {
                try {
                    response = youtubeDownloader.getPlaylistInfo(requestPlaylistInfo);
                    playlistInfo = response.data();
                    videos = playlistInfo.videos();
                    videoIds = new ArrayList<>();
                    for (PlaylistVideoDetails video : videos) {
                        videoIds.add(video.videoId());
                    }
                    break;
                } catch (NullPointerException e) {
                    if (attempt == nAttempt) {
                        return tracks;
                    }
                }
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
                        new Duration(Long.valueOf(videoDetails.lengthSeconds() * 1000L)),
                        "https://www.youtube.com/watch?v=" + videoId,
                        requesterId
                ));
            }
        }

        return tracks;
    }

    @Override
    public void download(Track track) {
        if (isFromThisPlatform(track.getUrl())) {
            VideoInfo videoInfo;
            videoInfo = getVideoInfo(urlToId(track.getUrl()));
            Format format;
            try {
                format = videoInfo.bestAudioFormat();
            } catch (NullPointerException e) {
                return;
            }
            if (format == null) {
                format = videoInfo.bestVideoWithAudioFormat();
            }
            if (format != null) {
                RequestVideoFileDownload requestFile = new RequestVideoFileDownload(format);
                track.setTrackFile(youtubeDownloader.downloadVideoFile(requestFile).data());
            }
        }
    }

    @Override
    public String getThumbnailUrl(Track track) {
        if (track != null) {
            if (isFromThisPlatform(track.getUrl())) {
                return "https://i.ytimg.com/vi/" + urlToId(track.getUrl()) + "/default.jpg";
            }
        }
        return null;
    }

    @Override
    public String getImageUrl(Track track) {
        if (track != null) {
            if (isFromThisPlatform(track.getUrl())) {
                return "https://i.ytimg.com/vi/" + urlToId(track.getUrl()) + "/hqdefault.jpg";
            }
        }
        return null;
    }

    private String urlToId(String url) {
        for (String protocol : PROTOCOLS) {
            if (url.contains(protocol)) {
                url = url.substring(protocol.length());
            }
        }
        for (String address : ADDRESSES) {
            if (url.contains(address)) {
                url = url.substring(address.length());
            }
        }
        for (String parameter : PARAMETER_SEPARATOR) {
            if (url.contains(parameter)) {
                url = url.substring(0, url.indexOf(parameter));
            }
        }
        return url;
    }

    private boolean isDownloadable(String videoId) {
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

    private boolean isPlaylist(String url) {
        return url.contains("playlist");
    }
}
