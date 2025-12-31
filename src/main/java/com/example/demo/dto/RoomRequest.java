package com.example.demo.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private Action Type;
    private Long chatId;
    private Long userId;
    public enum Action {
        join,leave,block
    }

}
