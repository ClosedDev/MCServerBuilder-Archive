package io.github.closeddev.Server;

import io.github.closeddev.Downloader;
import io.github.closeddev.JSONManager;
import io.github.closeddev.Logger;
import io.github.closeddev.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static io.github.closeddev.Main.MCSBPath;

public class CreateServer {

    public static final String SERVERPATH = MCSBPath + "/Temp/testserver";
    public static void createServer(String FullVersion, String Build, String vcode, int ramAmount) {
        try {
            Main.makeDir(SERVERPATH);

            File verjson = new File(MCSBPath + "/versions.json");
            if (!verjson.isFile()) { // version.json 파일이 없을 경우 기본 파일 생성
                JSONObject jsonObject = new JSONObject();
                JSONObject asdf = new JSONObject(); // 이거슨 아무 의미도 없는 JSONObject 이름 원하시면 바꾸시오
                asdf.put("list", new ArrayList<String>());
                jsonObject.put("vers", asdf);
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
            Files.copy(filename, Paths.get(SERVERPATH + "/paper.jar"));
            crteula(SERVERPATH);
            crtstart(SERVERPATH, ramAmount);


        } catch(Exception e) {
            Logger.log(e.toString(), 1);
        }
    }
    private static void crtstart(String svpath, float ramAmount) {
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

    public static void crtproper(JSONObject properties) {
        String configPath = Main.PROGRAM_PATH + "/server.properties";
        File config = new File(configPath);
        if (!config.isFile()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                Logger.log("Failed to create server.properties File!", 1);
            }
        }

        ServerManager.editProperties(configPath, "enable-command-block", true);
    }
}
