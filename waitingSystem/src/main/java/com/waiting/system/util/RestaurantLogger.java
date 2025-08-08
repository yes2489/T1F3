package com.waiting.system.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 시스템 동작 흐름을 기록하고 사용자에게 로그 메시지를 출력하는 클래스
 * - 주요 이벤트: 예약 생성, 취소, 입장, 호출
 * - 출력은 UIHandler를 통해 화면에 누적됨
 */
public class RestaurantLogger {

    /** 내부 로그 리스트 (멀티스레드 환경 대응) - 로깅 메시지를 UIHandler로 보내기 전에 보관하거나 추후 디버깅으로 활용
     * synchronizedList: 멀티 스레드 환경에서 데이터 안전성을 보장하는 자료 구조.
     *                   내용을 읽고 쓰고 지우고 하는데 타 스레드와의 충돌없이 사용 가능
     *                   (synchronized 키워드를 통해 thread-safe를 구현)
     * */
    private final List<String> logs = Collections.synchronizedList(new ArrayList<>());

    /** 로그 시간 형식 (yy-MM-dd HH:mm:ss) */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");

    // TODO: 예약 생성 로그 출력, 예약 취소 로그 출력, 입장 완료 로그 출력, 호출 로그 출력

    /**
     * 로그 메시지 포맷 생성
     * @param event 이벤트 이름 (예: 예약 생성)
     * @param detail 상세 메시지
     * @return 포맷된 로그 문자열
     */
    private String logFormat(String event, String detail) {
        return "[" + LocalDateTime.now().format(formatter) + "] [" + event + "] " + detail;
    }

    /**
     * 로그 저장 및 UI 출력 누적 처리
     * @param msg 로그 메시지
     */
    private void output(String msg) {
        logs.add(msg);              // 로그 리스트에 저장
        UIHandler.addLine(msg);    // UIHandler에 출력 누적
        // flush()는 외부에서 호출
    }
}
