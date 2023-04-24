package io.github.closeddev.Server;

import io.github.closeddev.Main;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;

public class ServerManager {
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
            throw new RuntimeException(e);
        }
    }
}
