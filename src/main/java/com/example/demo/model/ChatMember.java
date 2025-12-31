package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "joined_at")
    private Timestamp joinedAt;

    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;

    public enum Role {
        MEMBER, ADMIN
    }
}
