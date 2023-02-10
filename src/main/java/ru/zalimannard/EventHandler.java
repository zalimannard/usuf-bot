package ru.zalimannard;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.CommandFactory;

import java.util.List;

public class EventHandler extends ListenerAdapter {
    private final String prefix;

    public EventHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        // hard shutdown
        if (event.getMessage().getContentRaw().equals(prefix + "hardreset")) {
            System.exit(0);
        }

        String[] messageTextList = event.getMessage().getContentRaw().split("\n");
        for (String messageText : messageTextList) {
            messageText = messageText.trim();
            if (!messageText.startsWith(getPrefix())) {
                continue;
            }

            // Update the channel for the bot's message output
            if (PlayerManagerManager.getInstance().getMessageSender(event.getGuild().getId()) == null) {
                PlayerManagerManager.getInstance().setMessageSender(event.getGuild().getId(),
                        new MessageSender(prefix, event.getMessage().getChannel(), event.getGuild()));
            } else {
                PlayerManagerManager.getInstance().getMessageSender(event.getGuild().getId())
                        .setCurrentMessageChannel(event.getMessage().getChannel());
            }

            messageText = messageText.substring(prefix.length());
            Command command = CommandFactory.identifyCommand(messageText);
            if (command != null) {
                messageText = messageText.substring(command.isThisCommand(messageText).length());
                command.execute(event.getMember(), messageText);
            }
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        List<Member> members = event.getGuild().getMembers();
        for (Member member : members) {
            if ((member.getVoiceState().inAudioChannel()) && (!member.getUser().isBot())) {
                return;
            }
        }

        PlayerManagerManager.getInstance().getPlayerManager(event.getGuild().getId()).getMusicManager(event.getGuild()).
                getScheduler().clear();
    }

    public String getPrefix() {
        return prefix;
    }
}
