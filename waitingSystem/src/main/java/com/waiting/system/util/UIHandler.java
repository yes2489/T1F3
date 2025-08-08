package com.waiting.system.util;

/**
 * 콘솔 UI를 관리하는 유틸리티 클래스
 * - 출력 메시지 누적 및 전체 화면 갱신을 담당
 * - 콘솔 입력 커맨드 파싱 및 유효성 검사 기능 포함
 */
public class UIHandler {
    // 현재 콘솔에 출력된 문자열 길이를 추적
    private static int lastLength = 0;
    // 출력 내용을 누적 저장하는 버퍼
    private static StringBuilder sb = new StringBuilder();

    /**
     * 콘솔 전체를 초기화하고 누적된 내용을 출력함
     * @param newText (사용 안됨) - 이전 flush 기반
     */
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

    // 문자열을 출력 버퍼에 추가 (줄바꿈 없음)
    public static void add(String text) {
        sb.append(text);
    }

    // boolean 값을 출력 버퍼에 추가 (줄바꿈 없음)
    public static void add(Boolean b) {
        sb.append(b.toString());
    }

    // 정수 값을 출력 버퍼에 추가 (줄바꿈 없음)
    public static void add(Integer i) {
        sb.append(i.toString());
    }

    // 문자열을 한 줄로 출력 버퍼에 추가
    public static void addLine(String text) {
        sb.append(text).append("\n");
    }

    // boolean 값을 한 줄로 출력 버퍼에 추가
    public static void addLine(Boolean b) {
        sb.append(b.toString()).append("\n");
    }

    // 정수 값을 한 줄로 출력 버퍼에 추가
    public static void addLine(Integer i) {
        sb.append(i.toString()).append("\n");
    }

    /**
     * 문자열이 11자리 숫자인지 검사 (전화번호 유효성 검사)
     * @param text 입력 문자열
     * @return 11자리 숫자면 true, 아니면 false
     */
    public static boolean isPhoneNum(String text){
        return text.matches("\\d{11}");
    }

    /**
     * 사용자 입력 문자열을 공백 기준으로 나눠 커맨드 배열로 반환
     * @param text 입력 문자열
     * @return 커맨드 배열
     */
    public static String[] getCommand(String text){
        return text.trim().split("\\s+");
    }
}
