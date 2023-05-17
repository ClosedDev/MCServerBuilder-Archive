package io.github.closeddev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReporter {
    public static void fatal(String log, Logger logger) {
        logger.fatal(log);
        System.exit(-1);
    }
}
