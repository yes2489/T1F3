package com.waiting.system.service;

import com.waiting.system.model.Restaurant;

public class RestaurantManagerTest {
    // ANSI color
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_RED    = "\u001B[31m";

    public static void main(String[] args) {
        testAddAndGetRestaurant();
        testGetRestaurantById_NotFound();
        testAddRestaurant_DuplicateId();
        testMultipleRestaurants();

        System.out.println("\n" + ANSI_GREEN + "✅ All RestaurantManager tests passed!" + ANSI_RESET);
    }

    // ---------- Tests ----------

    private static void testAddAndGetRestaurant() {
        title("정상 등록 후 조회");

        RestaurantManager m = new RestaurantManager();
        m.addRestaurant(2, "김밥천국", "저렴한 분식", 20, 5);

        Restaurant r = m.getRestaurantById(2);

        expectEquals(2, r.getId(), "식당 ID");
        expectEquals("김밥천국", r.getName(), "식당 이름");
        expectEquals("저렴한 분식", r.getDescription(), "설명");
        expectEquals(20, r.getSeatCount(), "총 좌석 수");
        expectEquals(5, r.getCurCount(), "현재 착석 수");
    }

    private static void testGetRestaurantById_NotFound() {
        title("없는 ID 조회 시 예외");

        RestaurantManager m = new RestaurantManager();

        expectThrows(() -> m.getRestaurantById(999),
                java.util.NoSuchElementException.class, // ← 표준 예외로 지정
                "존재하지 않는 식당 ID",
                "존재하지 않는 식당 ID입니다.999");
    }

    private static void testAddRestaurant_DuplicateId() {
        title("중복 ID 등록 시 예외");

        RestaurantManager m = new RestaurantManager();
        m.addRestaurant(1, "비빔밥집", "맛있는 집", 12, 3);

        expectThrows(() -> m.addRestaurant(1, "다른가게", "중복", 10, 0),
                IllegalArgumentException.class,
                "이미 존재하는 식당 ID",
                "이미 존재하는 식당 ID입니다: 1");
    }

    private static void testMultipleRestaurants() {
        title("다중 등록/조회 독립성");

        RestaurantManager m = new RestaurantManager();
        m.addRestaurant(1, "A식당", "A", 10, 0);
        m.addRestaurant(2, "B식당", "B", 30, 10);

        Restaurant a = m.getRestaurantById(1);
        Restaurant b = m.getRestaurantById(2);

        expectEquals("A식당", a.getName(), "A 이름");
        expectEquals(10, a.getSeatCount(), "A 좌석");
        expectEquals("B식당", b.getName(), "B 이름");
        expectEquals(30, b.getSeatCount(), "B 좌석");
    }

    // ---------- Helpers ----------

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

    private static void expectThrows(Runnable fn, Class<? extends Throwable> exType, String label) {
        expectThrows(fn, exType, label, null);
    }

    private static void expectThrows(Runnable fn, Class<? extends Throwable> exType, String label, String msgContains) {
        try {
            fn.run();
        } catch (Throwable t) {
            if (!exType.isInstance(t)) {
                fail(label + " | expected exception=" + exType.getSimpleName() + ", actual=" + t.getClass().getSimpleName());
                return;
            }
            if (msgContains != null) {
                String msg = t.getMessage();
                if (msg == null || !msg.contains(msgContains)) {
                    fail(label + " | exception message mismatch. expected to contain=\"" + msgContains + "\", actual=\"" + msg + "\"");
                    return;
                }
            }
            ok(label + " threw " + exType.getSimpleName() + (msgContains != null ? " (message OK)" : ""));
            return;
        }
        fail(label + " | expected exception=" + exType.getSimpleName() + " but nothing thrown");
    }

    private static void ok(String msg) {
        System.out.println(ANSI_GREEN + "[OK] " + ANSI_RESET + msg);
    }

    private static void fail(String msg) {
        throw new AssertionError(ANSI_RED + "[FAIL] " + ANSI_RESET + msg);
    }

    // Custom exception to avoid importing java.util.NoSuchElementException here if you prefer fully-qualified above
    private static class NoSuchElementException extends java.util.NoSuchElementException {
        public NoSuchElementException(String s) { super(s); }
    }
}
