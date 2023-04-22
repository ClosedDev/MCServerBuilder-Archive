package io.github.closeddev;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
ㅁㄴㅇㄻㅊㅍㅁㄴㅇㅍㅊ

    public static void writeJSON(String filepath, JSONObject jsonObject) throws IOException {
        FileWriter savefile = new FileWriter(filepath);
        savefile.write(jsonObject.toJSONString());
        savefile.flush();
        savefile.close();
    }
}
