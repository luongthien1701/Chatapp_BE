package com.example.demo.handler;

import com.example.demo.dto.ChatRoomDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessengerResponse;
import com.example.demo.dto.NotificationDTO;
import com.example.demo.manager.CallManager;
import com.example.demo.manager.ChatSessionManager;
import com.example.demo.manager.GlobalOnlineManager;
import com.example.demo.manager.LocateManager;
import com.example.demo.model.Message;
import com.example.demo.model.Notification;
import com.example.demo.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Component
public class ChatHandler extends TextWebSocketHandler {
    private final MessageService messageService;
    private final NotiService notiService;
    private final ChatRoomService chatRoomService;
    private final ChatMemberService chatMemberService;
    private final UserService userService;
    private final NewsfeedService newsfeedService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String query = session.getUri().getQuery();
            Long userId = Long.parseLong(query.split("=")[1]);
            GlobalOnlineManager.addOnlineUser(userId, session);
            System.out.println("✅ [ChatHandler] User connected - UserId: " + userId);
        } catch (Exception e) {
            System.out.println("❌ [ChatHandler] Error in afterConnectionEstablished: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            Long userId = GlobalOnlineManager.getUserId(session);
            if (userId != null) {
                GlobalOnlineManager.removeOnlineUser(userId);
                CallManager.removeUserFromAllCalls(userId);
                LocateManager.removeLocate(userId);
                System.out.println("✅ [ChatHandler] User disconnected - UserId: " + userId);
            }
        } catch (Exception e) {
            System.out.println("❌ [ChatHandler] Error in afterConnectionClosed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(textMessage.getPayload(), Map.class);

            String event = (String) map.get("event");
            Map<String, Object> data = (Map<String, Object>) map.get("data");

            System.out.println("📨 [ChatHandler] Received event: " + event + ", Data: " + data);

            switch (event) {
                case "call_invite":
                    handleCallInvite(session,data);
                    break;
                case "call_accept":
                    handleCallAccept(session,data);
                    break;
                case "call_reject":
                    handleCallReject(session,data);
                    break;
                case "call_end":
                    handleCallEnd(session,data);
                    break;
                case "message":
                    handleChatMessage(session, data);
                    break;
                case "notification":
                    handleNotification(session, data);
                    break;
                case "join_room":
                    Long userId = ((Number) data.get("userId")).longValue();
                    Long roomId = ((Number) data.get("roomId")).longValue();
                    ChatSessionManager.addUserToRoom(userId, roomId);
                    break;
                case "leave_room":
                    Long uid = ((Number) data.get("userId")).longValue();
                    Long rid = ((Number) data.get("roomId")).longValue();
                    ChatSessionManager.removeUserFromRoom(uid, rid);
                    break;
                default:
                    System.out.println("⚠️ [ChatHandler] Unknown event: " + event);
            }
        } catch (Exception e) {
            System.out.println("❌ [ChatHandler] Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
    }


    protected void handleChatMessage(WebSocketSession session, Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MessageDTO dto = mapper.convertValue(payload, MessageDTO.class);
        Message mess = messageService.save(dto);
        MessengerResponse response = new MessengerResponse(mess);

        ChatRoomDTO chatRoomDTO = chatRoomService.findbyConvervationId(response.getGroupId());
        chatRoomDTO.setLastMessage(response.getContent());
        chatRoomDTO.setSendTime(new Timestamp(System.currentTimeMillis()));
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("event", "message");
        wrapper.put("data", response);

        String jsonMess = mapper.writeValueAsString(wrapper);

        Set<Long> members = chatRoomService.getMemberInRoom(dto.getChatRoomId());
        for (Long userId : members) {
            WebSocketSession userSession = GlobalOnlineManager.getSession(userId);
            if (userSession != null && userSession.isOpen()) {
                if (ChatSessionManager.isUserInRoom(userId, dto.getChatRoomId())) {
                    System.out.println(userId);
                    userSession.sendMessage(new TextMessage(jsonMess));
                }
            }
        }
    }

    protected void handleNotification(WebSocketSession session, Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NotificationDTO notification = mapper.convertValue(payload, NotificationDTO.class);
        notiService.save(notification);
        if (GlobalOnlineManager.isOnlineUser(notification.getReceiver())) {
            WebSocketSession userSession = GlobalOnlineManager.getSession(notification.getReceiver());
            if (userSession != null && userSession.isOpen()) {
                Map<String, Object> wrapper = new HashMap<>();
                wrapper.put("event", "notification");
                wrapper.put("data", notification);

                String json = mapper.writeValueAsString(wrapper);
                userSession.sendMessage(new TextMessage(json));
            }
        }
        else
        {
            String type=notification.getType();
            String name=userService.findById(notification.getSender().getId()).getName();
            String token;
            Long userId;
            switch (type) {
                case "message":
                    token=notiService.getToken(notification.getReceiver());
                    FCMService.sendMessage(token,name+" đã gửi cho bạn một tin nhắn");
                    break;
                case "add_friend":
                    token=notiService.getToken(notification.getReceiver());
                    FCMService.sendMessage(token,name+" đã gửi gửi lời mời kết bạn");
                    break;
                case "like_post":
                    userId=newsfeedService.getUserId(notification.getReceiver());
                    token=notiService.getToken(userId);
                    FCMService.sendMessage(token,name+" đã thích bài viết của bạn");
                    break;
                case "comment":
                    userId=newsfeedService.getUserId(notification.getReceiver());
                    token=notiService.getToken(userId);
                    FCMService.sendMessage(token,name+" đã bình luận bài viết của bạn");
                    break;
                default:
                    break;
            }
        }
    }

    protected void handleCallInvite(WebSocketSession session, Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Long roomId = ((Number) payload.get("roomId")).longValue();
        Long userId = ((Number) payload.get("userId")).longValue();
        CallManager.startCall(roomId, userId);
        List<Long> members=chatMemberService.getMembers(roomId);
        for (Long memberId : members) {
            if (memberId.equals(userId)) continue;
            if (GlobalOnlineManager.isOnlineUser(memberId)) {
                WebSocketSession userSession = GlobalOnlineManager.getSession(memberId);
                if (userSession != null && userSession.isOpen()) {
                    Map<String, Object> wrapper = new HashMap<>();
                    wrapper.put("event", "call_comming");
                    wrapper.put("data", payload);
                    String json = mapper.writeValueAsString(wrapper);
                    userSession.sendMessage(new TextMessage(json));
                }
            }
        }
    }

    protected void handleCallAccept(WebSocketSession session, Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Long roomId = ((Number) payload.get("roomId")).longValue();
        Long userId = ((Number) payload.get("userId")).longValue();
        CallManager.joinCall(roomId, userId);
        Long callerId=CallManager.getCallerId(roomId);
        if  (GlobalOnlineManager.isOnlineUser(callerId)) {
            WebSocketSession userSession = GlobalOnlineManager.getSession(callerId);
            if (userSession != null && userSession.isOpen()) {
                Map<String, Object> wrapper = new HashMap<>();
                wrapper.put("event", "call_accept");
                wrapper.put("data", payload);
                String json = mapper.writeValueAsString(wrapper);
                userSession.sendMessage(new TextMessage(json));
            }
        }
    }

    protected void handleCallReject(WebSocketSession session, Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Long userId = ((Number) payload.get("userId")).longValue();
        Long roomId = ((Number) payload.get("roomId")).longValue();
        Set<Long> members=CallManager.getCallMembers(roomId);
        for (Long memberId : members) {
            if (memberId.equals(userId)) continue;
            if (GlobalOnlineManager.isOnlineUser(memberId)) {
                WebSocketSession userSession = GlobalOnlineManager.getSession(memberId);
                if (userSession != null && userSession.isOpen()) {
                    Map<String, Object> wrapper = new HashMap<>();
                    wrapper.put("event", "call_reject");
                    wrapper.put("data", payload);
                    String json = mapper.writeValueAsString(wrapper);
                    userSession.sendMessage(new TextMessage(json));
                }
            }
        }
    }

    protected void handleCallEnd(WebSocketSession session, Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Long roomId = ((Number) payload.get("roomId")).longValue();
        Long userId = ((Number) payload.get("userId")).longValue();
        Set<Long> members=CallManager.getCallMembers(roomId);
        for (Long memberId : members) {
            if (memberId.equals(userId)) continue;
            if (GlobalOnlineManager.isOnlineUser(memberId)) {
                WebSocketSession userSession = GlobalOnlineManager.getSession(memberId);
                if (userSession != null && userSession.isOpen()) {
                    Map<String, Object> wrapper = new HashMap<>();
                    wrapper.put("event", "call_end");
                    wrapper.put("data", payload);
                    String json = mapper.writeValueAsString(wrapper);
                    userSession.sendMessage(new TextMessage(json));
                }
            }
        }
    }
}

