package io.github.closeddev;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    public static void log(String text, Integer type) throws InterruptedException {
        if (type == 0) {
            System.out.println("[ OK ] " + text);
        } else if (type == 1) {
            System.out.println(ANSI_RED + "[ ERROR ] " + text + ANSI_RESET);
            Thread.sleep(5000);
            System.exit(1);
        } else if (type == 2) {
            System.out.println("[ WARNING ] " + text);
        }
    }
    
    
}
