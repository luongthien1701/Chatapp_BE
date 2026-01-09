package com.example.demo.dto.post;

import com.example.demo.dto.user.UserSummary;
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
    private UserSummary sender;
    private String content;
    private List<String> image;
    private Long favorite;
    private Long comments;
    private Timestamp createAt;
    private boolean is_liked;
}
