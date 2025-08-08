package com.waiting.system.service;

import com.waiting.system.model.Restaurant;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;



public class RestaurantManager {

    private Map<Integer, Restaurant> restaurants = new HashMap<>();


    public Restaurant getRestaurantById(int id) {
        if (!restaurants.containsKey(id)) {
            throw new NoSuchElementException("존재하지 않는 식당 ID입니다." + id);
        }
        return restaurants.get(id);
    }

    public void addRestaurant(int id, String name, String description, int seatCount, int curCount) {
        if (restaurants.containsKey(id)) {
            throw new IllegalArgumentException("이미 존재하는 식당 ID입니다: " + id);
        }

        Restaurant restaurant = new Restaurant(id, name, description, seatCount, curCount);
        restaurants.put(id, restaurant);
    }
}