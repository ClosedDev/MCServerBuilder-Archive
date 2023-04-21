package io.github.closeddev;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiManager {
    public static String getLatestBuild(String FullVersion) throws IOException, ParseException {
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
            throw e;
        }
        return bcode;
    }
}
