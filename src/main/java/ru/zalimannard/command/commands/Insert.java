package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import ru.zalimannard.MessageSender;
import ru.zalimannard.TrackScheduler;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;
import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackLoader;
import ru.zalimannard.track.platform.YouTubePlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Insert extends Command {
    public Insert() {
        super(
                new ArrayList<>(Arrays.asList("insert", "in")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "URL",
                                Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                        ),
                        new Argument(
                                "№ URL",
                                Pattern.compile("[0-9]+ (https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                        ),
                        new Argument(
                                "№ Запрос",
                                Pattern.compile("[0-9]+[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=~_|!:,.;(){}<>\\[\\]\"'-]+")
                        ),
                        new Argument(
                                "Запрос",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=~_|!:,.;(){}<>\\[\\]\"'-]+")
                        )
                )),
                "Вставить трек по ссылке/запросу после текущего/указанного трека",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        TrackScheduler scheduler = getTrackScheduler(member.getGuild());
        MessageSender messageSender = getMessageSender(member.getGuild());
        AudioManager audioManager = member.getGuild().getAudioManager();
        TrackLoader trackLoader = new TrackLoader();

        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            ArrayList<Track> tracksToAdd = new ArrayList<>();
            if (textArgument.trim().length() > 0) {
                tracksToAdd.addAll(trackLoader.getTracks(textArgument, member.getId()));
            }
            if (tracksToAdd.size() == 0) {
                // Невозможно добавить трек
                getMessageSender(member.getGuild()).sendError("Ничего не добавлено");
            } else if (tracksToAdd.size() == 1) {
                // Добавление одного трека
                scheduler.insert(scheduler.getCurrentTrackNumber(), tracksToAdd.get(0));
                messageSender.sendTrackAdded(scheduler, scheduler.getCurrentTrackNumber() + 1);
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } else {
                // Добавление нескольких треков
                for (int i = 0; i < tracksToAdd.size(); ++i) {
                    scheduler.insert(scheduler.getCurrentTrackNumber() + i, tracksToAdd.get(0));
                }
                messageSender.sendMessage("Добавлено " + tracksToAdd.size() + " трека(ов)");
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            }

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            int numberBefore = Integer.parseInt(textArgument.split(" ")[0]);
            String url = textArgument.split(" ")[1];
            if ((numberBefore >= 0) && (numberBefore <= scheduler.getPlaylistSize())) {
                ArrayList<Track> tracksToAdd = new ArrayList<>();
                if (url.trim().length() > 0) {
                    tracksToAdd.addAll(trackLoader.getTracks(url, member.getId()));
                }
                if (tracksToAdd.size() == 0) {
                    // Невозможно добавить трек
                    getMessageSender(member.getGuild()).sendError("Ничего не добавлено");
                } else if (tracksToAdd.size() == 1) {
                    // Добавление одного трека
                    scheduler.insert(numberBefore, tracksToAdd.get(0));
                    messageSender.sendTrackAdded(scheduler, numberBefore + 1);
                    audioManager.openAudioConnection(member.getVoiceState().getChannel());
                } else {
                    // Добавление нескольких треков
                    for (int i = 0; i < tracksToAdd.size(); ++i) {
                        scheduler.insert(numberBefore + i, tracksToAdd.get(0));
                    }
                    messageSender.sendMessage("Добавлено " + tracksToAdd.size() + " трека(ов)");
                    audioManager.openAudioConnection(member.getVoiceState().getChannel());
                }
            } else {
                messageSender.sendError("Неправильный номер для вставки. Нужно указать трек после которого вставит новый");
            }

        } else if (getArguments().get(2).getPattern().matcher(textArgument).matches()) {
            int numberBefore = Integer.parseInt(textArgument.split(" ")[0]);
            String request = textArgument.substring(textArgument.indexOf(' ') + 1).trim();
            YouTubePlatform youTubePlatform = new YouTubePlatform();

            try {
                ArrayList<Track> tracksFound = youTubePlatform.search(request);
                Track trackToAdd = new Track(tracksFound.get(0), member.getId());

                scheduler.insert(numberBefore, trackToAdd);
                messageSender.sendTrackAdded(scheduler, numberBefore + 1);
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } catch (Exception e) {
                getMessageSender(member.getGuild()).sendError("Упс, запрос не сработал. Попробуйте по-другому");
            }

        } else if (getArguments().get(3).getPattern().matcher(textArgument).matches()) {
            YouTubePlatform youTubePlatform = new YouTubePlatform();

            try {
                ArrayList<Track> tracksFound = youTubePlatform.search(textArgument);
                Track trackToAdd = new Track(tracksFound.get(0), member.getId());

                scheduler.insert(scheduler.getCurrentTrackNumber(), trackToAdd);
                messageSender.sendTrackAdded(scheduler, scheduler.getCurrentTrackNumber() + 1);
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } catch (Exception e) {
                getMessageSender(member.getGuild()).sendError("Упс, запрос не сработал. Попробуйте по-другому");
            }
        }
    }
}