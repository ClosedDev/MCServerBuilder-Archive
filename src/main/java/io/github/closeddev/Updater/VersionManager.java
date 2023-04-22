package io.github.closeddev.Updater;

import io.github.closeddev.Logger;
import io.github.closeddev.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VersionManager {

    public static final String VER = "0.0.1";

    public static String getVersionStr() throws IOException, InterruptedException {
        String temp = null;
        URLConnection connection = null;
        URL url = new URL("https://pastebin.com/raw/uMGL35qR");
        try {
            connection = url.openConnection();
        } catch (Exception e) {
            Logger.log(e.toString(), 1);
        }


        assert connection != null;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())))
        {
            String line;
            while ((line = in.readLine()) != null) {
                temp = line;
            }
        }
        return temp;
    }

    public static boolean checkUpdate(String lastVer) {
        int VER_INT = Integer.parseInt(VER.replaceAll("\\.", ""));
        int LAST_VER_INT = Integer.parseInt(lastVer.replaceAll("\\.", ""));

        return LAST_VER_INT > VER_INT;
    }
}
