package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Info extends Command {
    public Info() {
        super(
                new ArrayList<>(Arrays.asList("info", "i")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        ),
                        new Argument(
                                "№",
                                Pattern.compile("[0-9]+")
                        )
                )),
                "Информация о текущем/указанном треке",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            getMessageSender(member.getGuild()).sendTrackInfo(
                    getTrackScheduler(member.getGuild()),
                    getTrackScheduler(member.getGuild()).getCurrentTrackNumber());
        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            if ((Integer.parseInt(textArgument) < 1) || (Integer.parseInt(textArgument) > getTrackScheduler(member.getGuild()).getPlaylistSize())) {
                getMessageSender(member.getGuild()).sendError("Неверный номер трека");
            } else {
                getMessageSender(member.getGuild()).sendTrackInfo(
                        getTrackScheduler(member.getGuild()),
                        Integer.parseInt(textArgument));
            }
        }
    }
}