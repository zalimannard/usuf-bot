package ru.zalimannard;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    public static void main(String[] args) {
        final String prefix = System.getenv("PREFIX");
        final String token = System.getenv("TOKEN");

        JDABuilder.createDefault(token)
                .addEventListeners(new EventHandler(prefix))
                .setActivity(Activity.listening(prefix + "help"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .build();
    }
}