package ru.zalimannard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.zalimannard.track.Track;

import static org.junit.jupiter.api.Assertions.*;

class TrackSchedulerTest {
    TrackScheduler trackScheduler;
    Track track = new Track("title", "author", new Duration(10L), "https://youtube.com/watch?v=I8iYWNs_Tik", "requesterId");
    @BeforeEach
    void beforeEach() {
        trackScheduler = new TrackScheduler(null, null);
    }

    @Test
    void insert_withoutArgumentToPlaylistSize0_playlistSize1() {
        trackScheduler.insert(track);

        assertEquals(1, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_withoutArgumentToPlaylistSize1_playlistSize2() {
        trackScheduler.insert(track);
        trackScheduler.insert(track);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_toPlaylistSize0_playlistSize1() {
        trackScheduler.insert(0, track);

        assertEquals(1, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_toPlaylistSize0_currentTrack1() {
        trackScheduler.insert(0, track);

        assertEquals(1, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void insert_toStartPlaylistSize1_playlistSize2() {
        trackScheduler.insert(0, track);

        trackScheduler.insert(0, track);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_toStartPlaylistSize1_currentTrack2() {
        trackScheduler.insert(0, track);

        trackScheduler.insert(0, track);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void insert_toEndPlaylistSize2CurrentTrack2_playlistSize3() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        assertEquals(3, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_toEndPlaylistSize2CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void insert_toLessThan0PlaylistSize2CurrentTrack2_playlistSize2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        trackScheduler.insert(-1, track);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_toLessThan0PlaylistSize2CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        trackScheduler.insert(-1, track);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void insert_toGreaterThanPlaylistSize2CurrentTrack2_playlistSize2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        trackScheduler.insert(trackScheduler.getPlaylistSize() + 1, track);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void insert_toGreaterThanPlaylistSize2CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        trackScheduler.insert(trackScheduler.getPlaylistSize() + 1, track);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void jump_to2PlaylistSize3CurrentTrack1_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.jump(2);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void jump_toLessThan0CurrentTrack1_currentTrack1() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.jump(-1);

        assertEquals(1, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void jump_toGreaterThanPlaylistSize3CurrentTrack1_currentTrack1() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.jump(trackScheduler.getPlaylistSize() + 1);

        assertEquals(1, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void jump_emptyPlaylist_currentTrack0() {
        trackScheduler.jump(1);

        assertEquals(0, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track1PlaylistSize3CurrentTrack2_currentTrack1() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(1);

        assertEquals(1, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track2PlaylistSize3CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(2);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track3PlaylistSize3CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(3);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track1PlaylistSize3CurrentTrack2_playlistSize2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(1);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void remove_track2PlaylistSize3CurrentTrack2_playlistSize2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(2);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void remove_track3PlaylistSize3CurrentTrack2_playlistSize2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(3);

        assertEquals(2, trackScheduler.getPlaylistSize());
    }

    @Test
    void remove_track0PlaylistSize3CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(0);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track0PlaylistSize3CurrentTrack2_playlistSize3() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(0);

        assertEquals(3, trackScheduler.getPlaylistSize());
    }

    @Test
    void remove_track4PlaylistSize3CurrentTrack2_currentTrack2() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(4);

        assertEquals(2, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track4PlaylistSize3CurrentTrack2_playlistSize3() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(trackScheduler.getPlaylistSize(), track);

        trackScheduler.remove(4);

        assertEquals(3, trackScheduler.getPlaylistSize());
    }

    @Test
    void remove_track1PlaylistSize1CurrentTrack1_currentTrack0() {
        trackScheduler.insert(0, track);

        trackScheduler.remove(1);

        assertEquals(0, trackScheduler.getCurrentTrackNumber());
    }

    @Test
    void remove_track1PlaylistSize1CurrentTrack1_playlistSize0() {
        trackScheduler.insert(0, track);

        trackScheduler.remove(1);

        assertEquals(0, trackScheduler.getPlaylistSize());
    }

    @Test
    void getTrack_playListSize0_nullReturned() {
        Track receivedTrack = trackScheduler.getTrack();

        assertNull(receivedTrack);
    }

    @Test
    void getTrack_playListSize1CurrentTrack1_nonNullTrackReturned() {
        trackScheduler.insert(0, track);

        Track receivedTrack = trackScheduler.getTrack();

        assertNotNull(receivedTrack);
    }

    @Test
    void getTrack_track0PlaylistSize3_nullReturned() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        Track receivedTrack = trackScheduler.getTrack(4);

        assertNull(receivedTrack);
    }

    @Test
    void getTrack_track4PlaylistSize3_nullReturned() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        Track receivedTrack = trackScheduler.getTrack(4);

        assertNull(receivedTrack);
    }

    @Test
    void getTrack_track2PlaylistSize3_nonNullReturned() {
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);
        trackScheduler.insert(0, track);

        Track receivedTrack = trackScheduler.getTrack(2);

        assertNotNull(receivedTrack);
    }
}