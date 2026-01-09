package com.example.demo.dto.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendResponse {
    private Long id;
    private Long friendId;
    private String displayName;
    private String avatarUrl;
    private String status;
    private Date lastLogin;
}

