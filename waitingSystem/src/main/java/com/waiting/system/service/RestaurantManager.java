package com.waiting.system.service;

import com.waiting.system.model.Restaurant;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class RestaurantManager {

    private Map<Integer, Restaurant> restaurants = new HashMap<>();

    public RestaurantManager() {
        addRestaurant(1, "한식당", "정갈한 한식 코스 요리", 10, 0);
        addRestaurant(2, "이탈리안레스토랑", "정통 이탈리안 파스타 전문점", 8, 0);
        addRestaurant(3, "초밥집", "오마카세 스시 전문", 6, 0);
        addRestaurant(4, "중국집", "전통 짜장면과 탕수육", 12, 0);
    }

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