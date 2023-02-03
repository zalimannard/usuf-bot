package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Queue extends Command {
    public Queue() {
        super(
                new ArrayList<>(Arrays.asList("queue", "q")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "[n]",
                                Pattern.compile(" *")
                        ),
                        new Argument(
                                "before [n]",
                                Pattern.compile("b ")
                        ),
                        new Argument(
                                "all",
                                Pattern.compile("all")
                        )
                )),
                "Показать n (опционально) треков после/до текущего и всю очередь",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {

        } else if (getArguments().get(2).getPattern().matcher(textArgument).matches()) {

        }
    }
}