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
            int number = Integer.parseInt(textArgument);
            if ((number >= 1) && (number <= scheduler.getPlaylistSize())) {
                String title = scheduler.getTrack(number).getTitle();
                scheduler.remove(number);
                messageSender.sendMessage("Трек " + title + " удалён");
            } else {
                messageSender.sendError("Нет такого трека");
            }

        } else if (getArguments().get(1).getPattern().matcher(textArgument).matches()) {
            int number = Integer.parseInt(textArgument.split("-")[1]);
            if (number >= 1) {
                for (int i = number; i >= 1; --i) {
                    scheduler.remove(i);
                }
                messageSender.sendMessage("Треки 1-" + number + " удалены");
            } else {
                messageSender.sendError("Нечего удалять");
            }

        } else if (getArguments().get(2).getPattern().matcher(textArgument).matches()) {
            int number = Integer.parseInt(textArgument.split("-")[0]);
            int size = scheduler.getPlaylistSize();
            if (number <= size) {
                for (int i = size; i >= number; --i) {
                    scheduler.remove(i);
                }
                messageSender.sendMessage("Треки " + number + "-" + size + " удалены");
            } else {
                messageSender.sendError("Нечего удалять");
            }

        } else if (getArguments().get(3).getPattern().matcher(textArgument).matches()) {
            int left = Math.min(Integer.parseInt(textArgument.split("-")[0]), Integer.parseInt(textArgument.split("-")[1]));
            int right = Math.max(Integer.parseInt(textArgument.split("-")[0]), Integer.parseInt(textArgument.split("-")[1]));

            if ((left <= scheduler.getPlaylistSize()) && (right >= 1)) {
                for (int i = right; i >= left; --i) {
                    scheduler.remove(i);
                }
                messageSender.sendMessage("Треки " + left + "-" + right + " удалены");
            } else {
                messageSender.sendError("Нечего удалять");
            }
        }
    }
}