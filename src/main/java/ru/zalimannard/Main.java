package ru.zalimannard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        if (args.length != 2) {
            logger.fatal("Invalid count of arguments: {}", args.length);
            return;
        }

        logger.info("Launching a bot with token \"{}\" and prefix \"{}\"", args[0], args[1]);
    }
}