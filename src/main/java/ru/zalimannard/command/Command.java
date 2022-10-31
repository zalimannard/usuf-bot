package ru.zalimannard.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.MessageSender;
import ru.zalimannard.PlayerManagerManager;
import ru.zalimannard.TrackScheduler;

import java.util.ArrayList;
import java.util.regex.Matcher;

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
        for (Requirement requirement : requirements) {
            switch (requirement) {
                case REQUESTER_IN_THE_VOICE_CHANNEL:
                    if (!member.getVoiceState().inAudioChannel()) {
                        getMessageSender(member.getGuild())
                                .sendError("Команду можно вызвать только из голосового канала");
                        return;
                    }
                    break;
                case BOT_IN_THE_VOICE_CHANNEL:
                    if (!member.getGuild().getAudioManager().isConnected()) {
                        getMessageSender(member.getGuild())
                                .sendError("Бот должен быть в голосовом канале");
                        return;
                    }
                    break;
            }
        }

        textArgument = textArgument.trim();
        int wrongArgumentCount = 0;
        for (Argument argument : getArguments()) {
            if (!argument.getPattern().matcher(textArgument).matches()) {
                ++wrongArgumentCount;
            }
        }
        if (wrongArgumentCount == getArguments().size()) {
            getMessageSender(member.getGuild())
                    .sendError("Неверные аргументы");
            return;
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
                if (commandName.length() != trimmedText.length()) {
                    if (trimmedText.charAt(commandName.length()) == ' ') {
                        return commandName;
                    }
                } else {
                    return commandName;
                }
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

    /**
     * Gets message sender.
     *
     * @param guild the guild
     * @return the message sender
     */
    protected MessageSender getMessageSender(Guild guild) {
        return PlayerManagerManager.getInstance().getMessageSender(guild.getId());
    }

    /**
     * Gets track scheduler.
     *
     * @param guild the guild
     * @return the track scheduler
     */
    protected TrackScheduler getTrackScheduler(Guild guild) {
        return PlayerManagerManager.getInstance()
                .getPlayerManager(guild.getId()).getMusicManager(guild).getScheduler();
    }
}
