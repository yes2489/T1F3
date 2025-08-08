package com.waiting.system.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 예약 시스템 동작 흐름을 기록하는 전용 로거 클래스
 * - 콘솔 출력은 UIHandler에서 담당
 * - 이 클래스는 파일 또는 콘솔 로그 기록만 수행
 */
public class RestaurantLogger {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantLogger.class);

    /** 예약 생성 로그 */
    public static void logReservationCreated(String phone, String restaurantId, int waitingNumber) {
        logger.info("✅ 예약 생성: 전화번호={}, 식당ID={}, 대기번호={}", phone, restaurantId, waitingNumber);
    }

    /** 예약 취소 로그 */
    public static void logReservationCancelled(String phone, String restaurantId, boolean success) {
        if (success) {
            logger.info("🗑️ 예약 취소 성공: 전화번호={}, 식당ID={}", phone, restaurantId);
        } else {
            logger.warn("⚠️ 예약 취소 실패: 전화번호={}, 식당ID={}", phone, restaurantId);
        }
    }

    /** 손님 퇴장 로그 */
    public static void logCustomerQuit(String phone, String restaurantId) {
        logger.info("🚪 손님 퇴장: 전화번호={}, 식당ID={}", phone, restaurantId);
    }

    /** 입장 처리된 다음 손님들 로그 */
    public static void logNextCustomers(List<String> phoneNumbers) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            logger.info("📢 다음 입장 손님 없음");
            return;
        }

        for (String phone : phoneNumbers) {
            logger.info("📢 다음 입장 손님: 전화번호={}", phone);
        }
    }

    /** 좌석 현황 로그 */
    public static void logSeatStatus(String restaurantId, int leftSeats, int waitingSize) {
        logger.info("💺 좌석 현황 - 식당ID={}, 남은 좌석={}, 대기 인원={}", restaurantId, leftSeats, waitingSize);
    }

    /** 시스템 예외 로그 */
    public static void logException(String context, Exception e) {
        logger.error("❌ 예외 발생 - 위치: {}, 메시지: {}", context, e.getMessage(), e);
    }
}
