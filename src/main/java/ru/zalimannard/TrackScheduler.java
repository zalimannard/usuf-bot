package ru.zalimannard;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import ru.zalimannard.track.Track;

import java.util.LinkedList;

/**
 * The TrackScheduler class will be responsible for the order of playback.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final Guild guild;
    private final LinkedList<Track> playlist = new LinkedList<>();
    private int currentTrackNumber = 0;
    private boolean isTrackLooped = false;
    private boolean isQueueLooped = false;

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
     * @param track the track to insert
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
            if (playlist.size() == 1) {
                jump(1);
            } else if (number < currentTrackNumber) {
                ++currentTrackNumber;
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
            if (!isTrackLooped) {
                --number;
            }
            finishTrack();
        }
    }

    /**
     * Remove a track from a playlist by number.
     *
     * @param number the number
     */
    public void remove(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            if (playlist.get(number - 1).isDownloaded()) {
                playlist.get(number - 1).getTrackFile().delete();
            }
            playlist.remove(number - 1);
            if (number <= currentTrackNumber) {
                --currentTrackNumber;
            }
            if (number == currentTrackNumber) {
                finishTrack();
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

    /**
     * Is track looped boolean.
     *
     * @return true if the track is looped. Otherwise - false
     */
    public boolean isTrackLooped() {
        return isTrackLooped;
    }

    /**
     * Sets track looped.
     *
     * @param trackLooped the track looped
     */
    public void setTrackLooped(boolean trackLooped) {
        isTrackLooped = trackLooped;
    }

    /**
     * Is queue looped boolean.
     *
     * @return true if the queue is looped. Otherwise - false
     */
    public boolean isQueueLooped() {
        return isQueueLooped;
    }

    /**
     * Sets queue looped.
     *
     * @param queueLooped the queue looped
     */
    public void setQueueLooped(boolean queueLooped) {
        isQueueLooped = queueLooped;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (isTrackLooped) {
            play(currentTrackNumber);
        } else if ((isQueueLooped) && (currentTrackNumber == playlist.size())) {
            play(1);
        } else if (currentTrackNumber != playlist.size()) {
            play(currentTrackNumber + 1);
        } else {
            play(0);
        }
    }

    private void play(int number) {
        System.out.println("number: " + number);
        if ((number >= 1) && (number <= playlist.size())) {
            currentTrackNumber = number;
            PlayerManagerManager.getInstance().getPlayerManager(guild.getId()).loadAndPlay(guild,
                    playlist.get(currentTrackNumber - 1).getTrackFile().getAbsolutePath());
        } else {
            for (int i = playlist.size(); i >= 1; --i) {
                remove(i);
            }
            currentTrackNumber = 0;
            isTrackLooped = false;
            isQueueLooped = false;
            guild.getAudioManager().closeAudioConnection();
        }
    }

    /**
     * Gets current track time position.
     *
     * @return the current track time position
     */
    public Time getCurrentTrackTimePosition() {
        return new Time(player.getPlayingTrack().getPosition());
    }

    /**
     * Sets current track time position.
     *
     * @param time the time
     */
    public void setCurrentTrackTimePosition(Time time) {
        player.getPlayingTrack().setPosition(time.getMilliseconds());
    }

    private void finishTrack() {
        long currentTrackDurationMs = player.getPlayingTrack().getDuration();
        setCurrentTrackTimePosition(new Time(currentTrackDurationMs - 1));
    }
}
