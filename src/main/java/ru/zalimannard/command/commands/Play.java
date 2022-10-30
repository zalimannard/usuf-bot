package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The command Play.
 */
public class Play extends Command {

    /**
     * Instantiates a new Play command.
     */
    public Play() {
        super(
                new ArrayList<>(Arrays.asList("play", "p")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "Url",
                                Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                        )
                )),
                "Добавить трек(и) в конец очереди",
                new ArrayList<>(Arrays.asList(
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
    }
}
