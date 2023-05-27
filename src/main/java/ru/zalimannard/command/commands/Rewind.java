package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.Duration;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Rewind extends Command {
    public Rewind() {
        super(
                new ArrayList<>(Arrays.asList("rewind", "rw")),
                new ArrayList<>(List.of(
                        new Argument(
                                "HH:MM:SS",
                                Pattern.compile("[0-9:]+")
                        )
                )),
                "Перемотать трек к указанной позиции. Точность указывайте сколько нужно",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            if (Duration.isCorrectDuration(textArgument)) {
                Duration timePosition = new Duration(textArgument);
                scheduler.setCurrentTrackTimePosition(timePosition);
            } else {
                messageSender.sendError("Время указано в неверном формате");
            }
        }
    }
}