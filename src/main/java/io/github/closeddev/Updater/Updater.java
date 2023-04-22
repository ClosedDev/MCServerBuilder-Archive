package io.github.closeddev.Updater;

import io.github.closeddev.Main;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Updater {
    public static void updateMCSB(String programPath, String LAST_VER) throws IOException, InterruptedException {
        System.out.println("Downloading MCSB Updater!");
        Main.makeDir(Main.MCSBPath + "/Temp");

        File file = new File(Main.MCSBPath + "/Bin/MCSBUpdater.jar");
        if(file.exists()){
            file.delete();
        }

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL("https://github.com/ClosedDev/MCSBUpdater/releases/latest/download/MCSBUpdater.jar").openStream());
            fout = new FileOutputStream(Main.MCSBPath + "/Bin/MCSBUpdater.jar");

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        System.out.println("Starting MCSB Updater!");
        File BatFile = new File(Main.MCSBPath + "/Temp/Updater.bat");
        FileWriter fw = new FileWriter(BatFile, true);
        fw.write("@echo off\ntitle MCSBUpdater\njava -jar " + Main.MCSBPath + "/Bin/MCSBUpdater.jar " + Main.PROGRAM_PATH + " " + LAST_VER + "\npause\nexit");
        fw.flush();
        fw.close();

        Runtime.getRuntime().exec("cmd /c start " + Main.MCSBPath + "/Temp/Updater.bat");

        System.exit(0);
    }
}
