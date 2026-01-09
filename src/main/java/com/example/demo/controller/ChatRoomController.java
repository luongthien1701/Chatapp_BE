package com.example.demo.controller;

import com.example.demo.dto.chat.ChatRoomDTO;
import com.example.demo.dto.chat.RoomRequest;
import com.example.demo.manager.ChatSessionManager;
import com.example.demo.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room")
@CrossOrigin(origins = "*")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatSessionManager  chatSessionManager;

    public ChatRoomController(ChatRoomService chatRoomService, ChatSessionManager chatSessionManager) {
        this.chatRoomService = chatRoomService;
        this.chatSessionManager = chatSessionManager;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<ChatRoomDTO>> getAllrooms(@PathVariable Long userId){
        return ResponseEntity.ok(chatRoomService.GetallRoomById(userId));
    }
    @GetMapping("/infomation/{convervationId}")
    public ResponseEntity<ChatRoomDTO> getinfomation(@PathVariable Long convervationId){
        return ResponseEntity.ok(chatRoomService.findbyConvervationId(convervationId));
    }
    @PostMapping("/update")
    public ResponseEntity<String> updateRoom(@RequestBody RoomRequest chatRoom) {
        switch (chatRoom.getType()) {
            case join:
                chatSessionManager.addUserToRoom(chatRoom.getUserId(), chatRoom.getChatId());
                return ResponseEntity.ok("User joined room");
            case leave:
                chatSessionManager.removeUserFromRoom(chatRoom.getUserId(), chatRoom.getChatId());
                return ResponseEntity.ok("User left room");
            case block:
                return ResponseEntity.ok("Blocked user (not implemented yet)");
            default:
                return ResponseEntity.badRequest().body("Invalid action");
        }
    }
    @GetMapping("/find/{userId}/{friendId}")
    public ResponseEntity<?> findroom(@PathVariable Long userId, @PathVariable Long friendId){
        return ResponseEntity.ok(Map.of("roomId",chatRoomService.findRoom(userId,friendId)));
    }
    @GetMapping("/users/{conversationId}")
    public ResponseEntity<?> getAllUserByConversationId(@PathVariable Long conversationId){
        return ResponseEntity.ok().body(chatRoomService.getMemberInRoom(conversationId));
    }

}
