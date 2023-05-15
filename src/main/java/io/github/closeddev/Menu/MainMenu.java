package io.github.closeddev.Menu;

import io.github.closeddev.JSONManager;
import io.github.closeddev.LangManager;
import io.github.closeddev.Logger;
import io.github.closeddev.Main;
import io.github.closeddev.Updater.VersionManager;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static void startMenu() {
        System.out.println(LangManager.getText("inMenu", Main.Language) + "\n");
        System.out.println(LangManager.getText("initial_1", Main.Language));
        System.out.println(LangManager.getText("initial_2", Main.Language));
        System.out.println(LangManager.getText("initial_3", Main.Language));
        System.out.println(LangManager.getText("initial_4", Main.Language));

        System.out.println(""); // Line

        JSONObject langs = LangManager.getLangList();
        List<String> langList = (List<String>) langs.get("list");

        for (String lang: langList) {
            System.out.println(langs.get(lang) + ": " + lang);
        }
        String selectedLang;
        while (true) {
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            selectedLang = sc.next();
            if (langList.contains(selectedLang)) {
                break;
            }
            System.out.println("존재하지 않는 언어입니다.");
        }
        System.out.println("선택한 언어를 적용 중입니다...");

        JSONObject setJson = JSONManager.loadJSON(Main.MCSBPath + "/setting.json");
        setJson.put("Language", selectedLang);
        try {
            JSONManager.writeJSON(Main.MCSBPath + "/setting.json", setJson);
            Main.reloadSettings();
        } catch (Exception e) {
            Logger.log(e.toString(), 1);
        }

        System.out.println("\n\n    MCSB v" + VersionManager.VER);
        System.out.println("\n  -" + LangManager.getText("menuTest", Main.Language) + "-");
    }
}
