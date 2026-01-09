package com.example.demo.dto.chat;

import lombok.Data;

@Data
public class MessageDTO {
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private String fileUrl;
    private Type type;

    enum Type {TEXT, IMAGE, FILE }
}
