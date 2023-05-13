package io.github.closeddev.Updater;

import io.github.closeddev.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class VersionManager {

    public static final String VER = "0.0.2";

    public static String getVersionStr() {
        String temp = null;
        URLConnection connection = null;
        URL url = null;
        try {
            url = new URL("https://pastebin.com/raw/uMGL35qR");
        } catch (MalformedURLException e) {
            Logger.log(e.toString(), 1);
        }
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
        } catch (Exception e) {
            Logger.log(e.toString(), 1);
        }
        return temp;
    }

    public static boolean checkUpdate(String lastVer) {
        int VER_INT = Integer.parseInt(VER.replaceAll("\\.", ""));
        int LAST_VER_INT = Integer.parseInt(lastVer.replaceAll("\\.", ""));

        return LAST_VER_INT > VER_INT;
    }
}
