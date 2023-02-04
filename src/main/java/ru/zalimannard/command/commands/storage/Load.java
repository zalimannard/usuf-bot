package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;
import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Load extends Command {
    public Load() {
        super(
                new ArrayList<>(Arrays.asList("load")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "№",
                                Pattern.compile("[0-9]+")
                        )
                )),
                "Запустить указанную сохранённую очередь",
                new ArrayList<>(Arrays.asList(
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        QueueRepository repository = new QueueRepository();

        try {
            QueueEntity queue = repository.get(member.getGuild().getId(), Integer.parseInt(textArgument) - 1);
            TrackLoader trackLoader = new TrackLoader();
            scheduler.clear();
            for (int i = 0; i < queue.size(); ++i) {
                ArrayList<Track> tracks = trackLoader.getTracks(queue.getTrackEntity(i).getUrl(), "0");
                if (tracks.size() > 0) {
                    scheduler.insert(tracks.get(0));
                    AudioManager audioManager = member.getGuild().getAudioManager();
                    audioManager.openAudioConnection(member.getVoiceState().getChannel());
                }
            }
            messageSender.sendQueue(scheduler, scheduler.getCurrentTrackNumber() - 1,
                    scheduler.getCurrentTrackNumber() + 10);

        } catch (IllegalArgumentException e) {
            messageSender.sendError(e.getMessage());
        } catch (IOException e) {
            messageSender.sendError("Ошибка при загрузке очереди");
        }
    }
}