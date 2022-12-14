package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The command Help.
 */
public class Help extends Command {

    /**
     * Instantiates a new Help command.
     */
    public Help() {
        super(
                new ArrayList<>(Arrays.asList("help", "h")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        )
                )),
                "Открыть это меню",
                new ArrayList<>(Arrays.asList(
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            getMessageSender(member.getGuild()).sendHelp();
        }
    }
}