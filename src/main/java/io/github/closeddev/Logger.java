package io.github.closeddev;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_ORANGE = "\u001B[33m";

    public static void log(String text, Integer type) {
        if (type == 0) {
            System.out.println("[ OK ] " + text);
        } else if (type == 1) {
            System.out.println(ANSI_RED + "[ ERROR ] " + text + ANSI_RESET);
            System.exit(1);
        } else if (type == 2) {
            System.out.println(ANSI_ORANGE + "[ WARNING ] " + text + ANSI_RESET);
        }
    }
    
    
}
