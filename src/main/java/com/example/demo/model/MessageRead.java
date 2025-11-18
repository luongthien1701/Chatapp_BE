package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message_reads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "read_at")
    private Timestamp readAt;
}
