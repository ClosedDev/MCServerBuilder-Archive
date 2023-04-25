package io.github.closeddev;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class LangManager {
    private static JSONObject langData;

    static {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(Main.MCSBPath + "/lang.json");
            langData = (JSONObject) parser.parse(reader);
            reader.close();
        } catch (IOException | ParseException e) {
            Logger.log(e.toString(), 1);
        }
    }

    public static String getText(String key, String lang) {
        JSONObject used = (JSONObject) langData.get(lang);
        return used.get(key).toString();
    }
}