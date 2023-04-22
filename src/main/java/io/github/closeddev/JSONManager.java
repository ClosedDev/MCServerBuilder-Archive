package io.github.closeddev;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JSONManager {
    public static JSONObject loadJSON(String filepath) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try (Reader reader = new FileReader(filepath)) {
             jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static void writeJSON(String filepath, JSONObject jsonObject) throws IOException {
        FileWriter savefile = new FileWriter(filepath);
        savefile.write(jsonObject.toJSONString());
        savefile.flush();
        savefile.close();
    }
}
