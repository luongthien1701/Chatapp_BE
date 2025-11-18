package com.example.demo.handler;

import com.example.demo.dto.ChatRoomDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessengerResponse;
import com.example.demo.dto.NotificationDTO;
import com.example.demo.manager.ChatSessionManager;
import com.example.demo.manager.GlobalOnlineManager;
import com.example.demo.model.Message;
import com.example.demo.model.Notification;
import com.example.demo.service.ChatRoomService;
import com.example.demo.service.MessageService;
import com.example.demo.service.NotiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Component
public class ChatHandler extends TextWebSocketHandler {
    private final MessageService messageService;
    private final NotiService notiService;
    private final ChatRoomService  chatRoomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        Long userId = Long.parseLong(query.split("=")[1]);
        GlobalOnlineManager.addOnlineUser(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId=GlobalOnlineManager.getUserId(session);
        GlobalOnlineManager.removeOnlineUser(userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(textMessage.getPayload(), Map.class);

        String event = (String) map.get("event");
        Map<String, Object> data = (Map<String, Object>) map.get("data");

        switch (event) {
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
                System.out.println("⚠️ Unknown event: " + event);
        }
    }

    protected void handleChatMessage(WebSocketSession session, Map<String,Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MessageDTO dto = mapper.convertValue(payload, MessageDTO.class);

        Message mess = messageService.save(dto);
        MessengerResponse response = new MessengerResponse(mess);

        ChatRoomDTO chatRoomDTO = chatRoomService.findbyConvervationId(response.getGroupId());
        chatRoomDTO.setLastMessage(response.getContent());
        chatRoomDTO.setSendTime(new Timestamp(System.currentTimeMillis()));
        Map<String, Object> newMess = new HashMap<>();
        newMess.put("event", "new_message");
        newMess.put("data", chatRoomDTO);

        String jsonNewMess = mapper.writeValueAsString(newMess);
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("event", "message");
        wrapper.put("data", response);

        String jsonMess = mapper.writeValueAsString(wrapper);

        Set<Long> members=chatRoomService.getMemberInRoom(dto.getChatRoomId());
        for (Long userId : members) {
            WebSocketSession userSession = GlobalOnlineManager.getSession(userId);
            if (userSession != null && userSession.isOpen()) {
                if (ChatSessionManager.isUserInRoom(userId, dto.getChatRoomId())) {
                    userSession.sendMessage(new TextMessage(jsonMess));
                }

                if (!userId.equals(dto.getSenderId())) {
                    userSession.sendMessage(new TextMessage(jsonNewMess));
                }
            }
        }

    }

    protected void handleNotification(WebSocketSession session, Map<String,Object> payload) throws Exception {
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
    }


}
