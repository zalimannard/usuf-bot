package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Load extends Command {
    public Load() {
        super(
                new ArrayList<>(Arrays.asList("load")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "№",
                                Pattern.compile("[0-9]+")
                        )
                )),
                "Запустить указанную сохранённую очередь",
                new ArrayList<>(Arrays.asList(
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {

        }
    }
}