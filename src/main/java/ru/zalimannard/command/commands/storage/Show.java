package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;

import java.io.IOException;
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
        QueueRepository repository = new QueueRepository();
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            try {
                if (repository.getAll(member.getGuild().getId()).size() == 0) {
                    messageSender.sendError("Нет сохранённых очередей");
                } else {
                    messageSender.sendSavedQueue(repository.getAll(member.getGuild().getId()));
                }
            } catch (IOException e) {
                messageSender.sendError("Ошибка при считывании");
            }

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            try {
                messageSender.sendShowSavedQueue(repository.get(
                        member.getGuild().getId(),
                        Integer.parseInt(textArgument) - 1));

            } catch (IllegalArgumentException e) {
                messageSender.sendError(e.getMessage());
            } catch (IOException e) {
                messageSender.sendError("Ошибка при считывании");
            }
        }
    }
}