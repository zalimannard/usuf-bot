package ru.zalimannard;

import java.util.HashMap;
import java.util.Map;

public class PlayerManagerManager {
    private static PlayerManagerManager INSTANCE;
    private final Map<String, PlayerManager> managers = new HashMap<>();
    private final Map<String, MessageSender> senders = new HashMap<>();

    private PlayerManagerManager() {

    }

    public static PlayerManagerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManagerManager();
        }
        return INSTANCE;
    }

    public PlayerManager getPlayerManager(String guildId) {
        if (managers.get(guildId) == null) {
            managers.put(guildId, new PlayerManager());
        }
        return managers.get(guildId);
    }

    public void setMessageSender(String guildId, MessageSender messageSender) {
        senders.put(guildId, messageSender);
    }

    public MessageSender getMessageSender(String guildId) {
        return senders.get(guildId);
    }
}
