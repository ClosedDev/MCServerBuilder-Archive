package io.github.closeddev.Menu;

import io.github.closeddev.Logger;
import io.github.closeddev.Server.CreateServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateServerMenu { // Lang 작업 필요 필요!!

    private static final String BLANK = "";

    private static final HashMap<String, Integer> PROPERINT = new HashMap<>();

    public static void setServer() {
        //서버 만들기 첫번째 단계: 서버 이름, 버전, 램 용량 등을 정함 (코드 구현 예정)

        setproperties(); // properties 설정은 메서드를 분리
    }

    public static void setproperties() {
        CreateServer.setproperdefault();

        for (int i = CreateServer.PROPERTIES.keySet().toArray().length; i > 0; i--) {
            int i2 = ((i - CreateServer.PROPERTIES.keySet().toArray().length)*(-1));
            PROPERINT.put((String) CreateServer.PROPERTIES.keySet().toArray()[i2], i2);
        }

        while (true) {
            System.out.println("설정 : 현재 값");

            for (Map.Entry<String, Object> entry : CreateServer.PROPERTIES.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                System.out.println(PROPERINT.get(key).toString() + ") " + key + " : " + value);
            }
            System.out.println(BLANK);
            System.out.println("위 설정 중 변경하고 싶은 설정의 번호를 입력하세요.");
            System.out.println("설정을 완료하셨다면 exit를 입력해주세요.");

            String selectedProperties;

            while (true) {
                System.out.print("> ");
                Scanner sc = new Scanner(System.in);
                selectedProperties = sc.next();

                if (selectedProperties.equals("exit")) {
                    break;
                } else if (Integer.parseInt(selectedProperties) <= CreateServer.PROPERTIES.keySet().toArray().length) {
                    break;
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            }

            if (selectedProperties.equals("exit")) break;

            String valuetype;

            if (CreateServer.PROPERTIES.get((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]) instanceof String) valuetype = "String";
            else if (CreateServer.PROPERTIES.get((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]) instanceof Integer) valuetype = "Integer";
            else if (CreateServer.PROPERTIES.get((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]) instanceof Boolean) valuetype = "Boolean";
            else valuetype = "Unknown";

            System.out.println(BLANK);
            System.out.println("변경할 값을 입력하세요: " + (String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)]);

            Object setvalue;

            while (true) {
                Scanner sc = new Scanner(System.in);
                if (valuetype.equals("String")) {
                    try {
                        System.out.println("취소하시려면 cancel을 입력해주세요.");
                        System.out.print("> ");
                        setvalue = sc.next();
                        break;
                    } catch (Exception e) {
                        Logger.log("잘못된 입력입니다", 2);
                    }
                } else if (valuetype.equals("Integer")) {
                    try {
                        System.out.println("취소하시려면 -130을 입력해주세요.");
                        System.out.print("> ");
                        setvalue = sc.nextInt();
                        break;
                    } catch (Exception e) {
                        Logger.log("잘못된 입력입니다", 2);
                    }
                } else if (valuetype.equals("Boolean")) {
                    System.out.println("취소하시려면 cancel을 입력해주세요.");
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
                        Logger.log("잘못된 입력입니다", 2);
                    }
                } else {
                    Logger.log("java.util.UnknownTypeException", 1);
                }
            }

            if(!setvalue.toString().equals("cancel") && !setvalue.toString().equals("-130")) {
                CreateServer.PROPERTIES.put((String) PROPERINT.keySet().toArray()[Integer.parseInt(selectedProperties)], setvalue);
            }

            clearScreen();
        }

        System.out.println(BLANK);
        System.out.println("설정이 완료되었습니다.");

        CreateServer.crtproper();

    }

    private static void clearScreen() {
        for (int i = 0; i < 80; i++)
            System.out.println(BLANK);
    }
}
