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
                                "Запрос",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=~_|!:,.;(){}<>\\[\\]\"'-]+")
                        ),
                        new Argument(
                                "№ Запрос",
                                Pattern.compile("[0-9]+ [ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=~_|!:,.;(){}<>\\[\\]\"'-]+")
                        )
                )),
                "Вставить трек по ссылке/запросу после текущего/указанного трека",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL
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