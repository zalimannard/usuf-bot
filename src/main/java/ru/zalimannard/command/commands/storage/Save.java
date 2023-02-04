package ru.zalimannard.command.commands.storage;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Save extends Command {
    public Save() {
        super(
                new ArrayList<>(Arrays.asList("save")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "Название",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=_|!:,.;(){}<>\\[\\]\"'-]+")
                        ),
                        new Argument(
                                "Название~Описание",
                                Pattern.compile("[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=_|!:,.;(){}<>\\[\\]\"'-]+~[ a-zA-Zа-яА-ЯЁ0-9+&@#/%?=_|!:,.;(){}<>\\[\\]\"'-]+")
                        )
                )),
                "Сохранить текущую очередь",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
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