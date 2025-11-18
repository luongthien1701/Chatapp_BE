package com.example.demo.manager;

import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalOnlineManager {
    private static final Map<Long, WebSocketSession> onlineUsers = new ConcurrentHashMap<>();

    public static WebSocketSession getSession(Long userId){
        return onlineUsers.get(userId);
    }
    public static void addOnlineUser(long userId, WebSocketSession session) {
        onlineUsers.put(userId, session);
    }
    public static void removeOnlineUser(long userId) {
        onlineUsers.remove(userId);
    }
    public static boolean isOnlineUser(long userId) {
        return onlineUsers.containsKey(userId);
    }
    public static Collection<WebSocketSession> getOnlineUsers() {
        return onlineUsers.values();
    }
    public static Long getUserId(WebSocketSession session) {
        return onlineUsers.entrySet().stream()
                .filter(entry -> entry.getValue().equals(session))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

}
