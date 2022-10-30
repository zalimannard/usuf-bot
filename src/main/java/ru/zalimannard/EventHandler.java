package ru.zalimannard;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.CommandFactory;

/**
 * The type Event handler. It reacts to events that occurred in the Discord.
 * @author Dmitry Kolesnikov
 * @version 1.0
 */
public class EventHandler extends ListenerAdapter {
    private final String prefix;

    /**
     * Instantiates a new Event handler.
     *
     * @param prefix the prefix. The bot only responds to messages starting with this line.
     */
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
        }
    }

    /**
     * Gets prefix of processed messages.
     *
     * @return the prefix of processed messages.
     */
    public String getPrefix() {
        return prefix;
    }
}
