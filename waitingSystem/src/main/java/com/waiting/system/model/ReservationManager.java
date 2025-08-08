package com.waiting.system.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import com.waiting.system.model.Reservation;

public class ReservationManager {
    private int restaurantId;
    private int maxNum;
    private Queue<com.waiting.system.model.Reservation> reservationQueue;

    ReservationManager(int ID, int MaxNum) {
        this.restaurantId = ID;
        this.maxNum = MaxNum;
        this.reservationQueue = new LinkedList<>();
    }

    public int getMaxNum() {
        return maxNum;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public Queue<com.waiting.system.model.Reservation> getReservationQueue() {
        return reservationQueue;


    }

    public boolean reservation() {
        return true;
    }
}
