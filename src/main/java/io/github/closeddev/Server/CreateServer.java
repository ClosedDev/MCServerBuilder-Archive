package io.github.closeddev.Server;

import io.github.closeddev.Downloader;
import io.github.closeddev.JSONManager;
import io.github.closeddev.Logger;
import io.github.closeddev.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.github.closeddev.Main.MCSBPath;

public class CreateServer {

    public static HashMap<String, Object> PROPERTIES = new HashMap<>();

    public static final String SERVERPATH = MCSBPath + "/Temp/Testserver";
    public static void createServer(String FullVersion, String Build, String vcode, int ramAmount) {
        try {
            Main.makeDir(SERVERPATH);

            File verjson = new File(MCSBPath + "/versions.json");
            if (!verjson.isFile()) { // version.json 파일이 없을 경우 기본 파일 생성
                JSONObject jsonObject = new JSONObject();
                JSONObject versionObj = new JSONObject();
                versionObj.put("list", new ArrayList<String>());
                jsonObject.put("vers", versionObj);
                JSONManager.writeJSON(MCSBPath + "/versions.json", jsonObject);
            }

            Path filename = null;
            JSONObject jsonObject = JSONManager.loadJSON(MCSBPath + "/versions.json");
            JSONObject vers = (JSONObject) jsonObject.get("vers");
            JSONArray versionList = (JSONArray) vers.get("list");
            String paperurl = String.valueOf(new URL("https://api.papermc.io/v2/projects/paper/versions/" + FullVersion + "/builds/" + Build + "/downloads/paper-" + FullVersion + "-" + Build + ".jar"));


            if (versionList.contains(FullVersion)) { // 이미 다운로드한 버킷이 있는지 확인
                JSONObject ver = (JSONObject) vers.get(FullVersion);
                if (ver.get("build").equals(Build)) { // 이미 다운로드한 버킷이 최신 빌드인지 확인
                    filename = Paths.get((String) ver.get("jar"));
                } else {
                    String delfilename = (String) ver.get("jar");
                    File delfile = new File(delfilename);
                    if (!delfile.delete()) {
                        Logger.log("Failed to delete " + delfilename, 1);
                    }
                    ver.put("build", Build);
                    filename = Paths.get(MCSBPath + "/Jars/P" + vcode + "-" + Build + ".jar");
                    Downloader.Download(paperurl, String.valueOf(filename), "Papermc server"); // Jars 폴더에 버킷 저장
                    ver.put("jar", String.valueOf(filename));
                }
            } else {
                filename = Paths.get(MCSBPath + "/Jars/P" + vcode + "-" + Build + ".jar");
                Downloader.Download(paperurl, String.valueOf(filename), "Papermc server"); // Jars 폴더에 버킷 저장
                versionList.add(FullVersion); // JSON에 버전 삽입
                JSONObject versionfield = new JSONObject();
                versionfield.put("build", Build);
                versionfield.put("jar", String.valueOf(filename));
                vers.put(FullVersion, versionfield);
            }
            JSONManager.writeJSON(MCSBPath + "/versions.json", jsonObject);
            if (!Paths.get(SERVERPATH + "/paper.jar").toFile().exists()) Files.copy(filename, Paths.get(SERVERPATH + "/paper.jar"));
            crteula(SERVERPATH);
            crtstart(SERVERPATH, ramAmount);
            crtproper();
            Files.copy(Paths.get((MCSBPath + "/server.properties")), Paths.get(SERVERPATH + "/server.properties"));



        } catch(Exception e) {
            Logger.log(e.toString(), 1);
        }
    }
    public static void crtstart(String svpath, float ramAmount) {
        File BatFile = new File(svpath + "/start.bat");
        try {
            FileWriter fw = new FileWriter(BatFile, true);
            fw.write("@echo off\njava -" + "Xms" + ramAmount + "G -Xmx" + ramAmount + "G -jar paper.jar\npause\nexit");
            fw.flush();
            fw.close();
            System.out.println("@echo off\njava -" + "Xms" + ramAmount + "G -Xmx" + ramAmount + "G -jar paper.jar\npause\nexit");
        } catch (IOException e) {
            Logger.log(e.toString(), 1);
        }
    }

    private static void crteula(String svpath) {
        File BatFile = new File(svpath + "eula.txt");
        try {
            FileWriter fw = new FileWriter(BatFile, true);
            fw.write("eula=true");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            Logger.log(e.toString(), 1);
        }
    }

    public static void crtproper() {

        String configPath = MCSBPath + "/server.properties";
        File config = new File(configPath);
        if (!config.isFile()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                Logger.log("Failed to create server.properties File!", 1);
            }
        }

        for (Map.Entry<String, Object> entry : PROPERTIES.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            ServerManager.editProperties(configPath, key, value);
        }
    }

    public static void setproperdefault() {
        // Default properties value
        PROPERTIES.put("level-seed", 0);
        PROPERTIES.put("gamemode", "survival");
        PROPERTIES.put("enable-command-block", false);
        PROPERTIES.put("level-name", "world");
        PROPERTIES.put("motd", "A Minecraft Server");
        PROPERTIES.put("difficulty", "easy");
        PROPERTIES.put("max-players", 20);
        PROPERTIES.put("online-mode", true);
        PROPERTIES.put("allow-flight", false);
        PROPERTIES.put("allow-nether", true);
        PROPERTIES.put("server-port", 25565);
        PROPERTIES.put("hardcore", false);
        PROPERTIES.put("white-list", false);
        PROPERTIES.put("spawn-npcs", true);
        PROPERTIES.put("spawn-animals", true);
        PROPERTIES.put("level-type", "minecraft\\:default");
        PROPERTIES.put("spawn-monsters", true);
        PROPERTIES.put("spawn-protection", 16);
    }
}
