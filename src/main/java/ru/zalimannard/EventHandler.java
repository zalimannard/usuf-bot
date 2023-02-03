package ru.zalimannard;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.CommandFactory;

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

    public String getPrefix() {
        return prefix;
    }
}
