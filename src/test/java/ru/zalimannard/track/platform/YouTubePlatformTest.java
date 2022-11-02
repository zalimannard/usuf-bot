package ru.zalimannard.track.platform;

import org.junit.jupiter.api.Test;
import ru.zalimannard.track.Track;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class YouTubePlatformTest {

    @Test
    void isFromThisPlatform_youtubeDotComUrl_true() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String standardVideoUrl = "https://youtube.com/watch?v=I8iYWNs_Tik";

        assertTrue(youTubePlatform.isFromThisPlatform(standardVideoUrl));
    }

    @Test
    void isFromThisPlatform_youtuDotBeUrl_true() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String youtuDotBeVideoUrl = "https://youtu.be/I8iYWNs_Tik";

        assertTrue(youTubePlatform.isFromThisPlatform(youtuDotBeVideoUrl));
    }

    @Test
    void isFromThisPlatform_wwwDotYoutubeDotCom_true() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String wwwVideoUrl = "https://www.youtube.com/watch?v=I8iYWNs_Tik";

        assertTrue(youTubePlatform.isFromThisPlatform(wwwVideoUrl));
    }

    @Test
    void isFromThisPlatform_emptyString_false() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String emptyString = "";

        assertFalse(youTubePlatform.isFromThisPlatform(emptyString));
    }

    @Test
    void isFromThisPlatform_null_false() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String nullUrl = null;

        assertFalse(youTubePlatform.isFromThisPlatform(nullUrl));
    }

    @Test
    void isFromThisPlatform_spaceString_false() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String emptyString = "    ";

        assertFalse(youTubePlatform.isFromThisPlatform(emptyString));
    }

    @Test
    void isFromThisPlatform_youtubeDotRuUrl_false() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String incorrectVideoUrl = "https://youtube.ru/watch?v=I8iYWNs_Tik";

        assertFalse(youTubePlatform.isFromThisPlatform(incorrectVideoUrl));
    }

    @Test
    void isFromThisPlatform_httpYoutuDotBeUrl_true() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String incorrectVideoUrl = "http://youtube.com/watch?v=I8iYWNs_Tik";

        assertTrue(youTubePlatform.isFromThisPlatform(incorrectVideoUrl));
    }

    @Test
    void isFromThisPlatform_rutubeUrl_false() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String rutubeVideoUrl = "https://rutube.ru/video/4a0e63c4eb5ed4034f6eb2d2e6aaf65d/";

        assertFalse(youTubePlatform.isFromThisPlatform(rutubeVideoUrl));
    }

    @Test
    void search_runningInThe90s_nonEmptyList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String request = "Running in the 90s";

        ArrayList<Track> tracks = youTubePlatform.search(request);

        assertNotEquals(0, tracks.size());
    }

    @Test
    void search_runningInThe90s_nonEmptyTrackInfoList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String request = "Running in the 90s";

        ArrayList<Track> tracks = youTubePlatform.search(request);

        assertNotEquals(0, tracks.size());
    }

    @Test
    void search_emptyString_emptyList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String request = "";

        ArrayList<Track> tracks = youTubePlatform.search(request);

        assertEquals(0, tracks.size());
    }

    @Test
    void search_spaceString_emptyList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String request = "    ";

        ArrayList<Track> tracks = youTubePlatform.search(request);

        assertEquals(0, tracks.size());
    }

    @Test
    void getTracksByUrl_simpleVideoUrl_trackListSingleElement() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/watch?v=I8iYWNs_Tik";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(1, tracks.size());
    }

    @Test
    void getTracksByUrl_videoUrlWithTime_trackListSingleElement() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtu.be/I8iYWNs_Tik?t=1";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(1, tracks.size());
    }

    @Test
    void getTracksByUrl_videoUrlWithAbChannel_trackListSingleElement() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://www.youtube.com/watch?v=Gx7OL52qx-c&ab_channel=Arthas.mp4";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(1, tracks.size());
    }

    @Test
    void getTracksByUrl_incorrectVideoUrl_emptyTrackList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://www.youtube.com/watch?v=Gx7OL5FFFFFFFFFFFFFFFFF++2qx-c&ab_channel=Arthas.mp4";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(0, tracks.size());
    }

    @Test
    void getTracksByUrl_singleVideoPlaylistUrl_oneTrackInList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/playlist?list=PLl-U4gPoKWcfPzKP71ivan4F2WZRmCpgl";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(1, tracks.size());
    }

    @Test
    void getTracksByUrl_threeVideosPlaylistUrl_threeTracksInList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/playlist?list=PLl-U4gPoKWcdl4hY7F_MD2-f1lAkIabZi";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(3, tracks.size());
    }

    @Test
    void getTracksByUrl_threeVideosLinkOnlyPlaylistUrl_threeTracksInList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/playlist?list=PLl-U4gPoKWccv5m8rfTri0JgJl6kmYhAq";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(3, tracks.size());
    }

    @Test
    void getTracksByUrl_incorrectPlaylistUrl_emptyTrackList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/playlist?list=PLl-U4gPoKWSSSSSSSSSSSSSSSSSccv5m8rfTri0JgJl6kmYhAq";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(0, tracks.size());
    }

    @Test
    void getTracksByUrl_18pVideoUrl_emptyTrackList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtu.be/FmK-jkQOeKY";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(0, tracks.size());
    }

    @Test
    void getTracksByUrl_threeVideosWith18pVideoPlaylistUrl_nonEmptyTrackList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/playlist?list=PLl-U4gPoKWccwhMCYqeYRlbfbERXrDJbm";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(3, tracks.size());
    }

    @Test
    void getTracksByUrl_streamUrl_emptyTrackList() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://www.youtube.com/watch?v=jfKfPfyJRdk";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");

        assertEquals(0, tracks.size());
    }

    @Test
    void download_standardVideo_fileDownloaded() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/watch?v=I8iYWNs_Tik";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");
        assertNotEquals(0, tracks.size());

        Track track = tracks.get(0);

        assertNotNull(track.getTrackFile());
    }

    @Test
    void download_notAvailableForDownloadVideo_nullVideoFile() {
        Track track = new Track(null, null, null, "https://youtube.com/watch?v=I8iYWNs_Tikarstarstars", null);

        assertNull(track.getTrackFile());
    }

    @Test
    void getThumbnailUrl_standardVideo_nonNullUrl() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/watch?v=I8iYWNs_Tik";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");
        assertNotEquals(0, tracks.size());

        Track track = tracks.get(0);

        assertNotNull(youTubePlatform.getThumbnailUrl(track));
    }

    @Test
    void getImageUrl_standardVideo_nonNullUrl() {
        YouTubePlatform youTubePlatform = new YouTubePlatform();
        String url = "https://youtube.com/watch?v=I8iYWNs_Tik";

        ArrayList<Track> tracks = youTubePlatform.getTracksByUrl(url, "0");
        assertNotEquals(0, tracks.size());

        Track track = tracks.get(0);

        assertNotNull(youTubePlatform.getImageUrl(track));
    }
}