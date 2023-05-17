package io.github.closeddev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    static Logger logger = LogManager.getLogger(Main.class);
    public static void Download(String fileUrl, String saveAt) {
        File file = new File(saveAt);
        if(file.exists()){
            file.delete();
        }

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(saveAt);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } catch (IOException e) {
            CrashReporter.fatal(e.toString(), logger);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                CrashReporter.fatal(e.toString(), logger);
            }
        }
    }
}
