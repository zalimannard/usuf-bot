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
                                " ",
                                Pattern.compile(" *")
                        ),
                        new Argument(
                                "*кол-во*",
                                Pattern.compile("[0-9]+")
                        ),
                        new Argument(
                                "b *кол-во*",
                                Pattern.compile("b [0-9]+")
                        ),
                        new Argument(
                                "all",
                                Pattern.compile("all")
                        )
                )),
                "Показать 10 треков / n треков / n треков до текущего / всю очередь",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            messageSender.sendQueue(scheduler, scheduler.getCurrentTrackNumber() - 1,
                    scheduler.getCurrentTrackNumber() + 10);

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            int number = Integer.parseInt(textArgument);
            messageSender.sendQueue(scheduler, scheduler.getCurrentTrackNumber() - 1,
                    scheduler.getCurrentTrackNumber() + number);

        } else if (getArguments().get(2).getPattern().matcher(textArgument).matches()) {
            int number = Integer.parseInt(textArgument.split(" ")[1]);
            messageSender.sendQueue(scheduler, scheduler.getCurrentTrackNumber() - number,
                    scheduler.getCurrentTrackNumber() + 1);

        } else if (getArguments().get(3).getPattern().matcher(textArgument).matches()) {
            messageSender.sendQueue(scheduler, 1, scheduler.getPlaylistSize());
        }
    }
}