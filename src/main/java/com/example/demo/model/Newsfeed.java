package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "newsfeed")
public class Newsfeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "text")
    private String content;

    @Column(name = "image")
    private String imageUrl;

    private int favorite;

    @OneToMany(mappedBy = "newsfeed")
    private List<Comment> comments;
}
