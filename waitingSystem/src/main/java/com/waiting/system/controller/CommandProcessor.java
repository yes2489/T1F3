package com.waiting.system.controller;


import com.waiting.system.util.UIHandler;

public class CommandProcessor {
    /**
     * 전체 명령 처리 진입점
     * @param input 사용자 입력 문자열
     */
    public void processCommand(String input) {
        String[] tokens = UIHandler.getCommand(input);
        if (tokens.length == 0) return;

        String command = tokens[0].toLowerCase();

        switch (command) {
            case "reservation":
                handleReservation(tokens);
                break;
            case "cancel":
                handleCancel(tokens);
                break;
            case "quit":
                handleQuit(tokens);
                break;
            default:
                UIHandler.addLine("❌ 지원하지 않는 명령어입니다.");
        }
        UIHandler.flush(""); // 결과 출력
    }

    private void handleQuit(String[] tokens) {
    }

    private void handleCancel(String[] tokens) {
    }

    private void handleReservation(String[] tokens) {
    }

}
