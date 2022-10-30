package ru.zalimannard.command;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommandFactory {
    private static ArrayList<Command> commands = new ArrayList<>(Arrays.asList(
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