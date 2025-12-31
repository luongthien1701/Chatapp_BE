package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_image")
public class Post_Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "newsfeed_id")
    @JsonIgnore
    @ToString.Exclude
    private Newsfeed newsfeed;

    @JoinColumn(name = "img_url")
    private String imgUrl;
}
