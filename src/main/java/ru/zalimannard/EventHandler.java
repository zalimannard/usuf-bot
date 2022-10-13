package ru.zalimannard;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

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

    /**
     * Gets prefix of processed messages.
     *
     * @return the prefix of processed messages.
     */
    public String getPrefix() {
        return prefix;
    }
}
