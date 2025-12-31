package com.example.demo.manager;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ChatSessionManager {

    private static final ConcurrentHashMap<Long, Set<Long>> roomMembers = new ConcurrentHashMap<>();

    public static void addUserToRoom(Long userId, Long roomId) {
        roomMembers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(userId);
    }

    public static void removeUserFromRoom(Long userId, Long roomId) {
        Set<Long> members = roomMembers.get(roomId);
        if(members != null) members.remove(userId);
    }

    public static Set<Long> getRoomMembers(Long roomId) {
        return roomMembers.getOrDefault(roomId, Set.of());
    }
    public static boolean isUserInRoom(Long userId, Long roomId) {
        Set<Long> members = roomMembers.getOrDefault(roomId, Set.of());
        return members.contains(userId);
    }
}
