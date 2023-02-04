package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;

import java.io.IOException;
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
        QueueRepository repository = new QueueRepository();
        int index = Integer.parseInt(textArgument) - 1;

        try {
            String title = repository.get(member.getGuild().getId(), index).getTitle();
            repository.remove(member.getGuild().getId(), index);
            messageSender.sendMessage("Очередь " + title + " удалена " + member.getNickname());

        } catch (IllegalArgumentException e) {
            messageSender.sendError(e.getMessage());
        } catch (IOException e) {
            messageSender.sendError("Не удалось прочитать файл");
        }
    }
}