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
    private LinkedList<Track> playlist = new LinkedList<>();
    private int currentTrackNumber = 0;
    private final AudioPlayer player;
    private final Guild guild;

    /**
     * Instantiates a new Track scheduler.
     *
     * @param player the AudioPlayer
     * @param guild  the guild
     */
    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
    }

    /**
     * Insert a new track to the end of the playlist.
     *
     * @param track  the track to insert
     */
    public void insert(Track track) {
        playlist.add(track);
        if (playlist.size() == 1) {
            // TODO Start playing
        }
    }

    /**
     * Insert a new track into the playlist.
     *
     * @param number the number after which to insert the track
     * @param track  the track to insert
     */
    public void insert(int number, Track track) {
        if ((number >= 0) && (number <= playlist.size())) {
            playlist.add(number, track);
            if ((number < currentTrackNumber) || (playlist.size() == 1)) {
                ++currentTrackNumber;
            }
            if (playlist.size() == 1) {
                // TODO Start playing
            }
        }
    }

    /**
     * Play the track at the specified number.
     *
     * @param number the number
     */
    public void jump(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            currentTrackNumber = number;
            // TODO Start playing
        }
    }

    /**
     * Remove a track from a playlist by number.
     *
     * @param number the number
     */
    public void remove(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            playlist.remove(number - 1);
            if (number < currentTrackNumber) {
                --currentTrackNumber;
            }
            if (number == currentTrackNumber) {
                // TODO Skip current track
            }
            if (playlist.size() == 0) {
                currentTrackNumber = 0;
            }
        }
    }

    /**
     * Gets current track.
     *
     * @return the current track
     */
    public Track getTrack() {
        if (playlist.size() > 0) {
            return playlist.get(currentTrackNumber - 1);
        }
        return null;
    }

    /**
     * Get a track by number.
     *
     * @param number the number
     * @return the track
     */
    public Track getTrack(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            return playlist.get(number - 1);
        }
        return null;
    }

    /**
     * Gets current track number.
     *
     * @return the current track number
     */
    public int getCurrentTrackNumber() {
        return currentTrackNumber;
    }

    /**
     * Gets playlist size.
     *
     * @return the playlist size
     */
    public int getPlaylistSize() {
        return playlist.size();
    }
}
