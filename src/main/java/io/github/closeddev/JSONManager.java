package io.github.closeddev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JSONManager {
    static Logger logger = LogManager.getLogger(JSONManager.class);

    public static JSONObject loadJSON(String filepath) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try (Reader reader = new FileReader(filepath)) {
             jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            CrashReporter.fatal(e.toString(), logger);
        }
        return jsonObject;
    }

    public static void writeJSON(String filepath, JSONObject jsonObject) {
        try {
            FileWriter savefile = new FileWriter(filepath);
            savefile.write(jsonObject.toJSONString());
            savefile.flush();
            savefile.close();
        } catch (IOException e) {
            CrashReporter.fatal(e.toString(), logger);
        }
    }
}
