package com.example.demo.dto.noti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgoraRequest {
    private String channelName;
    private int userId;
}
