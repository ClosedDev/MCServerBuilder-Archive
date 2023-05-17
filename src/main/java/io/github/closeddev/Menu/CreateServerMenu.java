package io.github.closeddev.Menu;

import io.github.closeddev.ApiManager;
import io.github.closeddev.CrashReporter;
import io.github.closeddev.LangManager;
import io.github.closeddev.Main;
import io.github.closeddev.Server.CreateServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static io.github.closeddev.Main.countChar;

public class CreateServerMenu { //추가 Lang 작업 필요

    static Logger logger = LogManager.getLogger(CreateServerMenu.class);

    private static final String BLANK = "";

    private static final HashMap<String, Integer> PROPERINT = new HashMap<>();

    public static void setServer() {
        //서버 만들기 첫번째 단계: 서버 이름, 버전, 램 용량 등을 정함 (코드 구현 예정)

        System.out.println(BLANK);
        System.out.println(BLANK);

        System.out.println("서버 이름을 설정합니다.\n서버 이름을 입력하세요.");

        String ServerName;

        while (true) {
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            ServerName = sc.next();

            if(ServerName.isEmpty()) {
                System.out.println(LangManager.getText("crtserver_4", Main.Language));
            } else {
                break;
            }
        }

        System.out.println(BLANK);

        System.out.println("서버 버전을 선택합니다.\n원하는 서버의 버전을 입력하세요.\n지원되는 버전 목록을 확인하시려면 list를 입력하세요.");

        String FullVersion;

        while (true) {
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            FullVersion = sc.next();

            if((FullVersion.isEmpty() || !ApiManager.getFullArray().contains(FullVersion) && !FullVersion.equals("list"))) {
                System.out.println(LangManager.getText("crtserver_4", Main.Language));
            } else if (FullVersion.equals("list")) {
                for (Object i: ApiManager.getFullArray()) {
                    System.out.println(i);
                }
            } else {
                break;
            }
        }

        System.out.println(BLANK);

        System.out.println("서버에서 사용할 메모리의 크기를 정합니다.\n메모리 크기를 입력해 주세요.\n(단위: GB | 추천: 1GB)");

        int ramAmount;

        while (true) {
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            try {
                ramAmount = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println(LangManager.getText("crtserver_4", Main.Language));
            }
        }
        System.out.println("잠시 후, 서버 properties 설정을 진행합니다...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            CrashReporter.fatal(e.toString(), logger);
        }

        setproperties(); // properties 설정은 메서드를 분리

        clearScreen();

        System.out.println("서버를 생성합니다.");

        String bcode = ApiManager.getLatestBuild(FullVersion);
        int vcodeint = Integer.parseInt(FullVersion.replaceAll("\\.", ""));
        String vcode = null;

        if(countChar(FullVersion, '.')<2) vcode = String.valueOf(vcodeint * 10);

        if(vcodeint>1000) {
            vcode = String.valueOf(vcodeint - 1000);
        } else {
            vcode = "0" + String.valueOf(vcodeint - 100);
        }

        CreateServer.createServer(FullVersion, bcode, vcode, ramAmount, ServerName);
    }

    public static void setproperties() {
        CreateServer.setproperdefault();

        for (int i = CreateServer.PROPERTIES.keySet().toArray().length; i > 0; i--) {
            int i2 = ((i - CreateServer.PROPERTIES.keySet().toArray().length)*(-1));
            PROPERINT.put((String) CreateServer.PROPERTIES.keySet().toArray()[i2], i2);
        }

        while (true) {
            System.out.println(LangManager.getText("crtserver_1", Main.Language));

            for (Map.Entry<String, Object> entry : CreateServer.PROPERTIES.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                System.out.println(PROPERINT.get(key).toString() + ") " + LangManager.getText(key, Main.Language) + " : " + value);
            }
            System.out.println(BLANK);
            System.out.println(LangManager.getText("crtserver_2", Main.Language));
            System.out.println(LangManager.getText("crtserver_3", Main.Language));

            String selectedProperties;

            while (true) {
                System.out.print("> ");
                Scanner sc = new Scanner(System.in);
                selectedProperties = sc.next();

                try {
                    if (selectedProperties.equals("exit")) {
                        break;
                    } else if (Integer.parseInt(selectedProperties) > 0 && Integer.parseInt(selectedProperties) <= CreateServer.PROPERTIES.keySet().toArray().length) {
                        break;
                    } else {
                        System.out.println(LangManager.getText("crtserver_4", Main.Language));
                    }
                } catch (Exception e) {
                    System.out.println(LangManager.getText("crtserver_4", Main.Language));
                }
            }

            if (selectedProperties.equals("exit")) break;

            String valuetype;

            if (CreateServer.PROPERTIES.get((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]) instanceof String) valuetype = "String";
            else if (CreateServer.PROPERTIES.get((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]) instanceof Integer) valuetype = "Integer";
            else if (CreateServer.PROPERTIES.get((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]) instanceof Boolean) valuetype = "Boolean";
            else valuetype = "Unknown";

            System.out.println(BLANK);
            System.out.println(LangManager.getText("crtserver_5", Main.Language) + (String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)] + " ) " + LangManager.getText((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)], Main.Language));

            Object setvalue;

            while (true) {
                Scanner sc = new Scanner(System.in);
                if (valuetype.equals("String")) {
                    try {
                        System.out.println(LangManager.getText("crtserver_6", Main.Language));
                        System.out.print("> ");
                        setvalue = sc.next();
                        break;
                    } catch (Exception e) {
                        logger.warn(LangManager.getText("crtserver_4", Main.Language));
                    }
                } else if (valuetype.equals("Integer")) {
                    try {
                        System.out.println(LangManager.getText("crtserver_7", Main.Language));
                        System.out.print("> ");
                        setvalue = sc.nextInt();
                        break;
                    } catch (Exception e) {
                        logger.warn(LangManager.getText("crtserver_7", Main.Language));
                    }
                } else if (valuetype.equals("Boolean")) {
                    System.out.println(LangManager.getText("crtserver_6", Main.Language));
                    System.out.print("> ");
                    setvalue = sc.next();
                    if(setvalue.equals("false")) {
                        setvalue = false;
                        break;
                    } else if(setvalue.equals("true")) {
                        setvalue = true;
                        break;
                    } else if(setvalue.equals("cancel")) {
                        break;
                    } else {
                        logger.warn(LangManager.getText("crtserver_4", Main.Language));
                    }
                } else {
                    CrashReporter.fatal("Invalid Type", logger);
                }
            }

            if(!setvalue.toString().equals("cancel") && !setvalue.toString().equals("-130")) {
                CreateServer.PROPERTIES.put((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)], setvalue);
            }

            clearScreen();
        }

        System.out.println(BLANK);
        System.out.println(LangManager.getText("crtserver_8", Main.Language));
    }

    private static void clearScreen() {
        for (int i = 0; i < 80; i++)
            System.out.println(BLANK);
    }
}
