package com.example.demo.dto;

import com.example.demo.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private SenderInfo sender;
    private String content;
    private String image;
    private Long favorite;
    private Long comments;
    private Timestamp createAt;
    private boolean is_liked;
}
