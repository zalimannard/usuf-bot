package ru.zalimannard;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.awt.*;

/**
 * The type Message sender.
 */
public class MessageSender {
    private final String commandPrefix;
    private final Color goodColor = Color.decode("#2ECC71");
    private final Color badColor = Color.decode("#FF0000");
    private MessageChannel currentMessageChannel;
    private final Guild guild;

    /**
     * Instantiates a new Message sender.
     *
     * @param commandPrefix         the command prefix
     * @param currentMessageChannel the current message channel
     * @param guild                 the guild
     */
    public MessageSender(String commandPrefix, MessageChannel currentMessageChannel, Guild guild) {
        this.commandPrefix = commandPrefix;
        setCurrentMessageChannel(currentMessageChannel);
        this.guild = guild;
    }

    /**
     * Send message.
     *
     * @param text the text
     */
    public void sendMessage(String text) {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(goodColor);
        trackAddedEmbed.setTitle(text);
        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    /**
     * Send error.
     *
     * @param text the text
     */
    public void sendError(String text) {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(badColor);
        trackAddedEmbed.setTitle(text);
        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }


    /**
     * Send help.
     */
    public void sendHelp() {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(goodColor);
        trackAddedEmbed.setTitle("Помощь");
        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    /**
     * Gets current message channel.
     *
     * @return the current message channel
     */
    public MessageChannel getCurrentMessageChannel() {
        return currentMessageChannel;
    }

    /**
     * Sets current message channel.
     *
     * @param currentMessageChannel the current message channel
     */
    public void setCurrentMessageChannel(MessageChannel currentMessageChannel) {
        this.currentMessageChannel = currentMessageChannel;
    }
}
