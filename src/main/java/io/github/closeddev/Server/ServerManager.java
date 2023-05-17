package io.github.closeddev.Server;

import io.github.closeddev.CrashReporter;
import io.github.closeddev.Main;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ServerManager {
    static Logger logger = LogManager.getLogger(ServerManager.class);

    public static boolean isServerSet() {
        File f = new File(Main.PROGRAM_PATH + "/world");
        return f.isDirectory();
    }

    public static void editProperties(String path, String key, Object value) {
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration(path);
            conf.setProperty(key, value);
            conf.save();
        } catch (ConfigurationException e) {
            CrashReporter.fatal(e.toString(), logger);
        }
    }
}
