package io.github.closeddev.Plugins;

import io.github.closeddev.Downloader;
import io.github.closeddev.Server.ServerManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    public static List<String> getPluginList(String path) {
        if (ServerManager.isServerSet()) {
            File dir = new File(path); // 폴더 경로 지정
            File[] files = dir.listFiles((dir1, name) -> name.endsWith(".jar")); // .jar 파일 필터링
            List<String> filesSTR = new ArrayList<>();
            if (files != null) {
                if (files.length > 0) {
                    for (File file : files) {
                        filesSTR.add(file.getName());
                    }
                    return filesSTR;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void installPlugin(String path, String url, String filename) {
        try {
            Downloader.Download(url, path + "/Plugins/" + filename ,filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
