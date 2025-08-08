package com.waiting.system.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.waiting.system.model.Reservation;

public class ReservationManager {
    final private Queue<Reservation> reservationQueue;

    public ReservationManager() {
        this.reservationQueue = new ConcurrentLinkedQueue<>();
    }

    public int getWaitingLength() {
        return reservationQueue.size();
    }

    public Reservation addReservation(String phoneNum, int memberNum) {
        Reservation newReservation = new Reservation(memberNum, phoneNum);

        reservationQueue.add(newReservation);
        return newReservation;
    }

    public void cancel(String phoneNum) {
        reservationQueue.removeIf(r -> phoneNum != null && phoneNum.equals(r.getPhoneNum()));
    }

    public Reservation call() {
        return reservationQueue.poll();
    }

    public Reservation callByNum(int memberNum) {
        for (Reservation r : reservationQueue) {
            if (r.getMemberNum() <= memberNum) {
                if (reservationQueue.remove(r)) { // 안전하게 제거
                    return r;
                }
            }
        }
        return null;
    }

    public int getHowFar(String phoneNum) {
        if (phoneNum == null) return -1;
        int count = 0;
        for(Reservation r : reservationQueue) {
            if(r.getPhoneNum().equals(phoneNum)) {
                return count + 1;
            }
            count++;
        }
        return -1;
    }

    public int getFirstMemberNum() {
        Reservation peek = reservationQueue.peek();
        if(peek != null)
            return peek.getMemberNum();
        else
            return -1;
    }
}
