package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Skip extends Command {
    public Skip() {
        super(
                new ArrayList<>(Arrays.asList("skip", "s")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        )
                )),
                "Пропустить текущий трек",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            scheduler.setTrackLooped(false);
            if (scheduler.getCurrentTrackNumber() < scheduler.getPlaylistSize()) {
                scheduler.jump(scheduler.getCurrentTrackNumber() + 1);
            } else if ((scheduler.getCurrentTrackNumber() == scheduler.getPlaylistSize())
                    && (scheduler.isQueueLooped())) {
                scheduler.jump(1);
            } else {
                scheduler.clear();
            }
        }
    }
}