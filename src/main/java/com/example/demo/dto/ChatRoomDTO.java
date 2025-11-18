package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomDTO {
    private Long id;
    private String name;
    private String avatarUrl;
    private String lastMessage;
    private String group;
    private Timestamp sendTime;
}
