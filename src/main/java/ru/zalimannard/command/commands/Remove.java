package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.MessageSender;
import ru.zalimannard.TrackScheduler;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Remove extends Command {
    public Remove() {
        super(
                new ArrayList<>(Arrays.asList("remove", "r")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "№",
                                Pattern.compile("[0-9]+")
                        ),
                        new Argument(
                                "-№",
                                Pattern.compile("-[0-9]+")
                        ),
                        new Argument(
                                "№-",
                                Pattern.compile("[0-9]+-")
                        ),
                        new Argument(
                                "№-№",
                                Pattern.compile("[0-9]+-[0-9]+")
                        )
                )),
                "Удалить трек/треки до/треки после/указанные",
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

        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {

        } else if (getArguments().get(2).getPattern().matcher(textArgument).matches()) {

        } else if (getArguments().get(3).getPattern().matcher(textArgument).matches()) {

        }
    }
}