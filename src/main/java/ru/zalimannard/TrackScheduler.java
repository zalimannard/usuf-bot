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

    public void insert(Track track) {
        insert(playlist.size(), track);
    }

    public void insert(int number, Track track) {
        if ((number >= 0) && (number <= playlist.size())) {
            playlist.add(number, track);
            if (playlist.size() == 1) {
                play(1);
            } else if (number < currentTrackNumber) {
                ++currentTrackNumber;
            } else if (number == currentTrackNumber) {
                // Неявная предзагрузка трека, если он идёт после текущего
                playlist.get(currentTrackNumber).getTrackFile();
            }
        }
    }

    public void jump(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            if (!isTrackLooped) {
                --number;
            }
            currentTrackNumber = number;
            finishTrack();
        }
    }

    public void clear() {
        isTrackLooped = false;
        isQueueLooped = false;
        while (playlist.size() > 0) {
            // Текущая дорожка удаляется последней чтобы не началось новое воспроизведение
            if (playlist.size() == 1) {
                remove(1);
            }
            else if (playlist.size() == currentTrackNumber) {
                remove(playlist.size() - 1);
            } else {
                remove(playlist.size());
            }
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
            if (number == currentTrackNumber + 1) {
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
            clear();
            isTrackLooped = false;
            isQueueLooped = false;
            PlayerManagerManager.getInstance().getMessageSender(guild.getId()).deletePreviousNowPlaying();
            guild.getAudioManager().closeAudioConnection();
        }
    }

    private void play(int number) {
        if ((number >= 1) && (number <= playlist.size())) {
            currentTrackNumber = number;
            PlayerManagerManager.getInstance().getPlayerManager(guild.getId())
                    .loadAndPlay(guild, playlist.get(currentTrackNumber - 1).getTrackFile().getAbsolutePath());
            // Сообщение о запущенном треке
            MessageSender messageSender = PlayerManagerManager.getInstance().getMessageSender(guild.getId());
            messageSender.sendNowPlaying(this);

            if (currentTrackNumber < playlist.size()) {
                // Неявная предзагрузка следующего трека
                playlist.get(currentTrackNumber).getTrackFile();
            }
        }
    }

    public Duration getCurrentTrackTimePosition() {
        return new Duration(player.getPlayingTrack().getPosition());
    }

    public void setCurrentTrackTimePosition(Duration duration) {
        player.getPlayingTrack().setPosition(duration.getMilliseconds());
    }

    private void finishTrack() {
        if (player.getPlayingTrack() != null) {
            long currentTrackDurationMs = player.getPlayingTrack().getDuration();
            setCurrentTrackTimePosition(new Duration(currentTrackDurationMs - 1));
        }
    }
}
