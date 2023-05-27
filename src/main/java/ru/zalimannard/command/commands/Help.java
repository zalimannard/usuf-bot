package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Help extends Command {
    public Help() {
        super(
                new ArrayList<>(Arrays.asList("help", "h")),
                new ArrayList<>(List.of(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        )
                )),
                "Открыть это меню",
                new ArrayList<>(List.of())
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            messageSender.sendHelp();
        }
    }
}