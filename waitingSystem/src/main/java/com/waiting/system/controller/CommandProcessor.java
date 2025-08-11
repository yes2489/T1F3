package com.waiting.system.controller;

import java.util.ArrayList;
import com.waiting.system.model.Restaurant;
import com.waiting.system.service.RestaurantManager;
import com.waiting.system.view.UIHandler;

public class CommandProcessor {
    private final RestaurantManager manager;
    // 생성자에서 주입 받기
    public CommandProcessor(RestaurantManager manager) {
        this.manager = manager;
    }
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
            case "help":
                handleHelp();
                break;
            default:
                UIHandler.addLine("지원하지 않는 명령어입니다. 'help'를 입력해 사용 가능한 명령을 확인하세요.");
                UIHandler.flush("");
        }
    }
    private void handleHelp() {
        UIHandler.addLine("[사용 가능한 명령어]");
        UIHandler.addLine("----------------------------------------");
        UIHandler.addLine("1) reservation <전화번호> <인원수:int> <식당ID:int>");
        UIHandler.addLine("   - 예약을 생성하고 대기번호를 반환합니다.");
        UIHandler.addLine("   - 예) reservation 01012345678 3 1");
        UIHandler.addLine("");
        UIHandler.addLine("2) cancel <전화번호> <식당ID:int>");
        UIHandler.addLine("   - 해당 전화번호의 예약을 취소합니다.");
        UIHandler.addLine("   - 예) cancel 01012345678 1");
        UIHandler.addLine("");
        UIHandler.addLine("3) quit <식당ID:int> <퇴장인원:int>");
        UIHandler.addLine("   - 손님 퇴장 후 입장 대기 중인 다음 대기 손님을 안내합니다.");
        UIHandler.addLine("   - 예) quit 101 12");
        UIHandler.addLine("");
        UIHandler.addLine("4) help");
        UIHandler.addLine("   - 명령어와 사용법을 표시합니다.");
        UIHandler.addLine("----------------------------------------");
        UIHandler.flush("");
    }
    private void handleQuit(String[] tokens) {
        // 인수 검증
        if (tokens.length < 3) {
            UIHandler.addLine("사용법: quit <식당ID:int> <퇴장인원:int>");
            UIHandler.flush("");
            return;
        }
        // 식당 ID로 해당 식당 객체 가져오기
        Restaurant restaurant = manager.getRestaurantById(Integer.parseInt(tokens[2]));
        // 손님 퇴장 후 입장 대기 중인 다음 손님 번호 목록 반환
        ArrayList<String> nextCustomerNum = restaurant.quit(
                Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2])
        );
        // 반환된 손님 번호 목록 출력
        for (int i = 0; i < nextCustomerNum.size(); i++) {
            UIHandler.addLine("들어올 손님 번호는 " + nextCustomerNum.get(i) + "입니다.");
        }
        UIHandler.flush("");
    }
    private void handleCancel(String[] tokens) {
        // 인수 검증
        if (tokens.length < 3) {
            UIHandler.addLine("사용법: cancel <전화번호> <식당ID:int>");
            UIHandler.flush("");
            return;
        }
        // 식당 ID로 해당 식당 객체 가져오기
        Restaurant restaurant = manager.getRestaurantById(Integer.parseInt(tokens[2]));
        // 예약 취소 여부 반환
        boolean isCanceled = restaurant.cancelReservation(Integer.parseInt(tokens[2]), tokens[1]);
        // 반환된 예약 취소 여부 출력
        if (isCanceled) {
            UIHandler.addLine("예약이 취소되었습니다.");
        } else {
            UIHandler.addLine("예약 취소에 실패했습니다. (예약을 찾을 수 없음)");
        }
        UIHandler.flush("");
    }
    private void handleReservation(String[] tokens) {
        // 인수 검증
        if (tokens.length < 4) {
            UIHandler.addLine("사용법: reservation <전화번호> <인원수:int> <식당ID:int>");
            UIHandler.flush("");
            return;
        }
        // 식당 객체 가져오기
        Restaurant restaurant = manager.getRestaurantById(Integer.parseInt(tokens[3]));
        // 예약 후 대기 번호 반환
        int waitingNumber = restaurant.makeReservation(tokens[1], Integer.parseInt(tokens[2]));
        // 콘솔 출력
        UIHandler.addLine("예약이 완료되었습니다. 당신의 대기 번호는 " + waitingNumber + "번입니다.");
        UIHandler.flush("");
    }
}