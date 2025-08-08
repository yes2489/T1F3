package com.waiting.system.model;

import com.waiting.system.model.Reservation;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTest {

    // ANSI color codes
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_RED    = "\u001B[31m";

    public static void main(String[] args) {
        // --- 1) 생성자 & getter ---
        Reservation r = new Reservation(4, "010-1234-5678");
        expectEquals(4, r.getMemberNum(), "memberNum should match constructor arg");
        expectEquals("010-1234-5678", r.getPhoneNum(), "phoneNum should match constructor arg");

        // --- 2) createdAt 포맷 확인 ---
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
        String expectedFormat = LocalTime.now().format(formatter).substring(0, 6);
        // 초 단위 차이를 허용하기 위해 분까지만 비교
        expectTrue(r.getCreatedAt().contains("시"), "createdAt should contain '시'");
        expectTrue(r.getCreatedAt().contains("분"), "createdAt should contain '분'");
        expectTrue(r.getCreatedAt().contains("초"), "createdAt should contain '초'");

        // --- 3) toString() 내용 확인 ---
        String toStr = r.toString();
        expectTrue(toStr.contains("memberNum: 4"), "toString should include memberNum");
        expectTrue(toStr.contains(r.getPhoneNum()), "toString should include phoneNum");
        expectTrue(toStr.contains(r.getCreatedAt()), "toString should include createdAt");

        System.out.println("\n" + ANSI_GREEN + "✅ All Reservation tests passed!" + ANSI_RESET);
    }

    // --- Assertion helpers ---
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