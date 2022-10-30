package ru.zalimannard.command;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

/**
 * The type Command.
 */
public abstract class Command {
    private final ArrayList<String> names;
    private final ArrayList<Argument> arguments;
    private final String description;
    private final ArrayList<Requirement> requirements;

    /**
     * Instantiates a new Command.
     *
     * @param names        the names
     * @param arguments    the arguments
     * @param description  the description
     * @param requirements the requirements
     */
    public Command(ArrayList<String> names, ArrayList<Argument> arguments, String description,
                   ArrayList<Requirement> requirements) {
        this.names = names;
        this.arguments = arguments;
        this.description = description;
        this.requirements = requirements;
    }

    /**
     * Execute.
     *
     * @param member       the member
     * @param textArgument the argument in the form of a single string
     */
    public void execute(Member member, String textArgument) {
        ArrayList<Requirement> requirements = getRequirements();
        for (int i = 0; i < requirements.size(); ++i) {
            switch (requirements.get(i)) {
                case BOT_IN_THE_VOICE_CHANNEL:
                    break;
                case REQUESTER_IN_THE_VOICE_CHANNEL:
                    break;
            }
        }
        onExecute(member, textArgument);
    }

    /**
     * Is this command string.
     *
     * @param text the text
     * @return null if the string does not belong to the command. Otherwise, it is a variant of the command in the current case
     */
    public String isThisCommand(String text) {
        String trimmedText = text.trim();
        for (String commandName : getNames()) {
            if (trimmedText.startsWith(commandName)) {
                return commandName;
            }
        }
        return null;
    }

    /**
     * Gets names.
     *
     * @return the names
     */
    public ArrayList<String> getNames() {
        return names;
    }

    /**
     * Gets arguments.
     *
     * @return the arguments
     */
    public ArrayList<Argument> getArguments() {
        return arguments;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets requirements.
     *
     * @return the requirements
     */
    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    /**
     * On execute.
     *
     * @param member       the member
     * @param textArgument the text argument
     */
    protected abstract void onExecute(Member member, String textArgument);
}
