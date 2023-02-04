package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DeleteSavedQueue extends Command {
    public DeleteSavedQueue() {
        super(
                new ArrayList<>(Arrays.asList("deletesavedqueue")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "№",
                                Pattern.compile("[0-9]+")
                        )
                )),
                "Удалить очередь из сохранённых",
                new ArrayList<>(Arrays.asList(
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {

        }
    }
}