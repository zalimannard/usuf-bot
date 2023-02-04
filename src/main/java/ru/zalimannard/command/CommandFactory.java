package ru.zalimannard.command;

import ru.zalimannard.command.commands.*;
import ru.zalimannard.command.commands.storage.DeleteSavedQueue;
import ru.zalimannard.command.commands.storage.Load;
import ru.zalimannard.command.commands.storage.Save;
import ru.zalimannard.command.commands.storage.Show;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommandFactory {
    private static ArrayList<Command> commands = new ArrayList<>(Arrays.asList(
            new Play(),
            new Skip(),
            new Info(),
            new Jump(),
            new Queue(),
            new Insert(),
            new Remove(),
            new Prev(),
            new Rewind(),
            new Shuffle(),
            new Loop(),
            new Loopq(),
            new Clear(),
            new Save(),
            new Show(),
            new Load(),
            new DeleteSavedQueue(),
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