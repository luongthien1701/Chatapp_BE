package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.model.Message;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private SenderInfo sender;
    private Long groupId;
    private String groupName;
    public MessengerResponse(Message message ) {
        this.id = message.getId();
        this.content = message.getContent();
        this.fileUrl = message.getFileUrl();
        this.type=message.getType().toString();
        this.status=message.getStatus().toString();
        this.sentAt=message.getSentAt();
        this.editedAt=message.getEditedAt();
        this.groupId=message.getChatRoom().getId();
        this.groupName=message.getChatRoom().getName();
        this.sender = new SenderInfo(
                message.getSender().getId(),
                message.getSender().getDisplayName(),
                message.getSender().getAvatarUrl()
        );
    }
}
