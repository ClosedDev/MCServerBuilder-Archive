package io.github.closeddev.Server;

import io.github.closeddev.Downloader;
import io.github.closeddev.JSONManager;
import io.github.closeddev.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CreateServer {

    public static final String SERVERPATH = Main.MCSBPath + "/Temp/Jars";
    public static void createServer(String FullVersion, String Build, String vcode) throws InterruptedException, IOException {
        Main.makeDir(SERVERPATH);
        Path filename = Paths.get(Main.MCSBPath + "/Jars/P" + vcode + "-" + Build + ".jar");
        JSONObject jsonObject = JSONManager.loadJSON(Main.MCSBPath + "/versions.json");
        JSONArray versionList = (JSONArray) jsonObject.get("vers").get("list");
        if () { // 이미 다운로드한 버킷이 있는지 확인
            String paperurl = String.valueOf(new URL("https://api.papermc.io/v2/projects/paper/versions/" + FullVersion + "/builds/" + Build + "/downloads/paper-" + FullVersion + "-" + Build + ".jar"));
            Downloader.Download(paperurl, String.valueOf(filename), "Papermc server"); // Jars 폴더에 버킷 저장
        } else {

        }

        Files.copy(filename, Paths.get(SERVERPATH + "/paper.jar"));
    }
}
