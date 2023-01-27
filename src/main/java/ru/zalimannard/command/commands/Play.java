package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import ru.zalimannard.Utils;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;
import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackLoader;
import ru.zalimannard.track.platform.YouTubePlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Play extends Command {
    public Play() {
        super(
                new ArrayList<>(Arrays.asList("play", "p")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "URL",
                                Pattern.compile("( *(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])+")
                        ),
                        new Argument(
                                "Запрос",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=~_|!:,.;(){}<>\\[\\]\"'-]+")
                        )
                )),
                "Добавить трек(и) в конец очереди",
                new ArrayList<>(Arrays.asList(
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            AudioManager audioManager = member.getGuild().getAudioManager();
            TrackLoader trackLoader = new TrackLoader();
            String[] textArguments = textArgument.split(" ");
            ArrayList<Track> tracksToAdd = new ArrayList<>();
            for (String textArgumentsPart : textArguments) {
                if (textArgumentsPart.trim().length() > 0) {
                    tracksToAdd.addAll(trackLoader.getTracks(textArgumentsPart, member.getId()));
                }
            }
            if (tracksToAdd.size() == 0) {
                // Невозможно добавить трек
                getMessageSender(member.getGuild()).sendError("Ничего не добавлено");
            } else if (tracksToAdd.size() == 1) {
                // Добавление одного трека
                getTrackScheduler(member.getGuild()).insert(tracksToAdd.get(0));
                getMessageSender(member.getGuild()).sendTrackAdded(tracksToAdd.get(0), getTrackScheduler(member.getGuild()).getPlaylistSize(), Utils.calculateTimeToTrack(getTrackScheduler(member.getGuild()), getTrackScheduler(member.getGuild()).getPlaylistSize()));
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } else {
                // Добавление нескольких треков
                getMessageSender(member.getGuild()).sendMessage("Добавлено " + tracksToAdd.size() + " трека(ов)");
                for (Track trackToAdd : tracksToAdd) {
                    getTrackScheduler(member.getGuild()).insert(trackToAdd);
                }
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            }
        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            AudioManager audioManager = member.getGuild().getAudioManager();
            YouTubePlatform youTubePlatform = new YouTubePlatform();
            try {
                ArrayList<Track> tracksFound = youTubePlatform.search(textArgument);
                Track trackToAdd = new Track(tracksFound.get(0), member.getId());

                getTrackScheduler(member.getGuild()).insert(trackToAdd);
                getMessageSender(member.getGuild()).sendTrackAdded(trackToAdd, getTrackScheduler(member.getGuild()).getPlaylistSize(), Utils.calculateTimeToTrack(getTrackScheduler(member.getGuild()), getTrackScheduler(member.getGuild()).getPlaylistSize()));
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } catch (Exception e) {
                getMessageSender(member.getGuild()).sendError("Упс, запрос не сработал. Попробуйте по-другому");
            }
        }
    }
}
