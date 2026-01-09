package com.example.demo.dto.chat;

import com.example.demo.dto.user.UserSummary;
import com.example.demo.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.model.Message;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessengerResponse {
    private Long id;
    private String content;
    private String fileUrl;
    private String type;
    private String status;
    private Timestamp sentAt;
    private Timestamp editedAt;
    private UserSummary sender;
    private Long groupId;
    private String groupName;

    public MessengerResponse(MessageDTO message) {
        this.content = message.getContent();
        this.fileUrl = message.getFileUrl();
        this.type = message.getType().toString();
        this.sentAt = new Timestamp(System.currentTimeMillis());
        this.editedAt = new Timestamp(System.currentTimeMillis());
        this.groupId = message.getChatRoomId();
        this.sender = new UserSummary(
                message.getSenderId(),
                null,
                null
        );
    }
}
