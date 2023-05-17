package io.github.closeddev.GUI;

public class StartGUI {
    public static void start(String MenuType) {
        if (MenuType.equals("Main")) {
            new MainMenu();
        }
    }
}
