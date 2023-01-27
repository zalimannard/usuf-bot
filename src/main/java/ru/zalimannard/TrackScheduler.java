package ru.zalimannard;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import ru.zalimannard.track.Track;

import java.util.LinkedList;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final Guild guild;
    private final LinkedList<Track> playlist = new LinkedList<>();
    private int currentTrackNumber = 0;
    private boolean isTrackLooped = false;
    private boolean isQueueLooped = false;

    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
    }

    /**
     * Insert a new track to the end of the playlist.
     */
    public void insert(Track track) {
        playlist.add(track);
        if (playlist.size() == 1) {
            play(1);
        }
    }

    /**
     * Insert a new track into the playlist after specified.
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

    public void jump(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            currentTrackNumber = number;
            if (!isTrackLooped) {
                --number;
            }
            finishTrack();
        }
    }

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

    public Track getCurrentTrack() {
        if (playlist.size() > 0) {
            return playlist.get(currentTrackNumber - 1);
        }
        return null;
    }

    public Track getTrack(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            return playlist.get(number - 1);
        }
        return null;
    }

    public void play(AudioTrack track) {
        player.startTrack(track, true);
    }

    public int getCurrentTrackNumber() {
        return currentTrackNumber;
    }

    public int getPlaylistSize() {
        return playlist.size();
    }

    public boolean isTrackLooped() {
        return isTrackLooped;
    }

    public void setTrackLooped(boolean trackLooped) {
        isTrackLooped = trackLooped;
    }

    public boolean isQueueLooped() {
        return isQueueLooped;
    }

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
        System.out.println("currentTrackNumber: " + currentTrackNumber);
        System.out.println(playlist);
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

    public Duration getCurrentTrackTimePosition() {
        return new Duration(player.getPlayingTrack().getPosition());
    }

    public void setCurrentTrackTimePosition(Duration duration) {
        player.getPlayingTrack().setPosition(duration.getMilliseconds());
    }

    private void finishTrack() {
        long currentTrackDurationMs = player.getPlayingTrack().getDuration();
        setCurrentTrackTimePosition(new Duration(currentTrackDurationMs - 1));
    }
}
