package ru.zalimannard.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.MessageSender;
import ru.zalimannard.PlayerManagerManager;
import ru.zalimannard.TrackScheduler;
import ru.zalimannard.Utils;

import java.util.ArrayList;

public abstract class Command {
    private final ArrayList<String> names;
    private final ArrayList<Argument> arguments;
    private final String description;
    private final ArrayList<Requirement> requirements;

    public Command(ArrayList<String> names, ArrayList<Argument> arguments, String description,
                   ArrayList<Requirement> requirements) {
        this.names = names;
        this.arguments = arguments;
        this.description = description;
        this.requirements = requirements;
    }

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

    public String isThisCommand(String text) {
        String russianToEnglish = Utils.russianToEnglish(text);
        String trimmedText = russianToEnglish.trim();
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

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Argument> getArguments() {
        return arguments;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    protected abstract void onExecute(Member member, String textArgument);

    protected MessageSender getMessageSender(Guild guild) {
        return PlayerManagerManager.getInstance().getMessageSender(guild.getId());
    }

    protected TrackScheduler getTrackScheduler(Guild guild) {
        return PlayerManagerManager.getInstance()
                .getPlayerManager(guild.getId()).getMusicManager(guild).getScheduler();
    }
}
