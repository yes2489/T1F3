package com.waiting.system.model;

import java.util.List;

public class RestaurantTest {

    // ANSI color codes
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_RED    = "\u001B[31m";

    public static void main(String[] args) {
        testEnqueueAndWaitingNumbers();
        testQuitCallsPossibleTeams();
        testCancelRemovesAllByPhone();
        testLeftSeatsAndWaitingCheckers();

        System.out.println("\n" + ANSI_GREEN + "✅ All Restaurant tests passed!" + ANSI_RESET);
    }

    private static Restaurant createRestaurant(int seatCount, int curCount) {
        // id=1, name/desc은 테스트 비중 낮음
        return new Restaurant(1, "테스트식당", "설명", seatCount, curCount);
    }

    // 1) makeReservation은 항상 큐 적재 + 대기번호 반환
    private static void testEnqueueAndWaitingNumbers() {
        title("예약은 항상 큐 적재되고 대기번호를 반환한다");

        // 좌석 5, 현재 5명 -> 남은 좌석 0, 반드시 큐 적재
        Restaurant restaurant = createRestaurant(5, 5);

        int w1 = restaurant.makeReservation("010-0000-0001", 2);
        int w2 = restaurant.makeReservation("010-0000-0002", 1);
        int w3 = restaurant.makeReservation("010-0000-0003", 4);

        expectEquals(1, w1, "첫 번째 예약 대기번호");
        expectEquals(2, w2, "두 번째 예약 대기번호");
        expectEquals(3, w3, "세 번째 예약 대기번호");

        // 대기순번 확인
        expectEquals(1, restaurant.checkWaitingNum(1, "010-0000-0001"), "대기순번(0001)");
        expectEquals(2, restaurant.checkWaitingNum(1, "010-0000-0002"), "대기순번(0002)");
        expectEquals(3, restaurant.checkWaitingNum(1, "010-0000-0003"), "대기순번(0003)");
    }

    // 2) quit: 퇴장으로 좌석이 생기면 가능한 팀을 호출하여 입장
    private static void testQuitCallsPossibleTeams() {
        title("퇴장 시 가능한 팀을 호출하여 입장시킨다");

        // 좌석 10, 현재 10명 -> 남은 0
        Restaurant restaurant = createRestaurant(10, 10);

        // 대기열: 3명, 2명, 6명(순서)
        restaurant.makeReservation("010-1111-1111", 3);
        restaurant.makeReservation("010-2222-2222", 2);
        restaurant.makeReservation("010-3333-3333", 6);

        // 5명 퇴장 -> 남은 좌석 5
        List<String> called = restaurant.quit(1, 5);

        // 기대: 3명 + 2명 호출, 6명은 남음
        expectEquals(2, called.size(), "호출된 팀 수");
        expectEquals("010-1111-1111", called.get(0), "첫 번째 호출 번호");
        expectEquals("010-2222-2222", called.get(1), "두 번째 호출 번호");

        // 호출 후 남은 좌석 = 0 (10석 - (10-5+3+2) = 0)
        expectEquals(0, restaurant.checkLeftSeat(1), "호출 후 잔여 좌석");

        // 남은 대기열: 6명 팀 한 팀, 순번 1
        expectEquals(1, restaurant.checkWaitingNum(1, "010-3333-3333"), "남은 팀 대기순번(6명 팀)");
    }

    // 3) cancelReservation: 동일 전화번호의 예약 모두 제거 (메서드는 true 반환 고정)
    private static void testCancelRemovesAllByPhone() {
        title("예약 취소 시 동일 전화번호의 모든 예약이 제거된다");

        Restaurant restaurant = createRestaurant(6, 6); // 꽉 차 있으므로 큐에 들어감

        restaurant.makeReservation("010-4444-4444", 2);
        restaurant.makeReservation("010-5555-5555", 1);
        restaurant.makeReservation("010-4444-4444", 3); // 같은 번호 두 건

        // 취소 전 확인
        expectEquals(1, restaurant.checkWaitingNum(1, "010-4444-4444"), "취소 전(4444) 대기순번");

        boolean removed = restaurant.cancelReservation(1, "010-4444-4444");
        expectTrue(removed, "cancelReservation 반환값");

        // 같은 번호는 모두 제거되어야 함
        expectEquals(-1, restaurant.checkWaitingNum(1, "010-4444-4444"), "취소 후(4444) 대기순번");

        // 다른 번호는 그대로
        expectEquals(1, restaurant.checkWaitingNum(1, "010-5555-5555"), "취소 후(5555) 대기순번 유지");
    }

    // 4) 잔여 좌석/대기순번 조회 기본 동작
    private static void testLeftSeatsAndWaitingCheckers() {
        title("잔여 좌석과 대기순번 조회 동작");

        Restaurant restaurant = createRestaurant(8, 5); // 남은 3석
        expectEquals(3, restaurant.checkLeftSeat(1), "초기 잔여 좌석");

        restaurant.makeReservation("010-6666-6666", 4); // 큐 적재(좌석 남아도 makeReservation은 항상 큐)
        restaurant.makeReservation("010-7777-7777", 2);

        expectEquals(1, restaurant.checkWaitingNum(1, "010-6666-6666"), "대기순번(6666)");
        expectEquals(2, restaurant.checkWaitingNum(1, "010-7777-7777"), "대기순번(7777)");

        // 2명 퇴장 -> 남은 좌석 5
        List<String> called = restaurant.quit(1, 2);
        // 가능한 시나리오: 맨앞 4명(6666) 호출 -> 좌석 1 남음, 다음 2명(7777)은 못 들어옴
        expectEquals(1, called.size(), "퇴장 후 호출된 팀 수");
        expectEquals("010-6666-6666", called.get(0), "퇴장 후 첫 호출 번호");
        expectEquals(1, restaurant.checkLeftSeat(1), "호출 후 잔여 좌석(8 - (5-2 + 4) = 1)");

        // 여전히 7777은 대기 1위
        expectEquals(1, restaurant.checkWaitingNum(1, "010-7777-7777"), "남은 팀 대기순번(7777)");
    }

    // ===== Helpers =====
    private static void title(String name) {
        System.out.println("\n== " + name + " ==");
    }

    private static void expectEquals(int expected, int actual, String label) {
        if (expected != actual) {
            fail(label + " | expected=" + expected + ", actual=" + actual);
        } else {
            ok(label + " = " + actual);
        }
    }

    private static void expectEquals(String expected, String actual, String label) {
        if ((expected == null && actual != null) || (expected != null && !expected.equals(actual))) {
            fail(label + " | expected=\"" + expected + "\", actual=\"" + actual + "\"");
        } else {
            ok(label + " = \"" + actual + "\"");
        }
    }

    private static void expectTrue(boolean condition, String label) {
        if (!condition) fail(label + " | condition was false");
        else ok(label + " is true");
    }

    private static void ok(String msg) {
        System.out.println(ANSI_GREEN + "[OK] " + ANSI_RESET + msg);
    }

    private static void fail(String msg) {
        throw new AssertionError(ANSI_RED + "[FAIL] " + ANSI_RESET + msg);
    }
}
