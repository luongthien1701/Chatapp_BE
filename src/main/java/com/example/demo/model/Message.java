package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private Users sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "file_url")
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "reply_to")
    private Message replyTo;

    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.TEXT;

    @Enumerated(EnumType.STRING)
    private MessageStatus status = MessageStatus.SENT;

    @Column(name = "sent_at")
    private Timestamp sentAt;

    @Column(name = "edited_at")
    private Timestamp editedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "message")
    @JsonIgnore
    private List<MessageRead> reads;

    @PrePersist
    public void onCreate()
    {
        sentAt = Timestamp.from(Instant.now());
        editedAt = Timestamp.from(Instant.now());
        isDeleted = false;
        status = MessageStatus.SENT;
        reads = new ArrayList<>();
        replyTo = null;
        fileUrl = null;
    }
    public enum MessageType {
        TEXT, IMAGE, FILE
    }

    public enum MessageStatus {
        SENT, DELIVERED, SEEN
    }
}
