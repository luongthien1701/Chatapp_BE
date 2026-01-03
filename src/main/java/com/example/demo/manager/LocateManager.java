package com.example.demo.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocateManager {
    private static final Map<Long, Map<Double, Double>> locate = new ConcurrentHashMap<>();

    public static Map<Double, Double> getLocate(Long userId) {
        return locate.get(userId);
    }

    public static void addLocate(Long userId, Double lat, Double lon) {
        if (locate.get(userId) != null) {
            locate.get(userId).put(lon, lat);
        } else {
            locate.put(userId, new ConcurrentHashMap<>(Map.of(lat, lon)));
        }
    }

    public static void removeLocate(Long userId) {
        if (locate.get(userId) == null) return;
        locate.remove(userId);
    }

}
