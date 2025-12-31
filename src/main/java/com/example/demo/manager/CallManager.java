package com.example.demo.manager;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallManager {
    private static final ConcurrentHashMap<Long, Set<Long>> callMembers = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Long> callers = new ConcurrentHashMap<>();

    public static void startCall(Long roomId, Long userId) {
        System.out.println("📞 [CallManager] startCall - Room: " + roomId + ", User: " + userId);
        if (callMembers.containsKey(roomId)) {
            System.out.println("⚠️ [CallManager] Call already exists for room: " + roomId);
            return;
        }
        callMembers.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
        callMembers.get(roomId).add(userId);
        callers.put(roomId, userId);
        System.out.println("✅ [CallManager] Call started - Members: " + callMembers.get(roomId));
    }

    public static void joinCall(Long roomId, Long userId) {
        System.out.println("📞 [CallManager] joinCall - Room: " + roomId + ", User: " + userId);
        callMembers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(userId);
        System.out.println("✅ [CallManager] User joined - Members: " + callMembers.get(roomId));
    }

    public static void leaveCall(Long roomId, Long userId) {
        System.out.println("📞 [CallManager] leaveCall - Room: " + roomId + ", User: " + userId);
        Set<Long> set = callMembers.get(roomId);
        if (set == null) {
            System.out.println("⚠️ [CallManager] No call found for room: " + roomId);
            return;
        }
        set.remove(userId);
        System.out.println("✅ [CallManager] User left - Remaining members: " + set);
        if (set.isEmpty()) {
            callMembers.remove(roomId);
            callers.remove(roomId);
            System.out.println("✅ [CallManager] Call ended - Room: " + roomId + " removed");
        }
    }

    public static Set<Long> getCallMembers(Long roomId) {
        Set<Long> members = callMembers.get(roomId);
        System.out.println("📋 [CallManager] getCallMembers - Room: " + roomId + ", Members: " + members);
        return members;
    }

    public static Long getCallerId(Long roomId) {
        Long callerId = callers.get(roomId);
        System.out.println("📋 [CallManager] getCallerId - Room: " + roomId + ", Caller: " + callerId);
        return callerId;
    }
    public static void removeUserFromAllCalls(Long userId) {
        System.out.println("🧹 [CallManager] removeUserFromAllCalls - User: " + userId);

        callMembers.forEach((roomId, members) -> {
            if (members.contains(userId)) {
                members.remove(userId);
                System.out.println("➖ [CallManager] Removed user " + userId + " from room " + roomId);

                // Nếu room trống → xóa room + caller
                if (members.isEmpty()) {
                    callMembers.remove(roomId);
                    callers.remove(roomId);
                    System.out.println("🗑️ [CallManager] Room " + roomId + " removed (empty)");
                }
            }
        });
    }

}

