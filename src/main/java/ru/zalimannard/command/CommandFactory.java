package ru.zalimannard.command;

import ru.zalimannard.command.commands.Clear;
import ru.zalimannard.command.commands.Help;
import ru.zalimannard.command.commands.Play;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommandFactory {
    private static ArrayList<Command> commands = new ArrayList<>(Arrays.asList(
            new Play(),
            new Clear(),
            new Help()
    ));

    public static Command identifyCommand(String text) {
        for (Command command : commands) {
            if (command.isThisCommand(text) != null) {
                return command;
            }
        }
        return null;
    }
}