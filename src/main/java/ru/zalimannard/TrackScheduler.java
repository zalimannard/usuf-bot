package ru.zalimannard;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import ru.zalimannard.track.Track;

import java.io.IOException;
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
            play(1);
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
            if (number < currentTrackNumber) {
                ++currentTrackNumber;
            } else if (playlist.size() == 1) {
                play(1);
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
            play(number);
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
            if (playlist.size() == 0) {
                play(0);
            } else if (number == currentTrackNumber) {
                play(number);
            } else if (number < currentTrackNumber) {
                --currentTrackNumber;
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
     * Play.
     *
     * @param track the track
     */
    public void play(AudioTrack track) {
        player.startTrack(track, true);
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

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (currentTrackNumber != playlist.size()) {
            play(currentTrackNumber + 1);
        } else {
            play(0);
        }
    }

    private void play(int number) {
        // TODO Make it possible to run tests without changing the code
        if ((number >= 1) && (number <= playlist.size())) {
            currentTrackNumber = number;
            try {
                PlayerManagerManager.getInstance().getPlayerManager(guild.getId()).loadAndPlay(guild,
                        playlist.get(number - 1).getTrackFile().getAbsolutePath());
            } catch (IOException e) {
                play(number + 1);
            }
        } else {
            playlist.clear();
            currentTrackNumber = 0;
            guild.getAudioManager().closeAudioConnection();
        }
    }
}
