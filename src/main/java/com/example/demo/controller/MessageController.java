package com.example.demo.controller;

import com.example.demo.dto.chat.MessengerResponse;
import com.example.demo.handler.ChatHandler;
import com.example.demo.manager.ChatSessionManager;
import com.example.demo.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messager")
public class MessageController {
    final MessageService  messageService;
    final ChatSessionManager  chatSessionManager;
    final ChatHandler  chatHandler;
    public MessageController(MessageService messageService, ChatSessionManager chatSessionManager, ChatHandler chatHandler) {
        this.messageService = messageService;
        this.chatSessionManager = chatSessionManager;
        this.chatHandler = chatHandler;
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<List<MessengerResponse>> getMessageByRoomId(@PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok(messageService.findAllByChatRoom_Id(roomId));
    }

}
