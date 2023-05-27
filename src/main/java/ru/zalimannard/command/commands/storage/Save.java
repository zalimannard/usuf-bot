package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.Duration;
import ru.zalimannard.Utils;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Save extends Command {
    public Save() {
        super(
                new ArrayList<>(List.of("save")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "Название",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=_|!:,.;(){}<>\\[\\]\"'-]+")
                        ),
                        new Argument(
                                "Название~Описание",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=_|!:,.;(){}<>\\[\\]\"'-]+~[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=_|!:,.;(){}<>\\[\\]\"'-]+")
                        )
                )),
                "Сохранить текущую очередь",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        QueueRepository repository = new QueueRepository();
        QueueEntity queue = null;
        Duration duration = Utils.calculateFullTime(scheduler);

        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            queue = new QueueEntity(textArgument, "", duration);
        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            queue = new QueueEntity(textArgument.split("~")[0], textArgument.split("~")[1], duration);
        }

        for (int i = 0; i < scheduler.getPlaylistSize(); ++i) {
            TrackEntity track = new TrackEntity(
                    scheduler.getTrack(i + 1).getTitle(),
                    scheduler.getTrack(i + 1).getUrl());
            queue.addTrackEntity(track);
        }

        try {
            repository.save(member.getGuild().getId(), queue);
            messageSender.sendMessage("Очередь сохранена");
        } catch (IOException e) {
            messageSender.sendError("Что-то пошло не так");
        }
    }
}