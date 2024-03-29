package io.github.closeddev.Updater;

import io.github.closeddev.Logger;
import io.github.closeddev.Main;


import java.io.*;
import java.net.URL;

public class Updater {
    public static void updateMCSB(String programPath, String LAST_VER) {
        System.out.println("Downloading MCSB Updater!");
        Main.makeDir(Main.MCSBPath + "/Temp");

        File file = new File(Main.MCSBPath + "/Bin/MCSBUpdater.jar");
        if(file.exists()){
            file.delete();
        }
        File file2 = new File(Main.MCSBPath + "/Temp/Updater.bat");
        if(file2.exists()){
            file2.delete();
        }
        File file3 = new File(Main.MCSBPath + "/Temp/MCSB.jar");
        if(file3.exists()){
            file3.delete();
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
            Logger.log(e.toString(), 1);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                Logger.log(e.toString(), 1);
            }
        }
        System.out.println("Starting MCSB Updater!");
        File BatFile = new File(Main.MCSBPath + "/Temp/Updater.bat");

        try {
            FileWriter fw = new FileWriter(BatFile, true);

            fw.write("@echo off\ntitle MCSBUpdater\njava -jar " + Main.MCSBPath + "/Bin/MCSBUpdater.jar " + Main.PROGRAM_JAR_PATH + " " + LAST_VER + "\npause\nexit");
            fw.flush();
            fw.close();

            Runtime.getRuntime().exec("cmd /c start " + Main.MCSBPath + "/Temp/Updater.bat");
        } catch (IOException e) {
            Logger.log(e.toString(), 1);
        }

        System.exit(0);
    }
}
