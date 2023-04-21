package io.github.closeddev;

import me.tongfei.progressbar.ProgressBar;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {
    public static void Download(String fileUrl, String saveAt, String filePresentName) throws IOException, MalformedURLException {

        URL url = new URL(fileUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long completeFileSize = httpConnection.getContentLength();

        try(InputStream inputStream = url.openStream();
            CountingInputStream cis = new CountingInputStream(inputStream);
            FileOutputStream fileOS = new FileOutputStream(saveAt);
            ProgressBar pb = new ProgressBar(filePresentName, Math.floorDiv(completeFileSize, 1000)
            )) {

            pb.setExtraMessage("Downloading...");

            new Thread(() -> {
                try {
                    IOUtils.copyLarge(cis, fileOS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (cis.getByteCount() < completeFileSize) {
                pb.stepTo(Math.floorDiv(cis.getByteCount(), 1000));
            }

            pb.stepTo(Math.floorDiv(cis.getByteCount(), 1000));
        }
    }
}
