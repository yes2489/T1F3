package test.java.com.waiting.system.model;

import main.java.com.waiting.system.model.Reservation;
import main.java.com.waiting.system.model.ReservationManager;

public class ReservationManagerTest {

    // ANSI color codes
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_RED    = "\u001B[31m";

    public static void main(String[] args) {
        ReservationManager mgr = new ReservationManager();

        // --- 1) addReservation & 기본 상태 ---
        Reservation r1 = mgr.addReservation("010-1", 2); // A
        Reservation r2 = mgr.addReservation("010-2", 4); // B
        Reservation r3 = mgr.addReservation("010-3", 1); // C
        Reservation r4 = mgr.addReservation("010-2", 3); // D (동일 번호로 2건)

        expectEquals(4, mgr.getWaitingLength(), "waiting length after adds");
        expectEquals(2, mgr.getFirstMemberNum(), "first member num after adds");
        expectEquals(3, mgr.getHowFar("010-3"), "howFar for 010-3 (1-based)");

        // --- 2) call (FIFO) ---
        Reservation called1 = mgr.call(); // A 나와야 함
        expectNotNull(called1, "call() #1 should return a reservation");
        expectEquals("010-1", called1.getPhoneNum(), "call() #1 phone");
        expectEquals(3, mgr.getWaitingLength(), "waiting length after call #1");
        expectEquals(4, mgr.getFirstMemberNum(), "first member after call #1 (should be B=4)");

        // --- 3) callByNum (<=) ---
        Reservation called2 = mgr.callByNum(2);
        expectNotNull(called2, "callByNum(2) should return a reservation");
        expectEquals("010-3", called2.getPhoneNum(), "callByNum(2) picked phone");
        expectEquals(2, mgr.getWaitingLength(), "waiting length after callByNum(2)");

        // --- 4) cancel (모두 삭제) ---
        mgr.cancel("010-2");
        expectEquals(0, mgr.getWaitingLength(), "waiting length after cancel(010-2)");
        expectEquals(-1, mgr.getFirstMemberNum(), "first member after all removed");
        expectNull(mgr.call(), "call() on empty should be null");
        expectNull(mgr.callByNum(10), "callByNum on empty should be null");
        expectEquals(-1, mgr.getHowFar("010-2"), "howFar on empty should be -1");

        System.out.println("\n" + ANSI_GREEN + "✅ All ReservationManager tests passed!" + ANSI_RESET);
    }

    // --- Tiny assertion helpers ---
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

    private static void expectNotNull(Object obj, String label) {
        if (obj == null) fail(label + " | expected non-null");
        else ok(label + " is non-null");
    }

    private static void expectNull(Object obj, String label) {
        if (obj != null) fail(label + " | expected null but got " + obj);
        else ok(label + " is null");
    }

    private static void ok(String msg) {
        System.out.println(ANSI_GREEN + "[OK] " + ANSI_RESET + msg);
    }

    private static void fail(String msg) {
        throw new AssertionError(ANSI_RED + "[FAIL] " + ANSI_RESET + msg);
    }
}

