package com.example.demo.dto.noti;

import com.example.demo.dto.user.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private boolean status;
    private Timestamp createdAt;
    private UserSummary sender;
    private Long receiver;
    private String type;
}