package io.github.closeddev;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JSONManager {
    public static JSONObject loadJSON(String filepath) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try (Reader reader = new FileReader(filepath)) {
             jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            Logger.log(e.toString(), 1);
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
            Logger.log(e.toString(), 1);
        }
    }
}
