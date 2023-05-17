package io.github.closeddev;

import io.github.closeddev.GUI.StartGUI;
import io.github.closeddev.Menu.CreateServerMenu;
import io.github.closeddev.Menu.MainMenu;
import io.github.closeddev.Server.ServerManager;
import io.github.closeddev.Updater.Updater;
import io.github.closeddev.Updater.VersionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);

    public static final String appdata = System.getenv("APPDATA");
    public static final String MCSBPath = appdata + "/MCServerBuilder";
    public static List<String> jarFiles;
    public static String PROGRAM_JAR_PATH, PROGRAM_PATH, Language;
    public static Boolean isServerCreated;

    public static JSONObject settings;

    public static void main(String[] args) {
        PROGRAM_JAR_PATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File PP = new File(PROGRAM_JAR_PATH);
        PROGRAM_PATH = PP.getParent();

        isServerCreated = ServerManager.isServerSet();

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
        logger.info("MCSB v" + VersionManager.VER + " Copyright \u24d2 ClosedDev ( MIT LICENCE v2 )");
        logger.info("Starting MCSB...");

        // 초기화
        logger.info("Initializing: temp folder");

        deleteDir(MCSBPath + "/Temp");

        if(IsUpdateFound) {
            logger.warn("New Update Found! v" + VersionManager.VER + " -> v" + LAST_VER);
            if ((Boolean) settings.get("AutoFileUpdate")) {
                logger.info("Updating MCSB...");
                Updater.updateMCSB(PROGRAM_JAR_PATH, LAST_VER);
            }
        }

        if(!(Boolean) settings.get("DebugMode")) {
            logger.warn("Debug Mode is Enabled!");

            CreateServerMenu.setServer();
            StartGUI.start("Main");
        }
        logger.info("Entering MainMenu...");
        MainMenu.startMenu();
    }

    public static void reloadSettings() {
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
            try {
                StringBuilder langStr = new StringBuilder();
                URL url = new URL("https://pastebin.com/raw/bDgywSwi");
                URLConnection connection = url.openConnection();


                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        langStr.append(line);
                    }
                }
                JSONParser parser = new JSONParser();
                JSONObject langObj = (JSONObject) parser.parse(langStr.toString());
                JSONManager.writeJSON(MCSBPath + "/lang.json", langObj);
            } catch (Exception e) {
                CrashReporter.fatal(e.toString(), logger);
            }
        }
        Language = (String) settings.get("Language");
    }

    public static void makeDir(String path) {
        File d = new File(path);

        if(!d.isDirectory()){
            if(!d.mkdirs()){
                CrashReporter.fatal("An error occurred while creating the folder.", logger);
            }
        }
    }

    public static void deleteDir(String path) {

        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                    }else {
                        deleteDir(folder_list[i].getPath()); //재귀함수호출
                    }
                    folder_list[i].delete();
                }
                folder.delete(); //폴더 삭제
            }
        } catch (Exception e) {
            CrashReporter.fatal("Failed to init!", logger);
        }
    }

    public static long countChar(String str, char ch) {
        return str.chars()
                .filter(c -> c == ch)
                .count();
    }
}
