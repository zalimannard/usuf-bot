package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Loop extends Command {
    public Loop() {
        super(
                new ArrayList<>(Arrays.asList("loop", "l")),
                new ArrayList<>(List.of(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        )
                )),
                "Зациклить текущий трек",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            scheduler.setTrackLooped(!scheduler.isTrackLooped());
            if (scheduler.isTrackLooped()) {
                messageSender.sendMessage("Зацикливание трека включено");
            } else {
                messageSender.sendMessage("Зацикливание трека отключено");
            }
        }
    }
}