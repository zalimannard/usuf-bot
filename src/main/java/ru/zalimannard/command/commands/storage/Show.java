package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Show extends Command {
    public Show() {
        super(
                new ArrayList<>(Arrays.asList("show")),
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
                "Показать список сохранённых очередей/вывести 10 треков указанной",
                new ArrayList<>(Arrays.asList(
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {

        }
    }
}