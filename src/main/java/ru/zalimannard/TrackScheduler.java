package ru.zalimannard;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import net.dv8tion.jda.api.entities.Guild;
import ru.zalimannard.track.Track;

import java.util.LinkedList;

/**
 * The TrackScheduler class will be responsible for the order of playback.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer mPlayer;
    private final Guild mGuild;

    /**
     * Instantiates a new Track scheduler.
     *
     * @param player the AudioPlayer
     * @param guild  the guild
     */
    public TrackScheduler(AudioPlayer player, Guild guild) {
        mPlayer = player;
        mGuild = guild;
    }

    /**
     * Insert a new track into the playlist.
     *
     * @param number the number after which to insert the track
     * @param track  the track to insert
     */
    public void insert(int number, Track track) {

    }

    /**
     * Play the track at the specified number.
     *
     * @param number the number
     */
    public void jump(int number) {

    }

    /**
     * Remove a track from a playlist by number.
     *
     * @param number the number
     */
    public void remove(int number) {

    }

    /**
     * Gets current track.
     *
     * @return the current track
     */
    public Track getCurrentTrack() {
        return null;
    }

    /**
     * Get a track by number.
     *
     * @param number the number
     * @return the track
     */
    public Track getTrack(int number) {
        return null;
    }

    /**
     * Gets current track number.
     *
     * @return the current track number
     */
    public int getCurrentTrackNumber() {
        return 0;
    }

    /**
     * Gets playlist size.
     *
     * @return the playlist size
     */
    public int getPlaylistSize() {
        return 0;
    }
}
