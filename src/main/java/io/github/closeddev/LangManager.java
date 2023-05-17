package io.github.closeddev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class LangManager {
    static Logger logger = LogManager.getLogger(LangManager.class);

    private static JSONObject langData;

    static {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(Main.MCSBPath + "/lang.json");
            langData = (JSONObject) parser.parse(reader);
            reader.close();
        } catch (IOException | ParseException e) {
            CrashReporter.fatal(e.toString(), logger);
        }
    }

    public static String getText(String key, String lang) {
        JSONObject used = (JSONObject) langData.get(lang);
        return used.get(key).toString();
    }

    public static JSONObject getLangList() {
        return (JSONObject) langData.get("langs");
    }
}