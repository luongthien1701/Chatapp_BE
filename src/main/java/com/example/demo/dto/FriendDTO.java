package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendDTO {
    private Long id;
    private Long friendId;
    private String displayName;
    private String avatarUrl;
    private String status;
    private Date lastLogin;
}

