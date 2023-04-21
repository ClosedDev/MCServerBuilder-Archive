package io.github.closeddev;

import io.github.closeddev.Server.CreateServer;
import io.github.closeddev.Updater.VersionManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static final String appdata = System.getenv("APPDATA");
    public static final String MCSBPath = appdata + "/MCServerBuilder";

    public static JSONObject settings;

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        makeDir(MCSBPath + "/");
        makeDir(MCSBPath + "/Bin");
        makeDir(MCSBPath + "/Jars");
        makeDir(MCSBPath + "/Logs");

        String LAST_VER = VersionManager.getVersionStr();
        Boolean IsUpdateFound = VersionManager.checkUpdate(LAST_VER);
        Logger.log("MCSB v" + VersionManager.VER + " Copyright \u24d2 ClosedDev ( MIT LICENCE v2 )", 0);
        reloadSettings();
        Logger.log(settings.toString(), 0);
        String FullVersion = "1.19.4";
        Logger.log(ApiManager.getLatestBuild(FullVersion), 0);
        String bcode = ApiManager.getLatestBuild(FullVersion);
        int vcodeint = Integer.valueOf(FullVersion.replaceAll("\\.", ""));
        String vcode = null;
        if(countChar(FullVersion, '.')<2) { vcode = String.valueOf(vcodeint*10); }
        if(vcodeint>1000) {
            vcode = String.valueOf(vcodeint - 1000);
        } else {
            vcode = "0" + String.valueOf(vcodeint - 100);
        }
        Logger.log(String.valueOf(vcode), 0);
        CreateServer.createServer(FullVersion, bcode, vcode);
    }

    public static void reloadSettings() throws IOException {
        File d = new File(MCSBPath + "/setting.json");
        if (!d.isFile()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AutoUpdateCheck", true);
            jsonObject.put("Language", "en-us");
            jsonObject.put("DebugMode", false);
            jsonObject.put("AutoFileUpdate", true);
            jsonObject.put("IsInitial", true);
            jsonObject.put("DebugMode", false);
            JSONManager.writeJSON(MCSBPath + "/setting.json", jsonObject);
        }
        settings = JSONManager.loadJSON(MCSBPath + "/setting.json");
    }

    public static void makeDir(String path) throws InterruptedException {
        File d = new File(path);

        if(!d.isDirectory()){
            if(!d.mkdirs()){
                Logger.log("An error occurred while creating the folder.", 1);
            }
        }
    }

    public static long countChar(String str, char ch) {
        return str.chars()
                .filter(c -> c == ch)
                .count();
    }
}
