package io.github.closeddev;

import io.github.closeddev.Server.CreateServer;
import io.github.closeddev.Updater.Updater;
import io.github.closeddev.Updater.VersionManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static final String appdata = System.getenv("APPDATA");
    public static final String MCSBPath = appdata + "/MCServerBuilder";
    public static List<String> jarFiles;
    public static String PROGRAM_PATH;

    public static JSONObject settings;

    public static void main(String[] args) throws Exception {
        PROGRAM_PATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        makeDir(MCSBPath + "/");
        makeDir(MCSBPath + "/Bin");
        makeDir(MCSBPath + "/Jars");
        makeDir(MCSBPath + "/Logs");

        reloadSettings(); //설정 리로드

        String LAST_VER = VersionManager.getVersionStr();
        Boolean IsUpdateFound = false;
        if ((Boolean) settings.get("AutoUpdateCheck")) {
            IsUpdateFound = VersionManager.checkUpdate(LAST_VER);
        }
        Logger.log("MCSB v" + VersionManager.VER + " Copyright \u24d2 ClosedDev ( MIT LICENCE v2 )", 0);

        if(IsUpdateFound) {
            Logger.log("New Update Found! v" + VersionManager.VER + " -> v" + LAST_VER, 2);
            if ((Boolean) settings.get("AutoFileUpdate")) {
                Logger.log("Updating MCSB...", 2);
                Updater.updateMCSB(PROGRAM_PATH, LAST_VER);
            }
        }

        if((Boolean) settings.get("DebugMode")) {
            Logger.log("Debug Mode is Enabled!", 2);
            Updater.updateMCSB(PROGRAM_PATH, LAST_VER);
            /*String FullVersion = "1.19.4";
            Logger.log(ApiManager.getLatestBuild(FullVersion), 0);
            String bcode = ApiManager.getLatestBuild(FullVersion);
            int vcodeint = Integer.parseInt(FullVersion.replaceAll("\\.", ""));
            String vcode = null;
            if(countChar(FullVersion, '.')<2) { vcode = String.valueOf(vcodeint*10); }
            if(vcodeint>1000) {
                vcode = String.valueOf(vcodeint - 1000);
            } else {
                vcode = "0" + String.valueOf(vcodeint - 100);
            }
            Logger.log(String.valueOf(vcode), 0);
            CreateServer.createServer(FullVersion, bcode, vcode);*/
        }
        System.out.println(ApiManager.getMajorArray());
//        Logger.log(LangManager.getText("test", "ko-kr"), 0);
    }

    public static void reloadSettings() throws IOException, InterruptedException, ParseException {
        File setfile = new File(MCSBPath + "/setting.json");
        if (!setfile.isFile()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AutoUpdateCheck", true);
            jsonObject.put("Language", "en-us");
            jsonObject.put("DebugMode", false);
            jsonObject.put("AutoFileUpdate", true);
            jsonObject.put("IsInitial", true);
            JSONManager.writeJSON(MCSBPath + "/setting.json", jsonObject);
        }
        settings = JSONManager.loadJSON(MCSBPath + "/setting.json");

        File langfile = new File(MCSBPath + "/lang.json");
        if (!langfile.isFile()) {
            StringBuilder langStr = new StringBuilder();
            URL url = new URL("https://pastebin.com/raw/bDgywSwi");
            URLConnection connection = url.openConnection();


            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())))
            {
                String line;
                while ((line = in.readLine()) != null) {
                    langStr.append(line);
                }
            }
            JSONParser parser = new JSONParser();
            JSONObject langObj = (JSONObject) parser.parse(langStr.toString());
            JSONManager.writeJSON(MCSBPath + "/lang.json", langObj);
        }
    }

    public static void makeDir(String path) throws InterruptedException {
        File d = new File(path);

        if(!d.isDirectory()){
            if(!d.mkdirs()){
                Logger.log("An error occurred while creating the folder.", 1);
            }
        }
    }

    private static long countChar(String str, char ch) {
        return str.chars()
                .filter(c -> c == ch)
                .count();
    }
}
