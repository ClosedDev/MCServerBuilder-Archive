package io.github.closeddev;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiManager {
    private static List<String> majorArray;
    private static List<String> fullArray;

    private static void reloadListVars() {
        try {
            // API 엔드포인트 URL 설정
            URL url = new URL("https://api.papermc.io/v2/projects/paper");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            // 응답 받아오기
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // JSON 파싱
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(content.toString());
                majorArray = (List<String>) jsonObject.get("version_groups");
                fullArray = (List<String>) jsonObject.get("versions");
            }
        } catch (Exception e) {
            Logger.log(e.toString(), 1);
        }
    }

    public static List<String> getFullArray() {
        reloadListVars();
        return fullArray;
    }
    public static List<String> getMajorArray() {
        reloadListVars();
        return majorArray;
    }

    public static String getLatestBuild(String FullVersion) {
        String bcode = null;
        try {
            // API 엔드포인트 URL 설정
            URL url = new URL("https://papermc.io/api/v2/projects/paper/versions/" + FullVersion);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            // 응답 받아오기
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // JSON 파싱
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(content.toString());
                JSONArray buildsArray = (JSONArray) jsonObject.get("builds");

                // builds 리스트의 마지막 항목 가져오기
                Long lastBuild = (Long) buildsArray.get(buildsArray.size() - 1);
                bcode = lastBuild.toString();
            }
        } catch (Exception e) {
            Logger.log(e.toString(), 1);
        }
        return bcode;
    }
}
