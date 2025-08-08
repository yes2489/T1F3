package com.waiting.system.model;

import java.util.*;

import com.waiting.system.service.ReservationManager;

public class Restaurant {

    private int id;
    private String name;
    private String description;
    private int seatCount;
    private int curCount = 0;
    ReservationManager reservationManager = new ReservationManager();


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getSeatCount() {
        return seatCount;
    }


    public int getCurCount() {
        return curCount;
    }


    public Restaurant(int id, String name, String description, int seatCount, int curCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seatCount = seatCount;
        this.curCount = curCount;
    }


    public synchronized int makeReservation(String phoneNum, int memberNum) {
        Reservation reservation = reservationManager.addReservation(phoneNum, memberNum);
        notifyAll();
        return reservationManager.getHowFar(reservation.getPhoneNum());
    }


    public boolean cancelReservation(int RestaurantId, String phoneNum) {
        return reservationManager.cancel(phoneNum);
    }


    public synchronized ArrayList<String> quit(int RestaurantId, int memberNum) {
        ArrayList<String> phoneNums = new ArrayList<>();

        curCount = Math.max(0, curCount - memberNum);

        while (true) {
            int availableSeat = seatCount - curCount;

            int nextMemberNum = reservationManager.getFirstMemberNum();

            if (nextMemberNum == -1 || availableSeat <= 0) break;

            if (nextMemberNum <= availableSeat) {
                Reservation reservation = reservationManager.call();
                curCount += reservation.getMemberNum();
                availableSeat -= reservation.getMemberNum();
                phoneNums.add(reservation.getPhoneNum());
            } else {
                Reservation reservation = reservationManager.callByNum(availableSeat);
                if (reservation == null) break;

                curCount += reservation.getMemberNum();
                availableSeat -= reservation.getMemberNum();
                phoneNums.add(reservation.getPhoneNum());
            }
        }

        return phoneNums;
    }

    public int checkWaitingNum(int RestaurantId, String phoneNum) {
        return reservationManager.getHowFar(phoneNum);
    }

    public int checkLeftSeat(int id) {
        return seatCount - curCount;
    }
}
