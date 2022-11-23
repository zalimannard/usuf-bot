package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.TrackScheduler;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Prev extends Command {
    public Prev() {
        super(
                new ArrayList<>(Arrays.asList("prev", "pr")),
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
            TrackScheduler trackScheduler = getTrackScheduler(member.getGuild());
            if (trackScheduler.getCurrentTrackNumber() > 1) {
                trackScheduler.jump(trackScheduler.getCurrentTrackNumber() - 1);
                trackScheduler.setTrackLooped(false);
            } else {
                getMessageSender(member.getGuild()).sendError("Сейчас играет первый трек в очереди");
            }
        }
    }
}