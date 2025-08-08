package com.waiting.system.controller;

import java.util.ArrayList;

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
    	// 식당 객체 가져오기
    	Restaurant restaurant = restaurantManager.getRestaurantById(tokens[2]);
    		
    	// 예약 후 대기 번호 반환
    	int waitingNumber = restaurant.makeReservation(tokens[0], tokens[1]);
    		
    	// 콘솔 출력
    	System.out.println("예약이 완료되었습니다. 당신의 대기 번호는 " + waitingNumber + "번입니다.");
    }

}
