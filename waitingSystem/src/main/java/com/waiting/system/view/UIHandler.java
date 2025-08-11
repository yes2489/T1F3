package com.waiting.system.view;

public class UIHandler {
    // 현재 콘솔에 출력된 문자열 길이를 추적
    private static int lastLength = 0;
    private static StringBuilder sb = new StringBuilder();

    public static void flush(String newText) {
        // 화면 지우기
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // 다시 맨 앞으로 이동해서 새 텍스트 출력
        System.out.print(sb);
        sb.setLength(0);

        // 마지막 출력 길이 저장
        lastLength = newText.length();
    }


    public static void add(String text) {
        sb.append(text);
    }

    public static void add(Boolean b) {
        sb.append(b.toString());
    }

    public static void add(Integer i) {
        sb.append(i.toString());
    }

    public static void addLine(String text) {
        sb.append(text).append("\n");
    }

    public static void addLine(Boolean b) {
        sb.append(b.toString()).append("\n");
    }

    public static void addLine(Integer i) {
        sb.append(i.toString()).append("\n");
    }

    public static boolean isPhoneNum(String text){
        return text.matches("\\d{11}");
    }

    public static String[] getCommand(String text){
        return text.trim().split("\\s+");
    }
}
