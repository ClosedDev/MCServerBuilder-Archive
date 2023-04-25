package io.github.closeddev.Menu;

import io.github.closeddev.LangManager;
import io.github.closeddev.Main;

public class MainMenu {
    public static void startMenu() {
        System.out.println(LangManager.getText("inMenu", Main.Language) + "\n");
        System.out.println(LangManager.getText("initial_1", Main.Language));
        System.out.println(LangManager.getText("initial_2", Main.Language));
        System.out.println(LangManager.getText("initial_3", Main.Language));
        System.out.println(LangManager.getText("initial_4", Main.Language));
    }
}
