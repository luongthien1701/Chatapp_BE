package com.example.demo.dto;

import lombok.Data;

@Data
public class FriendRequest {
    private Long senderId;
    private Long receiverId;
}
