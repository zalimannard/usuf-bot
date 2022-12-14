package ru.zalimannard;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Main. Needed to launch the application.
 * @author Dmitry Kolesnikov
 * @version 1.0
 */
public class Main {
    private static Logger logger = LogManager.getRootLogger();

    /**
     * The entry point of application.
     *
     * @param args the input arguments. args[0] - prefix of the bot, args[1] - token of the bot.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            logger.fatal("Invalid count of arguments: {}", args.length);
            return;
        }

        final String prefix = args[0];
        final String token = args[1];

        logger.info("Launching a bot with prefix \"{}\" and token \"{}\"", prefix, token);
        JDABuilder.createDefault(token)
                .addEventListeners(new EventHandler(prefix))
                .setActivity(Activity.listening(prefix + "help"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }
}