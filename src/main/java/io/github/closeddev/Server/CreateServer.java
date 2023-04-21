package io.github.closeddev.Server;

import io.github.closeddev.Downloader;
import io.github.closeddev.Main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateServer {

    public static final String SERVERPATH = Main.MCSBPath + "/Temp/Jars";
    public static void createServer(String FullVersion, String Build, String vcode) throws InterruptedException, IOException {
        Main.makeDir(SERVERPATH);
        Path filename = Paths.get(Main.MCSBPath + "/Jars/P" + vcode + "-" + Build + ".jar");
        String paperurl = String.valueOf(new URL("https://api.papermc.io/v2/projects/paper/versions/" + FullVersion + "/builds/" + Build + "/downloads/paper-" + FullVersion + "-" + Build + ".jar"));
        Downloader.Download(paperurl, String.valueOf(filename), "Papermc server"); // Jars 폴더에 버킷 저장
        Files.copy(filename, Paths.get(SERVERPATH + "/paper.jar"));
    }
}
