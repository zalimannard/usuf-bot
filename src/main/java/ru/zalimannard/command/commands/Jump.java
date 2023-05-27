package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Jump extends Command {
    public Jump() {
        super(
                new ArrayList<>(Arrays.asList("jump", "j")),
                new ArrayList<>(List.of(
                        new Argument(
                                "№",
                                Pattern.compile("[0-9]+")
                        )
                )),
                "Перейти к указанному треку",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            int number = Integer.parseInt(textArgument);
            if ((number >= 1) && (number <= scheduler.getPlaylistSize())) {
                scheduler.setTrackLooped(false);
                scheduler.jump(number);
            } else {
                messageSender.sendError("Трека с таким номером нет");
            }
        }
    }
}